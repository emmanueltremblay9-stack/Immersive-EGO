package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoader;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;

public final class EgoInventoryWeightService {
    private static final EgoInventoryWeightCache CACHE =
            new EgoInventoryWeightCache(EgoDynamicServerConfig.DEFAULT_SCHEDULER_INTERVAL_TICKS);

    private EgoInventoryWeightService() {
    }

    public static Optional<EgoInventoryWeightSnapshot> refreshPlayer(ServerPlayer player, long gameTime) {
        if (!EgoEncumbranceModule.enabled()) {
            CACHE.recordSkippedRequest();
            return Optional.empty();
        }
        CACHE.setRefreshIntervalTicks(EgoDynamicServerConfigLoader.current().schedulerIntervalTicks());
        return Optional.of(CACHE.snapshotFor(
                player.getUUID(),
                gameTime,
                () -> EgoInventoryWeightSampler.sample(player, gameTime)));
    }

    public static Optional<EgoInventoryWeightSnapshot> latest(ServerPlayer player) {
        return CACHE.latest(player.getUUID());
    }

    public static EgoInventoryWeightDebugCounters counters() {
        return CACHE.counters();
    }

    public static void remove(UUID playerId) {
        CACHE.remove(playerId);
    }
}
