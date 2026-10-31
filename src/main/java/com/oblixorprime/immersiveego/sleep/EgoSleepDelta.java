package com.oblixorprime.immersiveego.sleep;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoSleepDelta(
        EgoCircadianPhase circadianPhase,
        boolean sleeping,
        boolean nap,
        boolean phantomRestQualified,
        double sleepQuality,
        double pressureGain,
        double fatigueRecovery,
        double recoveryCapacityGain,
        double focusRecovery,
        double comfortRecovery) {
    public EgoSleepDelta {
        circadianPhase = Objects.requireNonNull(circadianPhase, "circadianPhase");
        sleepQuality = requireNormalized("sleepQuality", sleepQuality);
        pressureGain = requireNonNegative("pressureGain", pressureGain);
        fatigueRecovery = requireNonNegative("fatigueRecovery", fatigueRecovery);
        recoveryCapacityGain = requireNonNegative("recoveryCapacityGain", recoveryCapacityGain);
        focusRecovery = requireNonNegative("focusRecovery", focusRecovery);
        comfortRecovery = requireNonNegative("comfortRecovery", comfortRecovery);
    }

    public double netFatigueChange() {
        return pressureGain - fatigueRecovery;
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
