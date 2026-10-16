package com.oblixorprime.immersiveego.psychology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

class EgoPsychologyServiceTest {
    @Test
    void fearAndPainRaiseStressAndSpendRecoveryBudget() {
        EgoState updated = EgoPsychologyService.applyPsychology(
                EgoState.defaultState(),
                new EgoPsychologyContext(1.0D, 1.0D, 0.0D, 0.0D, 20, 1.0D));

        assertEquals(0.018D, updated.stress(), 1.0E-12D);
        assertEquals(0.986D, updated.focus(), 1.0E-12D);
        assertEquals(0.978D, updated.comfort(), 1.0E-12D);
        assertEquals(0.994D, updated.recoveryCapacity(), 1.0E-12D);
    }

    @Test
    void stableComfortableContextRelievesExistingStress() {
        EgoState strained = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STRESS, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.FOCUS, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.COMFORT, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.50D);

        EgoState updated = EgoPsychologyService.applyPsychology(
                strained,
                new EgoPsychologyContext(0.0D, 0.0D, 1.0D, 1.0D, 20, 2.0D));

        assertTrue(updated.stress() < strained.stress());
        assertTrue(updated.focus() > strained.focus());
        assertTrue(updated.comfort() > strained.comfort());
        assertTrue(updated.recoveryCapacity() < strained.recoveryCapacity());
    }

    @Test
    void rejectsInvalidContextValues() {
        assertThrows(IllegalArgumentException.class, () -> new EgoPsychologyContext(
                Double.NaN,
                0.0D,
                0.0D,
                0.0D,
                20,
                1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoPsychologyContext(
                0.0D,
                0.0D,
                0.0D,
                0.0D,
                20,
                0.0D));
    }
}
