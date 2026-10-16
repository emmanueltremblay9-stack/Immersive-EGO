package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoEncumbranceHysteresis {
    private static final double LOADED_ENTER = 0.65D;
    private static final double LOADED_EXIT = 0.55D;
    private static final double STRAINED_ENTER = 0.85D;
    private static final double STRAINED_EXIT = 0.75D;
    private static final double OVERBURDENED_ENTER = 1.0D;
    private static final double OVERBURDENED_EXIT = 0.95D;

    private EgoEncumbranceHysteresis() {
    }

    public static EgoEncumbranceTier resolve(
            EgoEncumbranceTier previousTier,
            double loadRatio,
            double overburdenPressure) {
        Objects.requireNonNull(previousTier, "previousTier");
        loadRatio = requireNormalized("loadRatio", loadRatio);
        overburdenPressure = requireNormalized("overburdenPressure", overburdenPressure);

        return switch (previousTier) {
            case NORMAL -> upwardTier(loadRatio, overburdenPressure);
            case LOADED -> loadedTier(loadRatio, overburdenPressure);
            case STRAINED -> strainedTier(loadRatio, overburdenPressure);
            case OVERBURDENED -> overburdenedTier(loadRatio, overburdenPressure);
        };
    }

    private static EgoEncumbranceTier upwardTier(double loadRatio, double overburdenPressure) {
        if (overburdenPressure > 0.0D || loadRatio >= OVERBURDENED_ENTER) {
            return EgoEncumbranceTier.OVERBURDENED;
        }
        if (loadRatio >= STRAINED_ENTER) {
            return EgoEncumbranceTier.STRAINED;
        }
        if (loadRatio >= LOADED_ENTER) {
            return EgoEncumbranceTier.LOADED;
        }
        return EgoEncumbranceTier.NORMAL;
    }

    private static EgoEncumbranceTier loadedTier(double loadRatio, double overburdenPressure) {
        if (loadRatio <= LOADED_EXIT) {
            return EgoEncumbranceTier.NORMAL;
        }
        return upwardTier(loadRatio, overburdenPressure).ordinal() > EgoEncumbranceTier.LOADED.ordinal()
                ? upwardTier(loadRatio, overburdenPressure)
                : EgoEncumbranceTier.LOADED;
    }

    private static EgoEncumbranceTier strainedTier(double loadRatio, double overburdenPressure) {
        if (overburdenPressure > 0.0D || loadRatio >= OVERBURDENED_ENTER) {
            return EgoEncumbranceTier.OVERBURDENED;
        }
        if (loadRatio <= STRAINED_EXIT) {
            return EgoEncumbranceTier.LOADED;
        }
        return EgoEncumbranceTier.STRAINED;
    }

    private static EgoEncumbranceTier overburdenedTier(double loadRatio, double overburdenPressure) {
        if (overburdenPressure == 0.0D && loadRatio <= OVERBURDENED_EXIT) {
            return EgoEncumbranceTier.STRAINED;
        }
        return EgoEncumbranceTier.OVERBURDENED;
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
