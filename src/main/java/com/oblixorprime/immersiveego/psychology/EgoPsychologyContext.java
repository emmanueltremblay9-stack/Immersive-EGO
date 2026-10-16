package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoPsychologyContext(
        double fearPressure,
        double painPressure,
        double stabilitySupport,
        double comfortSupport,
        int intervalTicks,
        double stressResilience) {
    public EgoPsychologyContext {
        fearPressure = requireNormalized("fearPressure", fearPressure);
        painPressure = requireNormalized("painPressure", painPressure);
        stabilitySupport = requireNormalized("stabilitySupport", stabilitySupport);
        comfortSupport = requireNormalized("comfortSupport", comfortSupport);
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
        if (!Double.isFinite(stressResilience) || stressResilience <= 0.0D) {
            throw new IllegalArgumentException("stressResilience must be finite and positive");
        }
    }

    public double seconds() {
        return intervalTicks / 20.0D;
    }

    public double resilienceMultiplier() {
        return Math.clamp(stressResilience, 0.25D, 4.0D);
    }

    public double vulnerabilityMultiplier() {
        return 1.0D / resilienceMultiplier();
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
