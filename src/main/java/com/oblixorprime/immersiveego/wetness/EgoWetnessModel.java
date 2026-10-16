package com.oblixorprime.immersiveego.wetness;

import com.oblixorprime.immersiveego.environment.EgoClimateProfile;
import java.util.Objects;

/** Provisional gameplay rates (not physical coefficients or a balance claim). */
public final class EgoWetnessModel {
    public static final double DRY_ZERO = 0.001D;
    public static final double IMMERSION_RATE = 1.4D;
    public static final double RAIN_RATE = 0.08D;

    private EgoWetnessModel() {}

    public record Step(EgoWetnessState state, double seconds, double acquisition, double drying, double rate) {}

    public static Step advance(EgoWetnessState state, EgoWetnessExposure exposure,
            EgoClimateProfile climate, double seconds) {
        Objects.requireNonNull(state);
        Objects.requireNonNull(exposure);
        Objects.requireNonNull(climate);
        if (!Double.isFinite(seconds) || seconds < 0.0D) {
            throw new IllegalArgumentException("Consumed simulation duration must be finite and nonnegative");
        }
        if (!state.supported() || seconds == 0.0D) {
            return new Step(state, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        double before = state.wetness();
        double rate = switch (exposure) {
            case IMMERSION -> IMMERSION_RATE;
            case RAIN -> RAIN_RATE;
            case DRY -> dryingRate(climate);
        };
        double after = exposure == EgoWetnessExposure.DRY
                ? before * Math.exp(-rate * seconds)
                : before + (1.0D - before) * -Math.expm1(-rate * seconds);
        if (exposure == EgoWetnessExposure.DRY && after <= DRY_ZERO) {
            after = 0.0D;
        }
        after = Math.clamp(after, 0.0D, 1.0D);
        return new Step(state.withWetness(after), seconds,
                Math.max(0.0D, after - before), Math.max(0.0D, before - after), rate);
    }

    public static double dryingRate(EgoClimateProfile climate) {
        return Math.clamp(0.035D * (1.0D + climate.heatLoad() * 0.8D
                - climate.humidity() * 0.5D - climate.wetnessPressure() * 0.35D), 0.02D, 0.08D);
    }
}
