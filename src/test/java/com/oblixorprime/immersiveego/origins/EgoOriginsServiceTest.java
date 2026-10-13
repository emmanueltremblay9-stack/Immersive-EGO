package com.oblixorprime.immersiveego.origins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

class EgoOriginsServiceTest {
    @Test
    void catalogMapsCommonBiomePathsToProfiles() {
        assertEquals(EgoOriginProfileCatalog.ARID, EgoOriginProfileCatalog.forBiomePath("desert").id());
        assertEquals(EgoOriginProfileCatalog.COLD, EgoOriginProfileCatalog.forBiomePath("snowy_plains").id());
        assertEquals(EgoOriginProfileCatalog.HIGHLAND, EgoOriginProfileCatalog.forBiomePath("jagged_peaks").id());
        assertEquals(EgoOriginProfileCatalog.HUMID, EgoOriginProfileCatalog.forBiomePath("mangrove_swamp").id());
        assertEquals(EgoOriginProfileCatalog.TEMPERATE, EgoOriginProfileCatalog.forBiomePath("plains").id());
    }

    @Test
    void originAssignmentSeedsProfileAcclimation() {
        EgoOriginState assigned = EgoOriginState.defaultState()
                .withOrigin(EgoOriginProfileCatalog.defaultById(EgoOriginProfileCatalog.ARID).orElseThrow());

        assertEquals(EgoOriginProfileCatalog.ARID, assigned.originId());
        assertTrue(assigned.heatAcclimation() > 0.0D);
        assertTrue(assigned.heatAcclimation() > assigned.coldAcclimation());
    }

    @Test
    void acclimationMovesTowardExposureAndProfileTarget() {
        EgoOriginProfile arid = EgoOriginProfileCatalog.defaultById(EgoOriginProfileCatalog.ARID).orElseThrow();
        EgoOriginState state = EgoOriginState.defaultState().withOrigin(arid);

        EgoOriginState advanced = EgoAcclimationCalculator.advance(
                state,
                new EgoAcclimationContext(20, arid, 1.0D, 0.0D, 0.0D, 0.0D, 1.0D));

        assertTrue(advanced.heatAcclimation() > state.heatAcclimation());
        assertTrue(advanced.heatAcclimation() > advanced.coldAcclimation());
    }

    @Test
    void acclimationBenefitsEaseStrainWithoutOverwritingState() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.COMFORT, 0.50D);
        EgoOriginState originState = new EgoOriginState(1, "highland", 0.50D, 0.20D, 0.50D, 0.40D);

        EgoState updated = EgoOriginsService.applyBenefits(state, originState);

        assertEquals(0.46D, updated.thermalStrain(), 1.0E-12D);
        assertEquals(0.47D, updated.respiratoryStrain(), 1.0E-12D);
        assertEquals(0.506D, updated.comfort(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidProfileAndContextValues() {
        assertThrows(IllegalArgumentException.class, () -> new EgoOriginProfile("bad-id", 0.0D, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoAcclimationContext(
                20,
                EgoOriginProfileCatalog.defaultProfile(),
                -0.1D,
                0.0D,
                0.0D,
                0.0D,
                1.0D));
    }
}
