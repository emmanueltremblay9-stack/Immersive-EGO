package com.oblixorprime.immersiveego.environment;

import java.util.Objects;

public record EgoExposureSample(
        EgoClimateProfile climateProfile,
        EgoSeasonPhase seasonPhase,
        double skyExposure,
        double shelter,
        double wetness) {
    public EgoExposureSample {
        Objects.requireNonNull(climateProfile, "climateProfile");
        Objects.requireNonNull(seasonPhase, "seasonPhase");
        skyExposure = requireNormalized("skyExposure", skyExposure);
        shelter = requireNormalized("shelter", shelter);
        wetness = requireNormalized("wetness", wetness);
    }

    public double unshelteredExposure() {
        return skyExposure * (1.0D - shelter);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return value;
    }
}
