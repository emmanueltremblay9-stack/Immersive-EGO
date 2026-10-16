package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoHydrationDemandInput(
        double thermalStrain,
        double respiratoryStrain,
        double fatigue,
        int intervalTicks,
        double hydrationEfficiency) {
    public EgoHydrationDemandInput {
        thermalStrain = requireNormalized("thermalStrain", thermalStrain);
        respiratoryStrain = requireNormalized("respiratoryStrain", respiratoryStrain);
        fatigue = requireNormalized("fatigue", fatigue);
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
        if (!Double.isFinite(hydrationEfficiency) || hydrationEfficiency <= 0.0D) {
            throw new IllegalArgumentException("hydrationEfficiency must be finite and positive");
        }
    }

    double seconds() {
        return intervalTicks / 20.0D;
    }

    double efficiencyMultiplier() {
        return 1.0D / Math.clamp(hydrationEfficiency, 0.25D, 4.0D);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
