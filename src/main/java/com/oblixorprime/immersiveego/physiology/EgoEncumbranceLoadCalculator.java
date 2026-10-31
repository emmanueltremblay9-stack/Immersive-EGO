package com.oblixorprime.immersiveego.physiology;

import java.util.Objects;

public final class EgoEncumbranceLoadCalculator {
    private static final double STAMINA_COST_PER_SECOND = 0.012D;
    private static final double FATIGUE_GAIN_PER_SECOND = 0.004D;

    private EgoEncumbranceLoadCalculator() {
    }

    public static EgoEncumbranceLoadDelta calculate(EgoEncumbranceContext context) {
        Objects.requireNonNull(context, "context");
        double pressure = context.effectivePressure();
        double seconds = context.seconds();
        return new EgoEncumbranceLoadDelta(
                pressure * STAMINA_COST_PER_SECOND * seconds,
                pressure * FATIGUE_GAIN_PER_SECOND * seconds);
    }
}
