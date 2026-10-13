package com.oblixorprime.immersiveego.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class EgoEnvironmentSamplerTest {
    @Test
    void seasonPhaseUsesStableEightDaySlices() {
        assertEquals(EgoSeasonPhase.SPRING, EgoEnvironmentSampler.seasonPhase(0L));
        assertEquals(EgoSeasonPhase.SPRING, EgoEnvironmentSampler.seasonPhase(191999L));
        assertEquals(EgoSeasonPhase.SUMMER, EgoEnvironmentSampler.seasonPhase(192000L));
        assertEquals(EgoSeasonPhase.AUTUMN, EgoEnvironmentSampler.seasonPhase(384000L));
        assertEquals(EgoSeasonPhase.WINTER, EgoEnvironmentSampler.seasonPhase(576000L));
        assertEquals(EgoSeasonPhase.SPRING, EgoEnvironmentSampler.seasonPhase(768000L));
    }

    @Test
    void climateSelectionUsesTemperatureDownfallAndSnow() {
        assertEquals(EgoClimateProfileCatalog.COLD_WET, EgoEnvironmentSampler.selectClimateProfile(0.7F, 0.8F, true).id());
        assertEquals(EgoClimateProfileCatalog.COLD_DRY, EgoEnvironmentSampler.selectClimateProfile(0.1F, 0.2F, false).id());
        assertEquals(EgoClimateProfileCatalog.HOT_HUMID, EgoEnvironmentSampler.selectClimateProfile(0.95F, 0.9F, false).id());
        assertEquals(EgoClimateProfileCatalog.HOT_DRY, EgoEnvironmentSampler.selectClimateProfile(1.0F, 0.1F, false).id());
        assertEquals(EgoClimateProfileCatalog.TEMPERATE, EgoEnvironmentSampler.selectClimateProfile(0.6F, 0.6F, false).id());
    }
}
