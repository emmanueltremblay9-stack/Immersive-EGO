package com.oblixorprime.immersiveego.derived;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

final class EgoDerivedStateCalculatorTest {
    @Test
    void defaultStateStaysNeutral() {
        EgoState derived = EgoDerivedStateCalculator.calculate(EgoState.defaultState());

        assertEquals(0.0D, derived.egoLoad(), 1.0E-9D);
        assertEquals(1.0D, derived.readiness(), 1.0E-9D);
    }

    @Test
    void strainIncreasesLoadAndReducesReadiness() {
        EgoState strained = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.5D)
                .withDisplayValue(EgoAttributeCatalog.STRESS, 1.0D);

        EgoState derived = EgoDerivedStateCalculator.calculate(strained);

        assertEquals(0.525D, derived.egoLoad(), 1.0E-9D);
        assertEquals(0.86875D, derived.readiness(), 1.0E-9D);
    }
}
