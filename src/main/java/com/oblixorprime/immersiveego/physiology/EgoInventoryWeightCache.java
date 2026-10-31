package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class EgoInventoryWeightCache {
    private int refreshIntervalTicks;
    private final Map<UUID, EgoInventoryWeightSnapshot> snapshots = new HashMap<>();
    private EgoInventoryWeightDebugCounters counters = EgoInventoryWeightDebugCounters.empty();

    public EgoInventoryWeightCache(int refreshIntervalTicks) {
        this.refreshIntervalTicks = EgoDynamicServerConfig.clampTicks(refreshIntervalTicks);
    }

    public synchronized void setRefreshIntervalTicks(int refreshIntervalTicks) {
        this.refreshIntervalTicks = EgoDynamicServerConfig.clampTicks(refreshIntervalTicks);
    }

    public synchronized EgoInventoryWeightSnapshot snapshotFor(
            UUID playerId,
            long gameTime,
            Supplier<EgoInventoryWeightSnapshot> sampler) {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(sampler, "sampler");
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }

        counters = counters.recordRequest();
        EgoInventoryWeightSnapshot cached = snapshots.get(playerId);
        if (cached != null && isFresh(cached, gameTime)) {
            counters = counters.recordHit();
            return cached;
        }

        try {
            EgoInventoryWeightSnapshot refreshed = Objects.requireNonNull(sampler.get(), "sampler result");
            snapshots.put(playerId, refreshed);
            counters = counters.recordRefresh();
            return refreshed;
        } catch (RuntimeException exception) {
            counters = counters.recordError();
            if (cached != null) {
                return cached;
            }
            throw exception;
        }
    }

    public synchronized void recordSkippedRequest() {
        counters = counters.recordRequest().recordSkip();
    }

    public synchronized Optional<EgoInventoryWeightSnapshot> latest(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        return Optional.ofNullable(snapshots.get(playerId));
    }

    public synchronized void remove(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        if (snapshots.remove(playerId) != null) {
            counters = counters.recordEvictions(1);
        }
    }

    public synchronized int size() {
        return snapshots.size();
    }

    public synchronized EgoInventoryWeightDebugCounters counters() {
        return counters;
    }

    private boolean isFresh(EgoInventoryWeightSnapshot snapshot, long gameTime) {
        return gameTime >= snapshot.gameTime()
                && gameTime - snapshot.gameTime() < refreshIntervalTicks;
    }
}
