package com.oblixorprime.immersiveego.sleep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

final class EgoSleepCalculatorTest {
    @Test
    void circadianPhaseFollowsMinecraftDayBoundaries() {
        assertEquals(EgoCircadianPhase.DAWN, EgoCircadianPhase.fromDayTime(0L));
        assertEquals(EgoCircadianPhase.DAY, EgoCircadianPhase.fromDayTime(6000L));
        assertEquals(EgoCircadianPhase.DUSK, EgoCircadianPhase.fromDayTime(12500L));
        assertEquals(EgoCircadianPhase.NIGHT, EgoCircadianPhase.fromDayTime(18000L));
        assertEquals(EgoCircadianPhase.DAWN, EgoCircadianPhase.fromDayTime(24000L));
    }

    @Test
    void awakeNightAccumulatesFatiguePressure() {
        EgoSleepDelta delta = EgoSleepCalculator.calculate(new EgoSleepInput(
                new EgoSleepContext(18000L, false, 200, 20, 1.0D),
                0.50D,
                0.40D,
                0.20D,
                0.80D));

        assertEquals(EgoCircadianPhase.NIGHT, delta.circadianPhase());
        assertFalse(delta.sleeping());
        assertEquals(0.0D, delta.sleepQuality(), 1.0E-12D);
        assertEquals(0.00249D, delta.pressureGain(), 1.0E-12D);
        assertEquals(delta.pressureGain(), delta.netFatigueChange(), 1.0E-12D);
    }

    @Test
    void nightSleepRecoversFatigueAndQualifiesPhantomRestAfterThreshold() {
        EgoSleepDelta delta = EgoSleepCalculator.calculate(new EgoSleepInput(
                new EgoSleepContext(18000L, true, 200, 20, 1.0D),
                0.50D,
                0.0D,
                0.0D,
                1.0D));

        assertEquals(EgoCircadianPhase.NIGHT, delta.circadianPhase());
        assertTrue(delta.sleeping());
        assertFalse(delta.nap());
        assertTrue(delta.phantomRestQualified());
        assertEquals(1.0D, delta.sleepQuality(), 1.0E-12D);
        assertEquals(0.018D, delta.fatigueRecovery(), 1.0E-12D);
        assertEquals(0.012D, delta.recoveryCapacityGain(), 1.0E-12D);
        assertEquals(0.008D, delta.focusRecovery(), 1.0E-12D);
        assertEquals(0.006D, delta.comfortRecovery(), 1.0E-12D);
        assertEquals(-0.018D, delta.netFatigueChange(), 1.0E-12D);
    }

    @Test
    void daySleepIsALowQualityNapWithoutPhantomRestQualification() {
        EgoSleepDelta delta = EgoSleepCalculator.calculate(new EgoSleepInput(
                new EgoSleepContext(6000L, true, 20, 20, 1.0D),
                0.50D,
                0.0D,
                0.0D,
                1.0D));

        assertEquals(EgoCircadianPhase.DAY, delta.circadianPhase());
        assertTrue(delta.nap());
        assertFalse(delta.phantomRestQualified());
        assertEquals(0.27D, delta.sleepQuality(), 1.0E-12D);
        assertEquals(0.00486D, delta.fatigueRecovery(), 1.0E-12D);
    }

    @Test
    void phantomSpawnEligibilityUsesVanillaRestlessTickThreshold() {
        EgoSleepContext rested = new EgoSleepContext(18000L, true, 100, 0, 20, 1.0D);
        EgoSleepContext restless = new EgoSleepContext(18000L, false, 0, 72000, 20, 1.0D);

        assertTrue(rested.phantomRestQualified());
        assertFalse(rested.phantomSpawnEligible());
        assertFalse(restless.phantomRestQualified());
        assertTrue(restless.phantomSpawnEligible());
    }

    @Test
    void serviceAppliesSleepDeltaAndClampsDisplayValues() {
        EgoState tired = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.01D)
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.995D)
                .withDisplayValue(EgoAttributeCatalog.FOCUS, 0.995D)
                .withDisplayValue(EgoAttributeCatalog.COMFORT, 0.995D);

        EgoState recovered = EgoSleepService.applySleep(
                tired,
                new EgoSleepContext(18000L, true, 200, 20, 1.0D));

        assertEquals(0.0D, recovered.fatigue(), 1.0E-12D);
        assertEquals(1.0D, recovered.recoveryCapacity(), 1.0E-12D);
        assertEquals(1.0D, recovered.focus(), 1.0E-12D);
        assertEquals(1.0D, recovered.comfort(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new EgoSleepContext(-1L, false, 0, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSleepContext(0L, false, -1, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSleepContext(0L, false, 0, -1, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSleepContext(0L, false, 0, 20, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSleepInput(new EgoSleepContext(0L, false, 0, 20, 1.0D), 1.1D, 0.0D, 0.0D, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSleepDelta(EgoCircadianPhase.NIGHT, true, false, true, 1.1D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSleepDelta(EgoCircadianPhase.NIGHT, true, false, true, 1.0D, -0.01D, 0.0D, 0.0D, 0.0D, 0.0D));
    }
}
