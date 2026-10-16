package com.oblixorprime.immersiveego.physiology;

import java.util.List;
import java.util.Objects;

public final class EgoInventoryWeightCalculator {
    public static final double BASE_CAPACITY = 200.0D;

    private EgoInventoryWeightCalculator() {
    }

    public static EgoInventoryWeightSnapshot calculate(
            long gameTime,
            List<EgoWeightedStack> stacks,
            double loadTolerance) {
        Objects.requireNonNull(stacks, "stacks");
        if (!Double.isFinite(loadTolerance) || loadTolerance <= 0.0D) {
            throw new IllegalArgumentException("loadTolerance must be finite and positive");
        }
        double totalWeight = 0.0D;
        for (EgoWeightedStack stack : stacks) {
            totalWeight += EgoItemWeightResolver.stackWeight(Objects.requireNonNull(stack, "stack"));
        }
        double capacity = BASE_CAPACITY * Math.clamp(loadTolerance, 0.25D, 4.0D);
        return EgoInventoryWeightSnapshot.from(gameTime, totalWeight, capacity, stacks.size());
    }
}
