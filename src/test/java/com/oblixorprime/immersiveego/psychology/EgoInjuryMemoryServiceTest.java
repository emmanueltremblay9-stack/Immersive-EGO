package com.oblixorprime.immersiveego.psychology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EgoInjuryMemoryServiceTest {
    @Test
    void healthLossRecordsPersistentInjuryMemory() {
        EgoInjuryMemory updated = EgoInjuryMemoryService.update(
                EgoInjuryMemory.defaultState(),
                new EgoInjuryMemoryContext(0.60D, 20, 1.0D));

        assertEquals(0.60D, updated.recentInjury(), 1.0E-12D);
        assertTrue(updated.lingeringPain() > 0.0D);
        assertTrue(updated.recoveryDebt() > 0.0D);
        assertEquals(0.60D, updated.painPressure(0.60D), 1.0E-12D);
    }

    @Test
    void recoveredHealthStillCarriesLingeringPainPressure() {
        EgoInjuryMemory injured = EgoInjuryMemoryService.update(
                EgoInjuryMemory.defaultState(),
                new EgoInjuryMemoryContext(0.60D, 20, 1.0D));

        EgoInjuryMemory healing = EgoInjuryMemoryService.update(
                injured,
                new EgoInjuryMemoryContext(0.0D, 20, 1.0D));

        assertTrue(healing.recentInjury() < injured.recentInjury());
        assertTrue(healing.painPressure(0.0D) > 0.0D);
    }

    @Test
    void sustainedRecoveryEventuallyDecaysMemory() {
        EgoInjuryMemory memory = EgoInjuryMemoryService.update(
                EgoInjuryMemory.defaultState(),
                new EgoInjuryMemoryContext(0.70D, 20, 1.0D));

        for (int i = 0; i < 200; i++) {
            memory = EgoInjuryMemoryService.update(
                    memory,
                    new EgoInjuryMemoryContext(0.0D, 20, 1.0D));
        }

        assertTrue(memory.recentInjury() < 0.01D);
        assertTrue(memory.painPressure(0.0D) < 0.20D);
    }

    @Test
    void rejectsInvalidContextValues() {
        assertThrows(IllegalArgumentException.class, () -> new EgoInjuryMemoryContext(Double.NaN, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoInjuryMemoryContext(0.0D, 20, 1.1D));
    }
}
