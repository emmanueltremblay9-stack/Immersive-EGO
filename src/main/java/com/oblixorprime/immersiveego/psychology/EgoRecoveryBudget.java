package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.data.EgoState;

public record EgoRecoveryBudget(
        double stressRelief,
        double focusRecovery,
        double comfortRecovery,
        double capacityCost) {
    public EgoRecoveryBudget {
        stressRelief = requireNonNegative("stressRelief", stressRelief);
        focusRecovery = requireNonNegative("focusRecovery", focusRecovery);
        comfortRecovery = requireNonNegative("comfortRecovery", comfortRecovery);
        capacityCost = requireNonNegative("capacityCost", capacityCost);
    }

    public static EgoRecoveryBudget allocate(EgoState state, double seconds, double resilienceMultiplier) {
        if (!Double.isFinite(seconds) || seconds < 0.0D) {
            throw new IllegalArgumentException("seconds must be finite and non-negative");
        }
        if (!Double.isFinite(resilienceMultiplier) || resilienceMultiplier <= 0.0D) {
            throw new IllegalArgumentException("resilienceMultiplier must be finite and positive");
        }

        double available = state.recoveryCapacity() * seconds * 0.012D * Math.clamp(resilienceMultiplier, 0.25D, 4.0D);
        double stressRelief = Math.min(state.stress(), available * 0.45D);
        double focusRecovery = Math.min(1.0D - state.focus(), available * 0.35D);
        double comfortRecovery = Math.min(1.0D - state.comfort(), available * 0.20D);
        return new EgoRecoveryBudget(
                stressRelief,
                focusRecovery,
                comfortRecovery,
                stressRelief + focusRecovery + comfortRecovery);
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
