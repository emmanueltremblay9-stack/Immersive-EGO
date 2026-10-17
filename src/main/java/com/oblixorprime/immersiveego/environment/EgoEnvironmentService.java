package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoader;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import com.oblixorprime.immersiveego.wetness.EgoWetnessRuntime;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;

public final class EgoEnvironmentService {
    public static final String ENVIRONMENT_MODULE_KEY = "environment";

    private static final EgoEnvironmentCache CACHE =
            new EgoEnvironmentCache(EgoDynamicServerConfig.DEFAULT_SCHEDULER_INTERVAL_TICKS);

    private EgoEnvironmentService() {
    }

    public static Optional<EgoEnvironmentSnapshot> refreshPlayer(ServerPlayer player, long gameTime) {
        if (!environmentModuleEnabled()) {
            CACHE.recordSkippedRequest();
            return Optional.empty();
        }
        CACHE.setRefreshIntervalTicks(EgoDynamicServerConfigLoader.current().schedulerIntervalTicks());
        return Optional.of(snapshotFor(player, gameTime));
    }

    public static EgoEnvironmentSnapshot debugSnapshot(ServerPlayer player) {
        return snapshotFor(player, player.serverLevel().getGameTime());
    }

    public static Optional<EgoEnvironmentSnapshot> latest(ServerPlayer player) {
        return CACHE.latest(player.getUUID()).map(snapshot -> currentThermal(player, snapshot));
    }

    public static void remove(UUID playerId) {
        CACHE.remove(playerId);
    }

    public static EgoEnvironmentDebugCounters counters() {
        return CACHE.counters();
    }

    public static boolean environmentModuleEnabled() {
        return EgoServerModuleConfigs.isEnabled(ENVIRONMENT_MODULE_KEY);
    }

    private static EgoEnvironmentSnapshot currentThermal(ServerPlayer player, EgoEnvironmentSnapshot raw) {
        return new EgoEnvironmentSnapshot(raw.gameTime(), raw.exposureSample(),
                EgoThermalSnapshotCalculator.calculate(raw.exposureSample(), EgoWetnessRuntime.thermalWetness(player),
                        player.getAttributeValue(EgoAttributes.THERMAL_TOLERANCE)));
    }

    private static EgoEnvironmentSnapshot snapshotFor(ServerPlayer player, long gameTime) {
        EgoEnvironmentSnapshot raw = CACHE.snapshotFor(
                player.getUUID(),
                gameTime,
                () -> EgoEnvironmentSampler.sample(player));
        return currentThermal(player, raw);
    }
}
