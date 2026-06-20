package com.oblixorprime.immersiveego.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoader;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorService;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEdge;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluationResult;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluationStep;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluator;
import java.util.Locale;
import java.util.List;
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
                .then(Commands.literal("debug")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("state")
                                .executes(context -> showDebugState(context.getSource()))))
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
