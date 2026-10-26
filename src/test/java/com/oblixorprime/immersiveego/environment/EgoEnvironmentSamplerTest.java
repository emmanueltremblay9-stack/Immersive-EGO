package com.oblixorprime.immersiveego.environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Random;
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
    void seasonPhaseMatchesReferenceAroundPositiveAndNegativeBoundaries() {
        for (long boundary = -12L; boundary <= 12L; boundary++) {
            for (long offset = -1L; offset <= 1L; offset++) {
                assertSeasonMatchesReference(boundary * 192000L + offset);
            }
        }
    }

    @Test
    void seasonPhaseFollowsEachSuppliedTimeIncludingRewindsAndExtremes() {
        long[] times = {0L, 192000L, 576000L, 191999L, -1L, -192000L,
                Long.MAX_VALUE, Long.MAX_VALUE - 1L, Long.MIN_VALUE, Long.MIN_VALUE + 1L,
                768000L, 384000L, 0L, -768001L, -768000L};
        for (long time : times) {
            assertSeasonMatchesReference(time);
        }
    }

    @Test
    void seasonPhaseMatchesReferenceAcrossFullLongInputRange() {
        Random random = new Random(0x5EA50L);
        for (int sample = 0; sample < 10000; sample++) {
            assertSeasonMatchesReference(random.nextLong());
        }
    }

    @Test
    void callerMutationOfEnumValuesCannotAlterSeasonLookup() {
        EgoSeasonPhase[] callerCopy = EgoSeasonPhase.values();
        callerCopy[0] = EgoSeasonPhase.WINTER;
        callerCopy[1] = null;
        assertEquals(EgoSeasonPhase.SPRING, EgoEnvironmentSampler.seasonPhase(0L));
        assertEquals(EgoSeasonPhase.SUMMER, EgoEnvironmentSampler.seasonPhase(192000L));
    }

    private static void assertSeasonMatchesReference(long dayTime) {
        // Independent arithmetic oracle: nonnegative BigInteger modulo avoids long overflow.
        int season = BigInteger.valueOf(dayTime).mod(BigInteger.valueOf(768000L))
                .divide(BigInteger.valueOf(192000L)).intValueExact();
        EgoSeasonPhase expected = switch (season) {
            case 0 -> EgoSeasonPhase.SPRING;
            case 1 -> EgoSeasonPhase.SUMMER;
            case 2 -> EgoSeasonPhase.AUTUMN;
            case 3 -> EgoSeasonPhase.WINTER;
            default -> throw new AssertionError("Invalid reference season");
        };
        assertEquals(expected, EgoEnvironmentSampler.seasonPhase(dayTime), "dayTime=" + dayTime);
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
