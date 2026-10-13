package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoEncumbranceContext(
        EgoEncumbranceTier tier,
        double loadRatio,
        double overburdenPressure,
        int intervalTicks) {
    public EgoEncumbranceContext {
        tier = Objects.requireNonNull(tier, "tier");
        loadRatio = requireNormalized("loadRatio", loadRatio);
        overburdenPressure = requireNormalized("overburdenPressure", overburdenPressure);
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
    }

    double seconds() {
        return intervalTicks / 20.0D;
    }

    double effectivePressure() {
        return Math.max(tier.pressure(), overburdenPressure);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
