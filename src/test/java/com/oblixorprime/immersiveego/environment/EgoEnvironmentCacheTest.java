package com.oblixorprime.immersiveego.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class EgoEnvironmentCacheTest {
    private static final UUID PLAYER_ID = new UUID(1L, 2L);

    @Test
    void firstRequestRefreshesThenHitsUntilIntervalExpires() {
        EgoEnvironmentCache cache = new EgoEnvironmentCache(20);
        AtomicInteger samples = new AtomicInteger();

        EgoEnvironmentSnapshot first = cache.snapshotFor(PLAYER_ID, 100L, () -> {
            samples.incrementAndGet();
            return sample(EgoClimateProfileCatalog.TEMPERATE);
        });
        EgoEnvironmentSnapshot second = cache.snapshotFor(PLAYER_ID, 119L, () -> {
            samples.incrementAndGet();
            return sample(EgoClimateProfileCatalog.HOT_DRY);
        });
        EgoEnvironmentSnapshot third = cache.snapshotFor(PLAYER_ID, 120L, () -> {
            samples.incrementAndGet();
            return sample(EgoClimateProfileCatalog.HOT_DRY);
        });

        assertSame(first, second);
        assertEquals(EgoClimateProfileCatalog.HOT_DRY, third.exposureSample().climateProfile().id());
        assertEquals(2, samples.get());
        assertEquals(new EgoEnvironmentDebugCounters(3L, 2L, 1L, 0L, 0L, 0L), cache.counters());
    }

    @Test
    void samplerFailureFallsBackToCachedSnapshotAndRecordsError() {
        EgoEnvironmentCache cache = new EgoEnvironmentCache(1);
        EgoEnvironmentSnapshot cached = cache.snapshotFor(PLAYER_ID, 10L, () -> sample(EgoClimateProfileCatalog.COLD_WET));

        EgoEnvironmentSnapshot result = cache.snapshotFor(PLAYER_ID, 11L, () -> {
            throw new IllegalStateException("boom");
        });

        assertSame(cached, result);
        assertEquals(new EgoEnvironmentDebugCounters(2L, 1L, 0L, 0L, 1L, 0L), cache.counters());
    }

    @Test
    void samplerFailureWithoutCachedSnapshotIsRethrown() {
        EgoEnvironmentCache cache = new EgoEnvironmentCache(20);

        assertThrows(IllegalStateException.class, () -> cache.snapshotFor(PLAYER_ID, 1L, () -> {
            throw new IllegalStateException("boom");
        }));
        assertEquals(new EgoEnvironmentDebugCounters(1L, 0L, 0L, 0L, 1L, 0L), cache.counters());
    }

    @Test
    void skippedRequestsAndEvictionsAreCountedWithoutSampling() {
        EgoEnvironmentCache cache = new EgoEnvironmentCache(20);
        cache.recordSkippedRequest();
        cache.snapshotFor(PLAYER_ID, 1L, () -> sample(EgoClimateProfileCatalog.TEMPERATE));
        cache.remove(PLAYER_ID);

        assertEquals(0, cache.size());
        assertEquals(new EgoEnvironmentDebugCounters(2L, 1L, 0L, 1L, 0L, 1L), cache.counters());
    }

    @Test
    void rejectsInvalidInputs() {
        EgoEnvironmentCache cache = new EgoEnvironmentCache(20);

        assertThrows(NullPointerException.class, () -> cache.snapshotFor(null, 0L, () -> sample(EgoClimateProfileCatalog.TEMPERATE)));
        assertThrows(NullPointerException.class, () -> cache.snapshotFor(PLAYER_ID, 0L, null));
        assertThrows(IllegalArgumentException.class, () -> cache.snapshotFor(PLAYER_ID, -1L, () -> sample(EgoClimateProfileCatalog.TEMPERATE)));
        assertThrows(IllegalArgumentException.class, () -> new EgoEnvironmentDebugCounters(-1L, 0L, 0L, 0L, 0L, 0L));
    }

    @Test
    void setRefreshIntervalTicksAffectsFreshness() {
        EgoEnvironmentCache cache = new EgoEnvironmentCache(20);
        AtomicInteger samples = new AtomicInteger();

        cache.snapshotFor(PLAYER_ID, 0L, () -> {
            samples.incrementAndGet();
            return sample(EgoClimateProfileCatalog.COLD_DRY);
        });
        cache.snapshotFor(PLAYER_ID, 19L, () -> {
            samples.incrementAndGet();
            return sample(EgoClimateProfileCatalog.HOT_DRY);
        });

        cache.setRefreshIntervalTicks(5);
        cache.snapshotFor(PLAYER_ID, 24L, () -> {
            samples.incrementAndGet();
            return sample(EgoClimateProfileCatalog.TEMPERATE);
        });

        assertEquals(2, samples.get());
    }

    private static EgoExposureSample sample(String profileId) {
        return new EgoExposureSample(
                EgoClimateProfileCatalog.defaultById(profileId).orElseThrow(),
                EgoSeasonPhase.SPRING,
                0.5D,
                0.5D,
                0.0D);
    }
}
