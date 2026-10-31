package com.oblixorprime.immersiveego.origins;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoAcclimationContext(
        int intervalTicks,
        EgoOriginProfile profile,
        double heatExposure,
        double coldExposure,
        double altitudeExposure,
        double humidityExposure,
        double acclimationGain) {
    public EgoAcclimationContext {
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
        profile = Objects.requireNonNull(profile, "profile");
        heatExposure = requireNormalized("heatExposure", heatExposure);
        coldExposure = requireNormalized("coldExposure", coldExposure);
        altitudeExposure = requireNormalized("altitudeExposure", altitudeExposure);
        humidityExposure = requireNormalized("humidityExposure", humidityExposure);
        if (!Double.isFinite(acclimationGain) || acclimationGain <= 0.0D) {
            throw new IllegalArgumentException("acclimationGain must be finite and positive");
        }
    }

    public double step() {
        return Math.min(1.0D, intervalTicks / 20.0D * 0.012D * Math.clamp(acclimationGain, 0.25D, 4.0D));
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
