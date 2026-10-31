package com.oblixorprime.immersiveego.physiology;

public record EgoEncumbranceLoadDelta(
        double staminaCost,
        double fatigueGain) {
    public EgoEncumbranceLoadDelta {
        staminaCost = requireNonNegative("staminaCost", staminaCost);
        fatigueGain = requireNonNegative("fatigueGain", fatigueGain);
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
