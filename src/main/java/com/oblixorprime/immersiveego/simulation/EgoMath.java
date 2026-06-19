package com.oblixorprime.immersiveego.simulation;

public final class EgoMath {
    private EgoMath() {
    }

    public static double clamp01(double value) {
        if (Double.isNaN(value)) {
            return 0.0D;
        }
        return Math.max(0.0D, Math.min(1.0D, value));
    }

    public static double weightedMean(double[] values, double[] weights) {
        if (values.length != weights.length) {
            throw new IllegalArgumentException("values and weights must have the same length");
        }
        double weightedSum = 0.0D;
        double totalWeight = 0.0D;
        for (int index = 0; index < values.length; index++) {
            double weight = weights[index];
            if (weight < 0.0D) {
                throw new IllegalArgumentException("weights must be non-negative");
            }
            weightedSum += clamp01(values[index]) * weight;
            totalWeight += weight;
        }
        return totalWeight == 0.0D ? 0.0D : clamp01(weightedSum / totalWeight);
    }
}
