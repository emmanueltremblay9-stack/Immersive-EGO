package com.oblixorprime.immersiveego.wetness;

/** The attachment is the sole owner of persistent body-surface wetness, never the cache. */
public record EgoWetnessState(int schemaVersion, double wetness, boolean quarantined) {
    public static final int CURRENT_SCHEMA = 1;

    public EgoWetnessState {
        if (!Double.isFinite(wetness) || wetness < 0.0D || wetness > 1.0D) {
            throw new IllegalArgumentException("Wetness must be finite and in [0,1]");
        }
    }

    public static EgoWetnessState dry() {
        return new EgoWetnessState(CURRENT_SCHEMA, 0.0D, false);
    }

    public boolean supported() {
        return schemaVersion == CURRENT_SCHEMA && !quarantined;
    }

    public EgoWetnessState withWetness(double value) {
        return new EgoWetnessState(schemaVersion, value, quarantined);
    }
}
