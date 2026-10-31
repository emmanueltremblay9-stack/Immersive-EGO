package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class EgoInventoryWeightCalculatorTest {
    private static final UUID PLAYER_ID = new UUID(3L, 4L);

    @Test
    void resolvesDefaultWeightsFromStackShapeAndItemId() {
        EgoWeightedStack sword = new EgoWeightedStack("minecraft:diamond_sword", 1, 1, true);
        EgoWeightedStack stone = new EgoWeightedStack("minecraft:stone", 64, 64, false);
        EgoWeightedStack pearl = new EgoWeightedStack("minecraft:ender_pearl", 16, 16, false);
        EgoWeightedStack seeds = new EgoWeightedStack("minecraft:wheat_seeds", 64, 64, false);

        assertEquals(4.0D, EgoItemWeightResolver.unitWeight(sword), 1.0E-12D);
        assertEquals(4.0D, EgoItemWeightResolver.stackWeight(sword), 1.0E-12D);
        assertEquals(1.0D, EgoItemWeightResolver.unitWeight(stone), 1.0E-12D);
        assertEquals(64.0D, EgoItemWeightResolver.stackWeight(stone), 1.0E-12D);
        assertEquals(0.25D, EgoItemWeightResolver.unitWeight(pearl), 1.0E-12D);
        assertEquals(4.0D, EgoItemWeightResolver.stackWeight(pearl), 1.0E-12D);
        assertEquals(0.10D, EgoItemWeightResolver.unitWeight(seeds), 1.0E-12D);
        assertEquals(6.4D, EgoItemWeightResolver.stackWeight(seeds), 1.0E-12D);
    }

    @Test
    void calculatesLoadRatioFromInventoryWeightAndLoadTolerance() {
        List<EgoWeightedStack> stacks = List.of(
                new EgoWeightedStack("minecraft:stone", 64, 64, false),
                new EgoWeightedStack("minecraft:diamond_sword", 1, 1, true),
                new EgoWeightedStack("minecraft:ender_pearl", 16, 16, false));

        EgoInventoryWeightSnapshot normal = EgoInventoryWeightCalculator.calculate(100L, stacks, 1.0D);
        EgoInventoryWeightSnapshot tolerant = EgoInventoryWeightCalculator.calculate(100L, stacks, 2.0D);

        assertEquals(72.0D, normal.totalWeight(), 1.0E-12D);
        assertEquals(200.0D, normal.capacity(), 1.0E-12D);
        assertEquals(0.36D, normal.loadRatio(), 1.0E-12D);
        assertEquals(0.0D, normal.overburdenPressure(), 1.0E-12D);
        assertEquals(3, normal.stackCount());
        assertEquals(400.0D, tolerant.capacity(), 1.0E-12D);
        assertEquals(0.18D, tolerant.loadRatio(), 1.0E-12D);
    }

    @Test
    void overburdenPressureClampsWhenInventoryExceedsCapacity() {
        List<EgoWeightedStack> stacks = List.of(
                new EgoWeightedStack("minecraft:stone", 64, 64, false),
                new EgoWeightedStack("minecraft:cobblestone", 64, 64, false),
                new EgoWeightedStack("minecraft:deepslate", 64, 64, false),
                new EgoWeightedStack("minecraft:dirt", 64, 64, false));

        EgoInventoryWeightSnapshot snapshot = EgoInventoryWeightCalculator.calculate(200L, stacks, 0.25D);

        assertEquals(256.0D, snapshot.totalWeight(), 1.0E-12D);
        assertEquals(50.0D, snapshot.capacity(), 1.0E-12D);
        assertEquals(1.0D, snapshot.loadRatio(), 1.0E-12D);
        assertEquals(1.0D, snapshot.overburdenPressure(), 1.0E-12D);
    }

    @Test
    void cacheRefreshesThenHitsUntilIntervalExpires() {
        EgoInventoryWeightCache cache = new EgoInventoryWeightCache(20);
        AtomicInteger samples = new AtomicInteger();

        EgoInventoryWeightSnapshot first = cache.snapshotFor(PLAYER_ID, 100L, () -> {
            samples.incrementAndGet();
            return snapshot(100L, 10.0D);
        });
        EgoInventoryWeightSnapshot second = cache.snapshotFor(PLAYER_ID, 119L, () -> {
            samples.incrementAndGet();
            return snapshot(119L, 20.0D);
        });
        EgoInventoryWeightSnapshot third = cache.snapshotFor(PLAYER_ID, 120L, () -> {
            samples.incrementAndGet();
            return snapshot(120L, 20.0D);
        });

        assertSame(first, second);
        assertEquals(20.0D, third.totalWeight(), 1.0E-12D);
        assertEquals(2, samples.get());
        assertEquals(new EgoInventoryWeightDebugCounters(3L, 2L, 1L, 0L, 0L, 0L), cache.counters());
    }

    @Test
    void cacheFallsBackToPreviousSnapshotOnSamplerFailure() {
        EgoInventoryWeightCache cache = new EgoInventoryWeightCache(1);
        EgoInventoryWeightSnapshot cached = cache.snapshotFor(PLAYER_ID, 10L, () -> snapshot(10L, 10.0D));

        EgoInventoryWeightSnapshot result = cache.snapshotFor(PLAYER_ID, 11L, () -> {
            throw new IllegalStateException("boom");
        });

        assertSame(cached, result);
        assertEquals(new EgoInventoryWeightDebugCounters(2L, 1L, 0L, 0L, 1L, 0L), cache.counters());
    }

    @Test
    void rejectsInvalidInputs() {
        assertThrows(NullPointerException.class, () -> new EgoWeightedStack(null, 1, 1, false));
        assertThrows(IllegalArgumentException.class, () -> new EgoWeightedStack("Bad", 1, 1, false));
        assertThrows(IllegalArgumentException.class, () -> new EgoWeightedStack("minecraft:stone", 0, 64, false));
        assertThrows(IllegalArgumentException.class, () -> new EgoWeightedStack("minecraft:stone", 1, 0, false));
        assertThrows(IllegalArgumentException.class, () -> EgoInventoryWeightCalculator.calculate(-1L, List.of(), 1.0D));
        assertThrows(IllegalArgumentException.class, () -> EgoInventoryWeightCalculator.calculate(0L, List.of(), 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoInventoryWeightDebugCounters(-1L, 0L, 0L, 0L, 0L, 0L));
        assertThrows(NullPointerException.class, () -> new EgoInventoryWeightCache(20).snapshotFor(null, 0L, () -> snapshot(0L, 0.0D)));
    }

    private static EgoInventoryWeightSnapshot snapshot(long gameTime, double totalWeight) {
        return EgoInventoryWeightSnapshot.from(gameTime, totalWeight, 200.0D, totalWeight == 0.0D ? 0 : 1);
    }
}
