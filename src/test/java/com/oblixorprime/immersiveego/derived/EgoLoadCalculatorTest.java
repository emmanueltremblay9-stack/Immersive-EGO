package com.oblixorprime.immersiveego.derived;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EgoLoadCalculatorTest {
    @Test
    void calculateUsesWeightedMeanMaxUrgencyAndPersistenceShares() {
        double value = EgoLoadCalculator.calculate(0.5D, 1.0D, 0.25D);

        assertEquals(0.625D, value, 1.0E-9D);
    }

    @Test
    void calculateClampsAllComponents() {
        double value = EgoLoadCalculator.calculate(2.0D, 2.0D, 2.0D);

        assertEquals(1.0D, value, 1.0E-9D);
    }
}
