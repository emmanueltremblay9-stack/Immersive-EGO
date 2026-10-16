package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoStaminaActivityContext(
        double movementIntensity,
        boolean sprinting,
        boolean swimming,
        boolean airborne,
        boolean usingItem,
        int intervalTicks,
        double staminaCapacity) {
    public EgoStaminaActivityContext {
        movementIntensity = requireNormalized("movementIntensity", movementIntensity);
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
        if (!Double.isFinite(staminaCapacity) || staminaCapacity <= 0.0D) {
            throw new IllegalArgumentException("staminaCapacity must be finite and positive");
        }
    }

    double seconds() {
        return intervalTicks / 20.0D;
    }

    double costMultiplier() {
        return 1.0D / Math.clamp(staminaCapacity, 0.25D, 4.0D);
    }

    double recoveryMultiplier() {
        return Math.clamp(staminaCapacity, 0.25D, 4.0D);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
