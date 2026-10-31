package com.oblixorprime.immersiveego.physiology;

public record EgoHydrationDemand(
        double baseline,
        double thermal,
        double respiratory,
        double fatigue,
        double efficiencyMultiplier,
        double total) {
    public EgoHydrationDemand {
        baseline = requireNonNegative("baseline", baseline);
        thermal = requireNonNegative("thermal", thermal);
        respiratory = requireNonNegative("respiratory", respiratory);
        fatigue = requireNonNegative("fatigue", fatigue);
        efficiencyMultiplier = requireNonNegative("efficiencyMultiplier", efficiencyMultiplier);
        total = requireNonNegative("total", total);
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
