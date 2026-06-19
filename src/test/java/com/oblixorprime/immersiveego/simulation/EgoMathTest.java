package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EgoMathTest {
    @Test
    void clamp01BoundsValuesAndTreatsNanAsZero() {
        assertEquals(0.0D, EgoMath.clamp01(-0.25D));
        assertEquals(0.0D, EgoMath.clamp01(Double.NaN));
        assertEquals(0.5D, EgoMath.clamp01(0.5D));
        assertEquals(1.0D, EgoMath.clamp01(1.25D));
    }

    @Test
    void weightedMeanNormalizesInputs() {
        double value = EgoMath.weightedMean(new double[] { 1.0D, 0.0D, 2.0D }, new double[] { 1.0D, 1.0D, 2.0D });

        assertEquals(0.75D, value);
    }

    @Test
    void weightedMeanHandlesLargeFiniteWeightsWithoutOverflow() {
        double value = EgoMath.weightedMean(
                new double[] { 1.0D, 0.0D, 1.0D },
                new double[] { Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE });

        assertEquals(2.0D / 3.0D, value, 1.0E-12D);
    }

    @Test
    void weightedMeanRejectsMismatchedArrays() {
        assertThrows(IllegalArgumentException.class, () -> EgoMath.weightedMean(new double[] { 1.0D }, new double[] { 1.0D, 1.0D }));
    }

    @Test
    void weightedMeanRejectsNegativeWeights() {
        assertThrows(IllegalArgumentException.class, () -> EgoMath.weightedMean(new double[] { 1.0D }, new double[] { -1.0D }));
    }

    @Test
    void weightedMeanRejectsNonFiniteWeights() {
        assertThrows(IllegalArgumentException.class, () -> EgoMath.weightedMean(new double[] { 1.0D }, new double[] { Double.NaN }));
        assertThrows(IllegalArgumentException.class, () -> EgoMath.weightedMean(new double[] { 1.0D }, new double[] { Double.POSITIVE_INFINITY }));
    }
}
