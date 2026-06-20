package com.oblixorprime.immersiveego.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.oblixorprime.immersiveego.ImmersiveEgo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

public final class EgoDynamicServerConfigLoader {
    public static final String FILE_NAME = "simulation.toml";

    private static final String SCHEDULER_ENABLED = "scheduler.enabled";
    private static final String SCHEDULER_INTERVAL_TICKS = "scheduler.intervalTicks";
    private static final String MIRROR_UPDATE_EPSILON = "display.mirrorUpdateEpsilon";

    private static EgoDynamicServerConfig current = EgoDynamicServerConfig.defaults();

    private EgoDynamicServerConfigLoader() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoDynamicServerConfigLoader::onServerStarting);
    }

    public static EgoDynamicServerConfig current() {
        return current;
    }

    public static EgoDynamicServerConfig load(Path serverConfigRoot) {
        Path configDirectory = serverConfigRoot.resolve(ImmersiveEgo.MOD_ID);
        Path configPath = configDirectory.resolve(FILE_NAME);

        try {
            Files.createDirectories(configDirectory);
            if (Files.notExists(configPath)) {
                Files.createFile(configPath);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to prepare Immersive EGO dynamic config at " + configPath, exception);
        }

        try (CommentedFileConfig fileConfig = CommentedFileConfig.of(configPath)) {
            fileConfig.load();
            EgoDynamicServerConfig loaded = readAndRepair(fileConfig);
            fileConfig.save();
            return loaded;
        }
    }

    private static EgoDynamicServerConfig readAndRepair(CommentedConfig config) {
        boolean schedulerEnabled = readBoolean(
                config,
                SCHEDULER_ENABLED,
                EgoDynamicServerConfig.DEFAULT_SCHEDULER_ENABLED,
                "Enable the future server-authoritative EGO scheduler. Keep false until active simulation frames are implemented.");
        int schedulerIntervalTicks = readClampedInt(
                config,
                SCHEDULER_INTERVAL_TICKS,
                EgoDynamicServerConfig.DEFAULT_SCHEDULER_INTERVAL_TICKS,
                EgoDynamicServerConfig.MIN_SCHEDULER_INTERVAL_TICKS,
                EgoDynamicServerConfig.MAX_SCHEDULER_INTERVAL_TICKS,
                "Future scheduler interval in server ticks. Values outside the supported range are clamped.");
        double mirrorUpdateEpsilon = readClampedDouble(
                config,
                MIRROR_UPDATE_EPSILON,
                EgoDynamicServerConfig.DEFAULT_MIRROR_UPDATE_EPSILON,
                0.0D,
                1.0D,
                "Minimum normalized display-value delta before future scheduler-driven mirror updates are emitted.");

        return new EgoDynamicServerConfig(schedulerEnabled, schedulerIntervalTicks, mirrorUpdateEpsilon);
    }

    private static boolean readBoolean(CommentedConfig config, String path, boolean defaultValue, String comment) {
        config.setComment(path, comment);
        Object value = config.get(path);
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }

        config.set(path, defaultValue);
        return defaultValue;
    }

    private static int readClampedInt(CommentedConfig config, String path, int defaultValue, int min, int max, String comment) {
        config.setComment(path, comment);
        Object value = config.get(path);
        int parsed = value instanceof Number number ? number.intValue() : defaultValue;
        int clamped = Math.clamp(parsed, min, max);
        config.set(path, clamped);
        return clamped;
    }

    private static double readClampedDouble(CommentedConfig config, String path, double defaultValue, double min, double max, String comment) {
        config.setComment(path, comment);
        Object value = config.get(path);
        double parsed = value instanceof Number number ? number.doubleValue() : defaultValue;
        double clamped = Double.isFinite(parsed) ? Math.clamp(parsed, min, max) : defaultValue;
        config.set(path, clamped);
        return clamped;
    }

    private static void onServerStarting(ServerStartingEvent event) {
        Path serverConfigRoot = event.getServer()
                .getWorldPath(LevelResource.ROOT)
                .resolve("serverconfig");
        current = load(serverConfigRoot);
        ImmersiveEgo.LOGGER.info("Loaded Immersive EGO dynamic server config from {}", serverConfigRoot.resolve(ImmersiveEgo.MOD_ID).resolve(FILE_NAME));
    }
}
