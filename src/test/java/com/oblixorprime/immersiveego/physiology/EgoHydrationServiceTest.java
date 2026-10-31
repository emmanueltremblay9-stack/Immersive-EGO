package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class EgoHydrationServiceTest {
    @Test
    void defaultDrinkProfilesAreUniqueAndValid() {
        Set<String> ids = new HashSet<>();

        for (EgoDrinkProfile profile : EgoDrinkProfileCatalog.defaults()) {
            assertTrue(ids.add(profile.id()), profile.id());
            assertTrue(profile.hydrationGain() > 0.0D, profile.id());
        }

        assertEquals(Set.of(
                EgoDrinkProfileCatalog.WATER_POTION,
                EgoDrinkProfileCatalog.MILK_BUCKET,
                EgoDrinkProfileCatalog.HONEY_BOTTLE), ids);
        assertTrue(EgoDrinkProfileCatalog.defaultById("missing").isEmpty());
    }

    @Test
    void drinkRecoveryIncreasesHydrationAndClampsAtFull() {
        EgoState thirsty = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.6D);
        EgoDrinkProfile water = EgoDrinkProfileCatalog.defaultById(EgoDrinkProfileCatalog.WATER_POTION).orElseThrow();

        EgoState hydrated = EgoHydrationService.applyDrink(thirsty, water, 1.0D);
        EgoState capped = EgoHydrationService.applyDrink(thirsty, water, 4.0D);

        assertEquals(0.8D, hydrated.hydration(), 1.0E-12D);
        assertEquals(1.0D, capped.hydration(), 1.0E-12D);
    }

    @Test
    void demandReducesHydrationButNeverBelowZero() {
        EgoState strained = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.001D)
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, 1.0D)
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, 1.0D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 1.0D);

        EgoState afterDemand = EgoHydrationService.applyDemand(strained, new EgoHydrationDemandContext(1200, 0.25D));

        assertEquals(0.0D, afterDemand.hydration(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidDrinkProfilesAndEfficiency() {
        assertThrows(NullPointerException.class, () -> new EgoDrinkProfile(null, EgoWaterQuality.POTABLE, 0.1D, 0.0D, 0.0D));
        assertThrows(NullPointerException.class, () -> new EgoDrinkProfile("minecraft:test", null, 0.1D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoDrinkProfile("Bad", EgoWaterQuality.POTABLE, 0.1D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoDrinkProfile("minecraft:test", EgoWaterQuality.POTABLE, 1.1D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> EgoHydrationService.applyDrink(EgoState.defaultState(), EgoDrinkProfileCatalog.defaultById(EgoDrinkProfileCatalog.WATER_POTION).orElseThrow(), Double.NaN));
    }
}
