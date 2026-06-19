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
        double maxWeight = 0.0D;
        for (double weight : weights) {
            if (!Double.isFinite(weight) || weight < 0.0D) {
                throw new IllegalArgumentException("weights must be finite and non-negative");
            }
            maxWeight = Math.max(maxWeight, weight);
        }
        if (maxWeight == 0.0D) {
            return 0.0D;
        }

        double weightedSum = 0.0D;
        double totalWeight = 0.0D;
        for (int index = 0; index < values.length; index++) {
            double normalizedWeight = weights[index] / maxWeight;
            weightedSum += clamp01(values[index]) * normalizedWeight;
            totalWeight += normalizedWeight;
        }
        return clamp01(weightedSum / totalWeight);
    }
}
