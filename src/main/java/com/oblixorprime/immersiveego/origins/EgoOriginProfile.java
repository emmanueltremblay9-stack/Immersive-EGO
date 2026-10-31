package com.oblixorprime.immersiveego.origins;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Locale;
import java.util.regex.Pattern;

public record EgoOriginProfile(
        String id,
        double heatAffinity,
        double coldAffinity,
        double altitudeAffinity,
        double humidityAffinity) {
    private static final Pattern ID_PATTERN = Pattern.compile("[a-z][a-z0-9_]{1,63}");

    public EgoOriginProfile {
        if (id == null || !ID_PATTERN.matcher(id).matches()) {
            throw new IllegalArgumentException("origin profile id must match " + ID_PATTERN.pattern());
        }
        id = id.toLowerCase(Locale.ROOT);
        heatAffinity = requireNormalized("heatAffinity", heatAffinity);
        coldAffinity = requireNormalized("coldAffinity", coldAffinity);
        altitudeAffinity = requireNormalized("altitudeAffinity", altitudeAffinity);
        humidityAffinity = requireNormalized("humidityAffinity", humidityAffinity);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
