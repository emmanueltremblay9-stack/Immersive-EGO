package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public final class EgoEnvironmentCache {
    private int refreshIntervalTicks;
    private final Map<UUID, EgoEnvironmentSnapshot> snapshots = new HashMap<>();
    private EgoEnvironmentDebugCounters counters = EgoEnvironmentDebugCounters.empty();

    public EgoEnvironmentCache(int refreshIntervalTicks) {
        this.refreshIntervalTicks = EgoDynamicServerConfig.clampTicks(refreshIntervalTicks);
    }

    public synchronized void setRefreshIntervalTicks(int refreshIntervalTicks) {
        this.refreshIntervalTicks = EgoDynamicServerConfig.clampTicks(refreshIntervalTicks);
    }

    public synchronized EgoEnvironmentSnapshot snapshotFor(
            UUID playerId,
            long gameTime,
            Supplier<EgoExposureSample> sampler) {
        Objects.requireNonNull(playerId, "playerId");
        Objects.requireNonNull(sampler, "sampler");
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }

        counters = counters.recordRequest();
        EgoEnvironmentSnapshot cached = snapshots.get(playerId);
        if (cached != null && isFresh(cached, gameTime)) {
            counters = counters.recordHit();
            return cached;
        }

        try {
            EgoEnvironmentSnapshot refreshed = EgoEnvironmentSnapshot.from(
                    gameTime,
                    Objects.requireNonNull(sampler.get(), "sampler result"));
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

    public synchronized Optional<EgoEnvironmentSnapshot> latest(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        return Optional.ofNullable(snapshots.get(playerId));
    }

    public synchronized void remove(UUID playerId) {
        Objects.requireNonNull(playerId, "playerId");
        if (snapshots.remove(playerId) != null) {
            counters = counters.recordEvictions(1);
        }
    }

    public synchronized void clear() {
        int removed = snapshots.size();
        snapshots.clear();
        counters = counters.recordEvictions(removed);
    }

    public synchronized int size() {
        return snapshots.size();
    }

    public synchronized EgoEnvironmentDebugCounters counters() {
        return counters;
    }

    private boolean isFresh(EgoEnvironmentSnapshot snapshot, long gameTime) {
        return gameTime >= snapshot.gameTime()
                && gameTime - snapshot.gameTime() < refreshIntervalTicks;
    }
}
