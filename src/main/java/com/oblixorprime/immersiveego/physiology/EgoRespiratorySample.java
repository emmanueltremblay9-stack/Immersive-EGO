package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoRespiratorySample(
        double altitudePressure,
        double depthPressure,
        double submersionPressure,
        double dimensionPressure,
        double respirationEfficiency) {
    public EgoRespiratorySample {
        altitudePressure = requireNormalized("altitudePressure", altitudePressure);
        depthPressure = requireNormalized("depthPressure", depthPressure);
        submersionPressure = requireNormalized("submersionPressure", submersionPressure);
        dimensionPressure = requireNormalized("dimensionPressure", dimensionPressure);
        if (!Double.isFinite(respirationEfficiency) || respirationEfficiency <= 0.0D) {
            throw new IllegalArgumentException("respirationEfficiency must be finite and positive");
        }
    }

    public double dominantPressure() {
        return Math.max(
                Math.max(altitudePressure, depthPressure),
                Math.max(submersionPressure, dimensionPressure));
    }

    double efficiencyMultiplier() {
        return 1.0D / Math.clamp(respirationEfficiency, 0.25D, 4.0D);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
