package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class EgoInventoryWeightCacheTest {
    private static final UUID PLAYER_ID = new UUID(1L, 2L);

    @Test
    void setRefreshIntervalTicksAffectsFreshness() {
        EgoInventoryWeightCache cache = new EgoInventoryWeightCache(20);
        AtomicInteger samples = new AtomicInteger();

        cache.snapshotFor(PLAYER_ID, 0L, () -> {
            samples.incrementAndGet();
            return sample(0.0D, 100.0D, 1);
        });
        cache.snapshotFor(PLAYER_ID, 19L, () -> {
            samples.incrementAndGet();
            return sample(10.0D, 100.0D, 1);
        });

        cache.setRefreshIntervalTicks(5);
        cache.snapshotFor(PLAYER_ID, 24L, () -> {
            samples.incrementAndGet();
            return sample(30.0D, 100.0D, 1);
        });

        assertEquals(2, samples.get());
    }

    private static EgoInventoryWeightSnapshot sample(double totalWeight, double capacity, int stackCount) {
        return EgoInventoryWeightSnapshot.from(10L, totalWeight, capacity, stackCount);
    }
}
