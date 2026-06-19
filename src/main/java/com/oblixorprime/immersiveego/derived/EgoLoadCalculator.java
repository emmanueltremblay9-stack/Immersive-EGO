package com.oblixorprime.immersiveego.derived;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public final class EgoLoadCalculator {
    private static final double WEIGHTED_MEAN_SHARE = 0.60D;
    private static final double MAX_URGENCY_SHARE = 0.30D;
    private static final double PERSISTENCE_SHARE = 0.10D;

    private EgoLoadCalculator() {
    }

    public static double calculate(double weightedMeanComponent, double maxUrgencyComponent, double persistenceComponent) {
        double value = EgoMath.clamp01(weightedMeanComponent) * WEIGHTED_MEAN_SHARE
                + EgoMath.clamp01(maxUrgencyComponent) * MAX_URGENCY_SHARE
                + EgoMath.clamp01(persistenceComponent) * PERSISTENCE_SHARE;
        return EgoMath.clamp01(value);
    }
}
