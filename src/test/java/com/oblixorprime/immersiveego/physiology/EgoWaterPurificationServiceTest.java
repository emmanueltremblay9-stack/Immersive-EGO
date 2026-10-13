package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

final class EgoWaterPurificationServiceTest {
    @Test
    void qualityImprovementIsBoundedAndDeterministic() {
        assertEquals(EgoWaterQuality.QUESTIONABLE, EgoWaterQuality.CONTAMINATED.improve(1));
        assertEquals(EgoWaterQuality.POTABLE, EgoWaterQuality.CONTAMINATED.improve(2));
        assertEquals(EgoWaterQuality.PURIFIED, EgoWaterQuality.POTABLE.improve(5));
    }

    @Test
    void existingItemsResolveToPurificationMethods() {
        assertEquals(EgoWaterPurificationMethod.CHARCOAL_FILTER, EgoWaterPurificationService.methodForItemId("minecraft:charcoal").orElseThrow());
        assertEquals(EgoWaterPurificationMethod.CHARCOAL_FILTER, EgoWaterPurificationService.methodForItemId("MINECRAFT:COAL").orElseThrow());
        assertEquals(EgoWaterPurificationMethod.BOILING, EgoWaterPurificationService.methodForItemId("minecraft:campfire").orElseThrow());
        assertTrue(EgoWaterPurificationService.methodForItemId("minecraft:dirt").isEmpty());
    }

    @Test
    void purificationImprovesQualityAndReducesContaminationRisk() {
        EgoDrinkProfile dirty = new EgoDrinkProfile(
                "minecraft:test_dirty_water",
                EgoWaterQuality.CONTAMINATED,
                0.20D,
                0.0D,
                0.90D);

        EgoDrinkProfile filtered = EgoWaterPurificationService.purify(dirty, EgoWaterPurificationMethod.CHARCOAL_FILTER);
        EgoDrinkProfile boiled = EgoWaterPurificationService.purify(dirty, EgoWaterPurificationMethod.BOILING);

        assertEquals(EgoWaterQuality.QUESTIONABLE, filtered.waterQuality());
        assertEquals(0.55D, filtered.contaminationRisk(), 1.0E-12D);
        assertEquals(EgoWaterQuality.POTABLE, boiled.waterQuality());
        assertEquals(0.20D, boiled.contaminationRisk(), 1.0E-12D);
    }

    @Test
    void contaminatedDrinkAddsStressWhilePotableDrinkDoesNot() {
        EgoState thirsty = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.4D);
        EgoDrinkProfile dirty = new EgoDrinkProfile(
                "minecraft:test_dirty_water",
                EgoWaterQuality.CONTAMINATED,
                0.20D,
                0.0D,
                0.50D);
        EgoDrinkProfile potable = EgoWaterPurificationService.purify(dirty, EgoWaterPurificationMethod.BOILING);

        EgoState dirtyState = EgoHydrationService.applyDrink(thirsty, dirty, 1.0D);
        EgoState potableState = EgoHydrationService.applyDrink(thirsty, potable, 1.0D);

        assertEquals(0.55D, dirtyState.hydration(), 1.0E-12D);
        assertTrue(dirtyState.stress() > thirsty.stress());
        assertEquals(0.60D, potableState.hydration(), 1.0E-12D);
        assertEquals(thirsty.stress(), potableState.stress(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidQualityStressInputs() {
        assertThrows(IllegalArgumentException.class, () -> EgoWaterQuality.POTABLE.stressPenalty(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> EgoWaterQuality.POTABLE.stressPenalty(1.01D));
    }
}
