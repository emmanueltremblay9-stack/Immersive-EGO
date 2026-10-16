package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoInventoryWeightSnapshot(
        long gameTime,
        double totalWeight,
        double capacity,
        double loadRatio,
        double overburdenPressure,
        int stackCount) {
    public EgoInventoryWeightSnapshot {
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }
        totalWeight = requireNonNegative("totalWeight", totalWeight);
        if (!Double.isFinite(capacity) || capacity <= 0.0D) {
            throw new IllegalArgumentException("capacity must be finite and positive");
        }
        loadRatio = requireNormalized("loadRatio", loadRatio);
        overburdenPressure = requireNormalized("overburdenPressure", overburdenPressure);
        if (stackCount < 0) {
            throw new IllegalArgumentException("stackCount must be non-negative");
        }
    }

    public static EgoInventoryWeightSnapshot from(
            long gameTime,
            double totalWeight,
            double capacity,
            int stackCount) {
        double loadRatio = EgoMath.clamp01(totalWeight / capacity);
        double overburdenPressure = EgoMath.clamp01((totalWeight - capacity) / capacity);
        return new EgoInventoryWeightSnapshot(
                gameTime,
                totalWeight,
                capacity,
                loadRatio,
                overburdenPressure,
                stackCount);
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
