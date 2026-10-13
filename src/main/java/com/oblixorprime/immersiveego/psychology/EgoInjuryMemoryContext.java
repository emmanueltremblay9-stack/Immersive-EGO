package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoInjuryMemoryContext(
        double healthDeficit,
        int intervalTicks,
        double recoveryCapacity) {
    public EgoInjuryMemoryContext {
        healthDeficit = requireNormalized("healthDeficit", healthDeficit);
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
        recoveryCapacity = requireNormalized("recoveryCapacity", recoveryCapacity);
    }

    public double seconds() {
        return intervalTicks / 20.0D;
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
