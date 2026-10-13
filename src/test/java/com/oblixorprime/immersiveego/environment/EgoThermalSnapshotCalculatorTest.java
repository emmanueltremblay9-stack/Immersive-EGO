package com.oblixorprime.immersiveego.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class EgoThermalSnapshotCalculatorTest {
    @Test
    void exposureSampleRejectsInvalidInputs() {
        EgoClimateProfile profile = EgoClimateProfileCatalog.defaultById(EgoClimateProfileCatalog.TEMPERATE).orElseThrow();

        assertThrows(NullPointerException.class, () -> new EgoExposureSample(null, EgoSeasonPhase.SPRING, 0.0D, 0.0D, 0.0D));
        assertThrows(NullPointerException.class, () -> new EgoExposureSample(profile, null, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoExposureSample(profile, EgoSeasonPhase.SPRING, -0.01D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoExposureSample(profile, EgoSeasonPhase.SPRING, 0.0D, 1.01D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoExposureSample(profile, EgoSeasonPhase.SPRING, 0.0D, 0.0D, Double.NaN));
    }

    @Test
    void shelterReducesHotDrySummerPressure() {
        EgoClimateProfile profile = EgoClimateProfileCatalog.defaultById(EgoClimateProfileCatalog.HOT_DRY).orElseThrow();
        EgoThermalSnapshot exposed = EgoThermalSnapshotCalculator.calculate(new EgoExposureSample(
                profile,
                EgoSeasonPhase.SUMMER,
                1.0D,
                0.0D,
                0.0D));
        EgoThermalSnapshot sheltered = EgoThermalSnapshotCalculator.calculate(new EgoExposureSample(
                profile,
                EgoSeasonPhase.SUMMER,
                1.0D,
                0.8D,
                0.0D));

        assertEquals(EgoThermalMode.HEAT, exposed.mode());
        assertTrue(exposed.heatPressure() > sheltered.heatPressure());
        assertTrue(exposed.exposurePressure() > sheltered.exposurePressure());
        assertTrue(exposed.thermalStrain() > sheltered.thermalStrain());
    }

    @Test
    void wetColdWinterProducesColdMode() {
        EgoClimateProfile profile = EgoClimateProfileCatalog.defaultById(EgoClimateProfileCatalog.COLD_WET).orElseThrow();

        EgoThermalSnapshot snapshot = EgoThermalSnapshotCalculator.calculate(new EgoExposureSample(
                profile,
                EgoSeasonPhase.WINTER,
                1.0D,
                0.0D,
                1.0D));

        assertEquals(EgoThermalMode.COLD, snapshot.mode());
        assertEquals(1.0D, snapshot.wetnessPressure());
        assertTrue(snapshot.coldPressure() > snapshot.heatPressure());
        assertEquals(snapshot.coldPressure(), snapshot.thermalStrain());
    }

    @Test
    void temperateShelteredSpringStaysNearNeutral() {
        EgoClimateProfile profile = EgoClimateProfileCatalog.defaultById(EgoClimateProfileCatalog.TEMPERATE).orElseThrow();

        EgoThermalSnapshot snapshot = EgoThermalSnapshotCalculator.calculate(new EgoExposureSample(
                profile,
                EgoSeasonPhase.SPRING,
                0.2D,
                0.8D,
                0.0D));

        assertEquals(EgoThermalMode.NEUTRAL, snapshot.mode());
        assertTrue(snapshot.thermalStrain() < 0.2D);
    }
}
