package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;
import java.util.regex.Pattern;

public record EgoClimateProfile(
        String id,
        double heatLoad,
        double coldLoad,
        double humidity,
        double windExposure,
        double precipitationExposure,
        double shelterDemand) {
    private static final Pattern ID_PATTERN = Pattern.compile("[a-z0-9_./-]+");

    public EgoClimateProfile {
        id = requireId(id);
        heatLoad = requireNormalized("heatLoad", heatLoad);
        coldLoad = requireNormalized("coldLoad", coldLoad);
        humidity = requireNormalized("humidity", humidity);
        windExposure = requireNormalized("windExposure", windExposure);
        precipitationExposure = requireNormalized("precipitationExposure", precipitationExposure);
        shelterDemand = requireNormalized("shelterDemand", shelterDemand);
    }

    public double dominantThermalLoad() {
        return Math.max(heatLoad, coldLoad);
    }

    public double wetnessPressure() {
        return EgoMath.weightedMean(
                new double[] {humidity, precipitationExposure},
                new double[] {1.0D, 2.0D});
    }

    private static String requireId(String id) {
        Objects.requireNonNull(id, "id");
        if (!ID_PATTERN.matcher(id).matches()) {
            throw new IllegalArgumentException("climate profile id must use lowercase path characters");
        }
        return id;
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return value;
    }
}
