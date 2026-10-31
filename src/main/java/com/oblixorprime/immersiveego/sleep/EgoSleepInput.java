package com.oblixorprime.immersiveego.sleep;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoSleepInput(
        EgoSleepContext context,
        double fatigue,
        double thermalStrain,
        double stress,
        double comfort) {
    public EgoSleepInput {
        context = Objects.requireNonNull(context, "context");
        fatigue = requireNormalized("fatigue", fatigue);
        thermalStrain = requireNormalized("thermalStrain", thermalStrain);
        stress = requireNormalized("stress", stress);
        comfort = requireNormalized("comfort", comfort);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return EgoMath.clamp01(value);
    }
}
