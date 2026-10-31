package com.oblixorprime.immersiveego.physiology;

import java.util.Objects;
import java.util.regex.Pattern;

public record EgoDrinkProfile(
        String id,
        EgoWaterQuality waterQuality,
        double hydrationGain,
        double electrolyteSupport,
        double contaminationRisk) {
    private static final Pattern ID_PATTERN = Pattern.compile("[a-z0-9_./:-]+");

    public EgoDrinkProfile {
        id = requireId(id);
        Objects.requireNonNull(waterQuality, "waterQuality");
        hydrationGain = requireNormalized("hydrationGain", hydrationGain);
        electrolyteSupport = requireNormalized("electrolyteSupport", electrolyteSupport);
        contaminationRisk = requireNormalized("contaminationRisk", contaminationRisk);
    }

    private static String requireId(String id) {
        Objects.requireNonNull(id, "id");
        if (!ID_PATTERN.matcher(id).matches()) {
            throw new IllegalArgumentException("drink profile id must use lowercase resource characters");
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
