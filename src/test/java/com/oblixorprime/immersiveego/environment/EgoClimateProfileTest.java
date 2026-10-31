package com.oblixorprime.immersiveego.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class EgoClimateProfileTest {
    @Test
    void validatesIdsAndNormalizedFields() {
        assertThrows(NullPointerException.class, () -> new EgoClimateProfile(null, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoClimateProfile("Hot", 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoClimateProfile("hot", -0.01D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoClimateProfile("hot", 0.0D, 1.01D, 0.0D, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoClimateProfile("hot", 0.0D, 0.0D, Double.NaN, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoClimateProfile("hot", 0.0D, 0.0D, 0.0D, Double.POSITIVE_INFINITY, 0.0D, 0.0D));
    }

    @Test
    void derivesStableProfilePressures() {
        EgoClimateProfile profile = new EgoClimateProfile("test/hot_wet", 0.7D, 0.2D, 0.6D, 0.4D, 0.9D, 0.5D);

        assertEquals(0.7D, profile.dominantThermalLoad());
        assertEquals(0.8D, profile.wetnessPressure(), 1.0E-12D);
    }

    @Test
    void defaultProfilesAreUniqueAndImmutable() {
        Set<String> ids = new HashSet<>();

        for (EgoClimateProfile profile : EgoClimateProfileCatalog.defaults()) {
            assertTrue(ids.add(profile.id()), profile.id());
            assertTrue(profile.dominantThermalLoad() >= 0.0D && profile.dominantThermalLoad() <= 1.0D, profile.id());
            assertTrue(profile.wetnessPressure() >= 0.0D && profile.wetnessPressure() <= 1.0D, profile.id());
        }

        assertEquals(Set.of(
                EgoClimateProfileCatalog.TEMPERATE,
                EgoClimateProfileCatalog.HOT_DRY,
                EgoClimateProfileCatalog.HOT_HUMID,
                EgoClimateProfileCatalog.COLD_DRY,
                EgoClimateProfileCatalog.COLD_WET), ids);
        assertThrows(UnsupportedOperationException.class, () -> EgoClimateProfileCatalog.defaults().add(
                new EgoClimateProfile("other", 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D)));
    }

    @Test
    void defaultLookupUsesProfileIds() {
        EgoClimateProfile hotDry = EgoClimateProfileCatalog.defaultById(EgoClimateProfileCatalog.HOT_DRY).orElseThrow();
        EgoClimateProfile coldWet = EgoClimateProfileCatalog.defaultById(EgoClimateProfileCatalog.COLD_WET).orElseThrow();

        assertEquals(0.80D, hotDry.heatLoad());
        assertEquals(0.65D, coldWet.coldLoad());
        assertTrue(EgoClimateProfileCatalog.defaultById("missing").isEmpty());
    }
}
