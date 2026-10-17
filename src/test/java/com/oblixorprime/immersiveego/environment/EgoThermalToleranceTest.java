package com.oblixorprime.immersiveego.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.origins.EgoOriginsService;
import com.oblixorprime.immersiveego.physiology.EgoHydrationDemandContext;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoSimulationFrameCalculator;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class EgoThermalToleranceTest {
    private static final double EPSILON = 1.0E-12D;

    private static EgoExposureSample sample(String climate) {
        return new EgoExposureSample(EgoClimateProfileCatalog.defaultById(climate).orElseThrow(),
                EgoSeasonPhase.SPRING, 1.0D, 0.0D, 0.0D);
    }

    @ParameterizedTest
    @CsvSource({
            "hot_dry, 0.25, 1.0, 0.78", "hot_dry, 0.5, 1.0, 0.39",
            "hot_dry, 1.0, 0.87, 0.195", "hot_dry, 2.0, 0.435, 0.0975",
            "hot_dry, 4.0, 0.2175, 0.04875",
            "cold_dry, 0.25, 0.284, 1.0", "cold_dry, 0.5, 0.142, 1.0",
            "cold_dry, 1.0, 0.071, 0.985", "cold_dry, 2.0, 0.0355, 0.4925",
            "cold_dry, 4.0, 0.01775, 0.24625"
    })
    void adjustedSnapshotFeedsHydrationAndBothAcclimationAxes(
            String climate, double tolerance, double heat, double cold) {
        EgoExposureSample sample = sample(climate);
        EgoThermalSnapshot thermal = EgoThermalSnapshotCalculator.calculate(sample, 0.0D, tolerance);
        assertEquals(heat, thermal.heatPressure(), EPSILON);
        assertEquals(cold, thermal.coldPressure(), EPSILON);
        assertEquals(climate.equals("hot_dry") ? 0.87D : 0.071D, thermal.rawHeatPressure(), EPSILON);
        assertEquals(climate.equals("hot_dry") ? 0.195D : 0.985D, thermal.rawColdPressure(), EPSILON);
        assertEquals(tolerance, thermal.thermalTolerance());
        assertEquals(climate.equals("hot_dry") ? EgoThermalMode.HEAT : EgoThermalMode.COLD, thermal.mode());
        double strain = Math.max(heat, cold);
        assertEquals(strain, thermal.thermalStrain(), EPSILON);

        EgoEnvironmentSnapshot environment = new EgoEnvironmentSnapshot(20L, sample, thermal);
        EgoState initial = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.8D)
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, 0.3D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.2D);
        var frame = EgoSimulationFrameCalculator.advanceFrame(20L, initial, EgoDynamicServerConfig.defaults(),
                Optional.of(environment), Optional.of(new EgoHydrationDemandContext(20, 1.0D)));
        // Baseline, respiratory and fatigue contributions retain their original coefficients.
        assertEquals(0.8D - (0.00025D + strain * 0.00075D + 0.3D * 0.0005D + 0.2D * 0.00025D),
                frame.outputState().hydration(), EPSILON);
        assertEquals(strain, frame.outputState().thermalStrain(), EPSILON);

        EgoOriginState origin = new EgoOriginState(1, "arid", 0.3D, 0.4D, 0.2D, 0.1D);
        EgoOriginState advanced = EgoOriginsService.advanceAcclimation(origin, Optional.of(environment), 1.0D, 20);
        // Arid affinity, gain and interval are unchanged; only the exposure targets use adjusted pressure.
        assertEquals(0.3D + (0.7D * heat + 0.3D * 0.75D - 0.3D) * 0.012D, advanced.heatAcclimation(), EPSILON);
        assertEquals(0.4D + (0.7D * cold + 0.3D * 0.15D - 0.4D) * 0.012D, advanced.coldAcclimation(), EPSILON);
        assertEquals(0.19832D, advanced.altitudeAcclimation(), EPSILON);
        assertEquals(0.09916D, advanced.humidityAcclimation(), EPSILON);
    }

    @Test
    void neutralTolerancePreservesLegacyOverloadsAndSnapshotConstructor() {
        var sample = sample("hot_dry");
        var neutral = EgoThermalSnapshotCalculator.calculate(sample, 0.0D, 1.0D);
        assertEquals(neutral, EgoThermalSnapshotCalculator.calculate(sample));
        assertEquals(neutral, EgoThermalSnapshotCalculator.calculate(sample, 0.0D));
        assertEquals(neutral, new EgoThermalSnapshot(neutral.mode(), neutral.heatPressure(), neutral.coldPressure(),
                neutral.wetnessPressure(), neutral.exposurePressure(), neutral.thermalStrain()));
    }

    @Test
    void pressureIsNormalizedBeforeDivisionAndAfterAmplification() {
        var wetWinter = new EgoExposureSample(EgoClimateProfileCatalog.defaultById("cold_wet").orElseThrow(),
                EgoSeasonPhase.WINTER, 1.0D, 0.0D, 1.0D);
        var resistant = EgoThermalSnapshotCalculator.calculate(wetWinter, 1.0D, 2.0D);
        assertEquals(1.0D, resistant.rawColdPressure());
        assertEquals(0.5D, resistant.coldPressure());
        var sensitive = EgoThermalSnapshotCalculator.calculate(wetWinter, 1.0D, 0.25D);
        assertEquals(1.0D, sensitive.coldPressure());
        assertEquals(1.0D, sensitive.thermalStrain());
    }

    @Test
    void wetnessKeepsOneMaximumBranchIndependentOfTolerance() {
        var profile = new EgoClimateProfile("test", 0.2D, 0.1D, 0.3D, 0.0D, 0.6D, 0.0D);
        var sample = new EgoExposureSample(profile, EgoSeasonPhase.SPRING, 1.0D, 0.0D, 1.0D);
        for (double tolerance : new double[] {0.25D, 1.0D, 2.0D, 4.0D}) {
            var climateDominates = EgoThermalSnapshotCalculator.calculate(sample, 0.2D, tolerance);
            var bodyDominates = EgoThermalSnapshotCalculator.calculate(sample, 0.8D, tolerance);
            assertEquals(0.5D, climateDominates.wetnessPressure());
            assertEquals(0.30D, climateDominates.rawColdPressure(), EPSILON);
            assertEquals(0.8D, bodyDominates.wetnessPressure());
            assertEquals(0.39D, bodyDominates.rawColdPressure(), EPSILON);
            assertEquals(1.0D, sample.wetness(), "Instantaneous exposure is not replaced by W or tolerance");
        }
    }

    @Test
    void toleranceBoundsCannotIntroduceDivisionByZeroOrNonfinitePressures() {
        var sample = sample("hot_dry");
        for (double invalid : new double[] {0.0D, -1.0D, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class,
                    () -> EgoThermalSnapshotCalculator.calculate(sample, 0.0D, invalid));
        }
        assertEquals(EgoThermalSnapshotCalculator.calculate(sample, 0.0D, 0.25D),
                EgoThermalSnapshotCalculator.calculate(sample, 0.0D, Double.MIN_VALUE));
        assertEquals(EgoThermalSnapshotCalculator.calculate(sample, 0.0D, 4.0D),
                EgoThermalSnapshotCalculator.calculate(sample, 0.0D, Double.MAX_VALUE));
    }
}
