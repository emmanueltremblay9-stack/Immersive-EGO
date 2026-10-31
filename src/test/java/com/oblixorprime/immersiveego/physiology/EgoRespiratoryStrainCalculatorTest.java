package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oblixorprime.immersiveego.data.EgoState;
import org.junit.jupiter.api.Test;

final class EgoRespiratoryStrainCalculatorTest {
    @Test
    void pressureCalculatorMapsAltitudeDepthSubmersionAndDimensions() {
        assertEquals(0.0D, EgoRespiratoryPressureCalculator.altitudePressure(159, 63), 1.0E-12D);
        assertEquals(0.5D, EgoRespiratoryPressureCalculator.altitudePressure(223, 63), 1.0E-12D);
        assertEquals(1.0D, EgoRespiratoryPressureCalculator.altitudePressure(287, 63), 1.0E-12D);

        assertEquals(0.0D, EgoRespiratoryPressureCalculator.depthPressure(31, 63), 1.0E-12D);
        assertEquals(0.5D, EgoRespiratoryPressureCalculator.depthPressure(-17, 63), 1.0E-12D);
        assertEquals(1.0D, EgoRespiratoryPressureCalculator.depthPressure(-65, 63), 1.0E-12D);

        assertEquals(0.0D, EgoRespiratoryPressureCalculator.submersionPressure(false, false), 1.0E-12D);
        assertEquals(0.35D, EgoRespiratoryPressureCalculator.submersionPressure(true, false), 1.0E-12D);
        assertEquals(1.0D, EgoRespiratoryPressureCalculator.submersionPressure(false, true), 1.0E-12D);

        assertEquals(0.0D, EgoRespiratoryPressureCalculator.dimensionPressure(EgoRespiratoryDimension.OVERWORLD), 1.0E-12D);
        assertEquals(0.35D, EgoRespiratoryPressureCalculator.dimensionPressure(EgoRespiratoryDimension.NETHER), 1.0E-12D);
        assertEquals(0.25D, EgoRespiratoryPressureCalculator.dimensionPressure(EgoRespiratoryDimension.END), 1.0E-12D);
        assertEquals(0.15D, EgoRespiratoryPressureCalculator.dimensionPressure(EgoRespiratoryDimension.OTHER), 1.0E-12D);
    }

    @Test
    void calculatesWeightedRespiratoryStrain() {
        EgoRespiratorySample sample = new EgoRespiratorySample(0.5D, 0.25D, 0.0D, 0.35D, 1.0D);

        assertEquals(0.3337857142857143D, EgoRespiratoryStrainCalculator.calculate(sample), 1.0E-12D);
    }

    @Test
    void respirationEfficiencyScalesAndClampsStrain() {
        EgoRespiratorySample submerged = new EgoRespiratorySample(0.0D, 0.0D, 1.0D, 0.0D, 2.0D);
        EgoRespiratorySample fullyPressuredLowEfficiency = new EgoRespiratorySample(1.0D, 1.0D, 1.0D, 1.0D, 0.25D);
        EgoRespiratorySample fullyPressuredHighEfficiency = new EgoRespiratorySample(1.0D, 1.0D, 1.0D, 1.0D, 4.0D);

        assertEquals(0.2864285714285715D, EgoRespiratoryStrainCalculator.calculate(submerged), 1.0E-12D);
        assertEquals(1.0D, EgoRespiratoryStrainCalculator.calculate(fullyPressuredLowEfficiency), 1.0E-12D);
        assertEquals(0.25D, EgoRespiratoryStrainCalculator.calculate(fullyPressuredHighEfficiency), 1.0E-12D);
    }

    @Test
    void serviceWritesRespiratoryStrainOnly() {
        EgoState state = EgoState.defaultState();
        EgoRespiratorySample sample = new EgoRespiratorySample(0.5D, 0.25D, 0.0D, 0.35D, 1.0D);

        EgoState updated = EgoRespirationService.applySample(state, sample);

        assertEquals(0.3337857142857143D, updated.respiratoryStrain(), 1.0E-12D);
        assertEquals(state.hydration(), updated.hydration(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidSamples() {
        assertThrows(IllegalArgumentException.class, () -> new EgoRespiratorySample(-0.01D, 0.0D, 0.0D, 0.0D, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoRespiratorySample(0.0D, Double.NaN, 0.0D, 0.0D, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoRespiratorySample(0.0D, 0.0D, 1.01D, 0.0D, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoRespiratorySample(0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        assertThrows(NullPointerException.class, () -> EgoRespiratoryPressureCalculator.dimensionPressure(null));
    }
}
