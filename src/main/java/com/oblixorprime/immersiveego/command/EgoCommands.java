package com.oblixorprime.immersiveego.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoader;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentDebugCounters;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentSampler;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentService;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentSnapshot;
import com.oblixorprime.immersiveego.environment.EgoExposureSample;
import com.oblixorprime.immersiveego.environment.EgoThermalSnapshot;
import com.oblixorprime.immersiveego.environment.EgoThermalSnapshotCalculator;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorService;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEdge;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluationResult;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluationStep;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluator;
import com.oblixorprime.immersiveego.wetness.EgoWetnessRuntime;
import com.oblixorprime.immersiveego.wetness.EgoWetnessState;
import java.util.List;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class EgoCommands {
    private static final List<EgoSynergyEdge> TRACE_SAMPLE_EDGES = List.of(
            new EgoSynergyEdge("fatigue", "stress", 0.25D),
            new EgoSynergyEdge("stress", "ego_load", 0.40D),
            new EgoSynergyEdge("ego_load", "readiness", -0.50D));

    private EgoCommands() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoCommands::registerCommands);
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("ego")
                .then(Commands.literal("status")
                        .executes(context -> showStatus(context.getSource())))
                .then(Commands.literal("wetness")
                        .then(Commands.literal("notices")
                                .then(Commands.argument("enabled", BoolArgumentType.bool())
                                        .executes(context -> {
                                            boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                            EgoWetnessRuntime.setNotices(context.getSource().getPlayerOrException(), enabled);
                                            context.getSource().sendSuccess(() -> Component.translatable(
                                                    "command.immersive_ego.wetness.notices", enabled), false);
                                            return 1;
                                        }))))
                .then(Commands.literal("debug")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("state")
                                .executes(context -> showDebugState(context.getSource())))
                        .then(Commands.literal("wetness")
                                .executes(context -> showDebugWetness(context.getSource())))
                        .then(Commands.literal("environment")
                                .executes(context -> showDebugEnvironment(context.getSource())))
                        .then(Commands.literal("origin")
                                .executes(context -> showDebugOrigin(context.getSource()))))
                .then(Commands.literal("synergy")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("trace")
                                .executes(context -> showSynergyTrace(context.getSource())))));
    }

    private static int showStatus(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        EgoState state = player.getData(EgoAttachments.EGO_STATE.get());
        EgoAttributeMirrorService.updatePlayerMirrors(player, state);

        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.status",
                percent(state.egoLoad()),
                percent(state.readiness()),
                percent(state.hydration()),
                percent(state.stamina()),
                percent(state.fatigue())), false);
        return 1;
    }

    private static int showDebugState(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        EgoState state = player.getData(EgoAttachments.EGO_STATE.get());
        EgoAttributeMirrorService.updatePlayerMirrors(player, state);

        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.debug.state.header",
                state.schemaVersion()), false);

        for (EgoAttributeSpec spec : EgoAttributeCatalog.displayOnlySpecs()) {
            source.sendSuccess(() -> Component.translatable(
                    "command.immersive_ego.debug.state.line",
                    Component.translatable(spec.descriptionId()),
                    decimal(state.displayValue(spec))), false);
        }

        return EgoAttributeCatalog.displayOnlySpecs().size();
    }

    private static int showDebugEnvironment(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        EgoEnvironmentSnapshot snapshot = EgoEnvironmentService.debugSnapshot(player);
        EgoExposureSample exposure = snapshot.exposureSample();
        EgoThermalSnapshot thermal = snapshot.thermalSnapshot();
        EgoEnvironmentDebugCounters counters = EgoEnvironmentService.counters();

        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.debug.environment.header",
                EgoEnvironmentService.environmentModuleEnabled(),
                counters.requests(),
                counters.refreshes(),
                counters.hits(),
                counters.skips(),
                counters.errors(),
                counters.evictions()), false);
        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.debug.environment.sample",
                snapshot.gameTime(),
                exposure.climateProfile().id(),
                exposure.seasonPhase().name().toLowerCase(Locale.ROOT),
                decimal(exposure.skyExposure()),
                decimal(exposure.shelter()),
                decimal(exposure.wetness())), false);
        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.debug.environment.thermal",
                thermal.mode().name().toLowerCase(Locale.ROOT),
                decimal(thermal.heatPressure()),
                decimal(thermal.coldPressure()),
                decimal(thermal.wetnessPressure()),
                decimal(thermal.exposurePressure()),
                decimal(thermal.thermalStrain())), false);

        return 1;
    }

    private static int showDebugWetness(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        EgoWetnessState state = player.getData(EgoAttachments.EGO_WETNESS.get());
        // A fresh observation/thermal preview, never a simulation step or attachment write.
        EgoExposureSample sample = EgoEnvironmentSampler.sample(player);
        EgoThermalSnapshot preview = EgoThermalSnapshotCalculator.calculate(sample,
                EgoWetnessRuntime.thermalWetness(player));
        source.sendSuccess(() -> Component.translatable("command.immersive_ego.wetness.state",
                decimal(state.wetness()), state.schemaVersion(), state.quarantined(),
                EgoWetnessRuntime.activity(player)), false);
        source.sendSuccess(() -> Component.translatable("command.immersive_ego.wetness.preview",
                EgoWetnessRuntime.exposure(player).name(), sample.climateProfile().id(),
                decimal(preview.wetnessPressure()), decimal(preview.thermalStrain())), false);
        EgoWetnessRuntime.Applied applied = EgoWetnessRuntime.lastApplied(player);
        if (applied == null) {
            source.sendSuccess(() -> Component.translatable("command.immersive_ego.wetness.none"), false);
        } else {
            boolean fresh = EgoWetnessRuntime.activity(player).equals("active")
                    && applied.playerTick() == player.tickCount
                    && applied.dimension().equals(player.level().dimension().location().toString());
            source.sendSuccess(() -> Component.translatable("command.immersive_ego.wetness.applied",
                    applied.dimension(), applied.playerTick(), fresh, applied.exposure().name(), applied.climate(),
                    decimal(applied.before()), decimal(applied.step().state().wetness()),
                    decimal(applied.step().seconds()), decimal(applied.step().acquisition()),
                    decimal(applied.step().drying()), decimal(applied.step().rate()), applied.consumedTicks()), false);
        }
        return 1;
    }

    private static int showDebugOrigin(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        EgoOriginState originState = player.getData(EgoAttachments.EGO_ORIGIN_STATE.get());

        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.debug.origin.header",
                originState.originId(),
                originState.schemaVersion()), false);
        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.debug.origin.acclimation",
                decimal(originState.heatAcclimation()),
                decimal(originState.coldAcclimation()),
                decimal(originState.altitudeAcclimation()),
                decimal(originState.humidityAcclimation())), false);

        return 1;
    }

    private static int showSynergyTrace(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        EgoState state = player.getData(EgoAttachments.EGO_STATE.get());
        List<EgoSynergyEdge> configuredEdges = EgoDynamicSynergyConfigLoader.current().edges();
        List<EgoSynergyEdge> traceEdges = configuredEdges.isEmpty() ? TRACE_SAMPLE_EDGES : configuredEdges;
        EgoSynergyEvaluationResult result = EgoSynergyEvaluator.evaluate(state, traceEdges);

        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.synergy.trace.header",
                result.steps().size()), false);

        for (EgoSynergyEvaluationStep step : result.steps()) {
            source.sendSuccess(() -> Component.translatable(
                    "command.immersive_ego.synergy.trace.line",
                    step.sourcePath(),
                    step.targetPath(),
                    decimal(step.sourceValue()),
                    signedDecimal(step.weight()),
                    signedDecimal(step.delta()),
                    decimal(step.beforeValue()),
                    decimal(step.afterValue())), false);
        }

        source.sendSuccess(() -> Component.translatable(
                "command.immersive_ego.synergy.trace.result",
                decimal(result.state().egoLoad()),
                decimal(result.state().readiness())), false);

        return result.steps().size();
    }

    private static String percent(double value) {
        return String.format(Locale.ROOT, "%.0f%%", value * 100.0D);
    }

    private static String decimal(double value) {
        return String.format(Locale.ROOT, "%.3f", value);
    }

    private static String signedDecimal(double value) {
        return String.format(Locale.ROOT, "%+.3f", value);
    }
}
