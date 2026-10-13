package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoRespiratoryStrainCalculator {
    private static final double[] PRESSURE_WEIGHTS = {0.8D, 0.7D, 1.2D, 0.8D};
    private static final double WEIGHTED_PRESSURE_SHARE = 0.65D;
    private static final double DOMINANT_PRESSURE_SHARE = 0.35D;

    private EgoRespiratoryStrainCalculator() {
    }

    public static double calculate(EgoRespiratorySample sample) {
        Objects.requireNonNull(sample, "sample");
        double weightedPressure = EgoMath.weightedMean(new double[] {
                sample.altitudePressure(),
                sample.depthPressure(),
                sample.submersionPressure(),
                sample.dimensionPressure()
        }, PRESSURE_WEIGHTS);
        double rawStrain = weightedPressure * WEIGHTED_PRESSURE_SHARE
                + sample.dominantPressure() * DOMINANT_PRESSURE_SHARE;
        return EgoMath.clamp01(rawStrain * sample.efficiencyMultiplier());
    }
}
