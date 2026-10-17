package com.oblixorprime.immersiveego.gametest;

import com.mojang.authlib.GameProfile;
import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoader;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentService;
import com.oblixorprime.immersiveego.environment.EgoThermalSnapshot;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import com.oblixorprime.immersiveego.wetness.EgoWetnessRuntime;
import com.oblixorprime.immersiveego.wetness.EgoWetnessState;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.UUID;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(ImmersiveEgo.MOD_ID)
@PrefixGameTestTemplate(false)
public final class EgoThermalToleranceGameTests {
    private EgoThermalToleranceGameTests() {}

    private static void check(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }

    private static void near(double expected, double actual, String message) {
        check(Math.abs(expected - actual) < 1.0E-12D, message + ": expected=" + expected + ", actual=" + actual);
    }

    private static ServerPlayer player(GameTestHelper helper) {
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(),
                new GameProfile(UUID.randomUUID(), "ego-thermal"), ClientInformation.createDefault());
        var pos = helper.absolutePos(new net.minecraft.core.BlockPos(1, 40, 1));
        player.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        return player;
    }

    @GameTest(template = "empty", timeoutTicks = 20)
    public static void tolerance_changes_refresh_cached_thermal_without_mutating_w(GameTestHelper helper) {
        ServerPlayer player = player(helper), other = player(helper);
        player.setData(EgoAttachments.EGO_WETNESS.get(), EgoWetnessState.dry().withWetness(0.8D));
        var before = player.getData(EgoAttachments.EGO_WETNESS.get());
        try {
            var neutral = EgoEnvironmentService.debugSnapshot(player);
            long hits = EgoEnvironmentService.counters().hits();
            player.getAttribute(EgoAttributes.THERMAL_TOLERANCE).setBaseValue(2.0D);
            var adjusted = EgoEnvironmentService.debugSnapshot(player);
            check(EgoEnvironmentService.counters().hits() > hits, "Fixture must exercise cached exposure");
            check(neutral.exposureSample().equals(adjusted.exposureSample()), "Tolerance must not change climate/exposure");
            near(neutral.thermalSnapshot().heatPressure() / 2.0D, adjusted.thermalSnapshot().heatPressure(), "Current heat tolerance");
            near(neutral.thermalSnapshot().coldPressure() / 2.0D, adjusted.thermalSnapshot().coldPressure(), "Current cold tolerance");
            check(adjusted.equals(EgoEnvironmentService.latest(player).orElseThrow()), "Latest must project the same current tolerance");
            near(1.0D, EgoEnvironmentService.debugSnapshot(other).thermalSnapshot().thermalTolerance(), "Player isolation");
            player.getAttribute(EgoAttributes.THERMAL_TOLERANCE).setBaseValue(1.0D);
            check(neutral.equals(EgoEnvironmentService.latest(player).orElseThrow()), "Returning to one restores neutral snapshot");
            var modifierId = net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(ImmersiveEgo.MOD_ID, "thermal_test");
            var attribute = player.getAttribute(EgoAttributes.THERMAL_TOLERANCE);
            attribute.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(modifierId,
                    1.5D, net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE));
            try {
                var modified = EgoEnvironmentService.latest(player).orElseThrow().thermalSnapshot();
                near(2.5D, modified.thermalTolerance(), "Effective value includes attribute modifiers");
                near(neutral.thermalSnapshot().heatPressure() / 2.5D, modified.heatPressure(), "Modifier affects cached heat pressure");
                near(neutral.thermalSnapshot().coldPressure() / 2.5D, modified.coldPressure(), "Modifier affects cached cold pressure");
            } finally { attribute.removeModifier(modifierId); }
            check(neutral.equals(EgoEnvironmentService.latest(player).orElseThrow()), "Removing the modifier restores neutral pressure");
            check(before.equals(player.getData(EgoAttachments.EGO_WETNESS.get())), "Reads and attribute changes never write W");
        } finally {
            EgoEnvironmentService.remove(player.getUUID());
            EgoEnvironmentService.remove(other.getUUID());
        }
        helper.succeed();
    }

    @GameTest(template = "empty", timeoutTicks = 20)
    public static void thermal_debug_reports_raw_adjusted_and_matching_wetness_preview(GameTestHelper helper) throws Exception {
        ServerPlayer player = player(helper);
        player.getAttribute(EgoAttributes.THERMAL_TOLERANCE).setBaseValue(2.0D);
        var state = player.getData(EgoAttachments.EGO_STATE.get());
        var wetness = player.getData(EgoAttachments.EGO_WETNESS.get());
        var origin = player.getData(EgoAttachments.EGO_ORIGIN_STATE.get());
        var messages = new ArrayList<Component>();
        CommandSource collector = new CommandSource() {
            public void sendSystemMessage(Component message) { messages.add(message); }
            public boolean acceptsSuccess() { return true; }
            public boolean acceptsFailure() { return true; }
            public boolean shouldInformAdmins() { return false; }
        };
        var source = new CommandSourceStack(collector, Vec3.ZERO, Vec2.ZERO, helper.getLevel(), 2,
                "Thermal test", Component.literal("Thermal test"), helper.getLevel().getServer(), player);
        try {
            var dispatcher = helper.getLevel().getServer().getCommands().getDispatcher();
            check(dispatcher.execute("ego debug environment", source) == 1, "Environment diagnostic executes");
            check(messages.size() == 4, "Includes separate raw and adjusted lines");
            EgoThermalSnapshot thermal = EgoEnvironmentService.latest(player).orElseThrow().thermalSnapshot();
            var raw = (TranslatableContents) messages.get(2).getContents();
            check(raw.getKey().equals("command.immersive_ego.debug.environment.thermal.raw"), "Raw diagnostic identity");
            check(raw.getArgs()[0].equals(decimal(thermal.rawHeatPressure())) &&
                    raw.getArgs()[1].equals(decimal(thermal.rawColdPressure())) && raw.getArgs()[2].equals("2.000"),
                    "Raw pressure and effective tolerance values");
            var adjusted = (TranslatableContents) messages.get(3).getContents();
            check(adjusted.getArgs()[1].equals(decimal(thermal.heatPressure())) &&
                    adjusted.getArgs()[2].equals(decimal(thermal.coldPressure())) &&
                    adjusted.getArgs()[5].equals(decimal(thermal.thermalStrain())), "Adjusted diagnostic values");
            check(dispatcher.execute("ego debug wetness", source) == 1, "Wetness diagnostic executes");
            var preview = (TranslatableContents) messages.get(5).getContents();
            check(preview.getArgs()[3].equals(decimal(thermal.thermalStrain())), "Wetness preview also uses tolerance");
            for (Component message : messages) Component.Serializer.toJson(message, helper.getLevel().registryAccess());
            check(state.equals(player.getData(EgoAttachments.EGO_STATE.get())), "Debug must not advance EGO");
            check(wetness.equals(player.getData(EgoAttachments.EGO_WETNESS.get())), "Debug must not advance W");
            check(origin.equals(player.getData(EgoAttachments.EGO_ORIGIN_STATE.get())), "Debug must not advance acclimation");
        } finally {
            EgoEnvironmentService.remove(player.getUUID());
        }
        helper.succeed();
    }

    private static String decimal(double value) {
        return String.format(Locale.ROOT, "%.3f", value);
    }

    @GameTest(template = "empty", timeoutTicks = 20)
    public static void scheduler_propagates_tolerance_to_hydration_and_acclimation_with_equal_w(GameTestHelper helper) throws Exception {
        Path root = Files.createTempDirectory("ego-thermal-gametest-");
        Path settings = root.resolve("immersive_ego/simulation.toml");
        Files.createDirectories(settings.getParent());
        Files.writeString(settings, "[scheduler]\nenabled=true\nintervalTicks=20\n");
        boolean core = EgoCoreConfig.ENABLE_SIMULATION.get();
        boolean notices = EgoCoreConfig.SHOW_WETNESS_NOTICES.get();
        var modules = EgoServerModuleConfigs.all();
        var enabled = modules.stream().map(module -> module.enabled().get()).toList();
        var world = helper.getLevel().getServer().getWorldData().overworldData();
        long gameTime = world.getGameTime();
        var states = new ArrayList<com.oblixorprime.immersiveego.data.EgoState>();
        var origins = new ArrayList<EgoOriginState>();
        var thermal = new ArrayList<EgoThermalSnapshot>();
        var wetness = new ArrayList<EgoWetnessState>();
        try {
            EgoCoreConfig.ENABLE_SIMULATION.set(true);
            EgoCoreConfig.SHOW_WETNESS_NOTICES.set(false);
            for (var module : modules) module.enabled().set(java.util.Set.of("environment", "physiology", "origins").contains(module.key()));
            EgoDynamicServerConfigLoader.reload(root);
            world.setGameTime(20L);
            for (double tolerance : new double[] {1.0D, 2.0D}) {
                ServerPlayer player = player(helper);
                player.getAttribute(EgoAttributes.THERMAL_TOLERANCE).setBaseValue(tolerance);
                player.setData(EgoAttachments.EGO_WETNESS.get(), EgoWetnessState.dry().withWetness(0.8D));
                player.setData(EgoAttachments.EGO_ORIGIN_STATE.get(), new EgoOriginState(1, "arid", 0.3D, 0.4D, 0.2D, 0.1D));
                var connection = new net.minecraft.network.Connection(net.minecraft.network.protocol.PacketFlow.SERVERBOUND);
                var channel = new io.netty.channel.embedded.EmbeddedChannel(connection);
                player.connection = new net.minecraft.server.network.ServerGamePacketListenerImpl(helper.getLevel().getServer(),
                        connection, player, net.minecraft.server.network.CommonListenerCookie.createInitial(player.getGameProfile(), false));
                try {
                    NeoForge.EVENT_BUS.post(new PlayerTickEvent.Post(player));
                    states.add(player.getData(EgoAttachments.EGO_STATE.get()));
                    origins.add(player.getData(EgoAttachments.EGO_ORIGIN_STATE.get()));
                    wetness.add(player.getData(EgoAttachments.EGO_WETNESS.get()));
                    thermal.add(EgoEnvironmentService.latest(player).orElseThrow().thermalSnapshot());
                } finally {
                    channel.finishAndReleaseAll();
                    NeoForge.EVENT_BUS.post(new PlayerEvent.PlayerLoggedOutEvent(player));
                }
            }
            check(wetness.get(0).equals(wetness.get(1)), "Tolerance must not change acquisition/drying");
            check(wetness.get(0).wetness() < 0.8D, "Actual active W step occurred");
            near(thermal.get(0).thermalStrain() / 2.0D, thermal.get(1).thermalStrain(), "Scheduler reads actual attribute");
            check(thermal.get(0).thermalStrain() > 0.0D, "Nonzero thermal fixture");
            near((thermal.get(0).thermalStrain() - thermal.get(1).thermalStrain()) * 0.00075D,
                    states.get(1).hydration() - states.get(0).hydration(), "Thermal hydration demand uses adjusted strain before origin relief");
            near((thermal.get(0).heatPressure() - thermal.get(1).heatPressure()) * 0.70D * 0.012D,
                    origins.get(0).heatAcclimation() - origins.get(1).heatAcclimation(), "Heat acclimation uses adjusted exposure");
            near((thermal.get(0).coldPressure() - thermal.get(1).coldPressure()) * 0.70D * 0.012D,
                    origins.get(0).coldAcclimation() - origins.get(1).coldAcclimation(), "Cold acclimation uses adjusted exposure");
            for (int i = 0; i < states.size(); i++) {
                double relief = 0.08D * Math.max(origins.get(i).heatAcclimation(), origins.get(i).coldAcclimation());
                near(Math.max(0.0D, thermal.get(i).thermalStrain() - relief), states.get(i).thermalStrain(), "Origin relief remains last");
            }
        } finally {
            world.setGameTime(gameTime);
            EgoCoreConfig.ENABLE_SIMULATION.set(core);
            EgoCoreConfig.SHOW_WETNESS_NOTICES.set(notices);
            for (int i = 0; i < modules.size(); i++) modules.get(i).enabled().set(enabled.get(i));
            EgoDynamicServerConfigLoader.reload(helper.getLevel().getServer().getWorldPath(LevelResource.ROOT).resolve("serverconfig"));
            try (var paths = Files.walk(root)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.deleteIfExists(path);
            }
        }
        helper.succeed();
    }
}
