package com.oblixorprime.immersiveego.wetness;

import static org.junit.jupiter.api.Assertions.*;
import com.oblixorprime.immersiveego.environment.*;
import org.junit.jupiter.api.Test;

final class EgoWetnessModelTest {
    private static final EgoClimateProfile DRY = new EgoClimateProfile("dry", 1, 0, 0, 0, 0, 0);
    private static final EgoClimateProfile WET = new EgoClimateProfile("wet", 0, 1, 1, 1, 1, 1);
    private static EgoWetnessState state(double value) { return EgoWetnessState.dry().withWetness(value); }
    private static double step(double value, EgoWetnessExposure exposure, double seconds) {
        return EgoWetnessModel.advance(state(value), exposure, DRY, seconds).state().wetness();
    }

    @Test void normalizedStateRejectsNanInfinityAndOutOfRange() {
        for (double value : new double[]{Double.NaN, Double.POSITIVE_INFINITY, -.001, 1.001})
            assertThrows(IllegalArgumentException.class, () -> state(value));
        assertEquals(0, state(0).wetness()); assertEquals(1, state(1).wetness());
    }
    @Test void invalidDurationRejected() {
        for (double value : new double[]{Double.NaN, Double.POSITIVE_INFINITY, -.01})
            assertThrows(IllegalArgumentException.class, () -> step(.3, EgoWetnessExposure.RAIN, value));
    }
    @Test void zeroDurationDoesNotEvenSnapSmallWetness() {
        var result = EgoWetnessModel.advance(state(.0001), EgoWetnessExposure.DRY, WET, 0);
        assertEquals(.0001, result.state().wetness()); assertEquals(0, result.seconds());
    }
    @Test void rainAndWaterAcquireAtDifferentRatesWithoutAdding() {
        assertEquals(EgoWetnessExposure.IMMERSION, EgoWetnessExposure.observed(true, true));
        assertEquals(EgoWetnessExposure.RAIN, EgoWetnessExposure.observed(false, true));
        assertEquals(EgoWetnessExposure.DRY, EgoWetnessExposure.observed(false, false));
        assertTrue(step(0, EgoWetnessExposure.IMMERSION, 1) > .7);
        assertTrue(step(0, EgoWetnessExposure.RAIN, 1) < .1);
    }
    @Test void oneTickExposuresAreRetainedBelowOuterSchedulerInterval() {
        assertTrue(step(0, EgoWetnessExposure.RAIN, .05) > EgoWetnessModel.DRY_ZERO);
        assertTrue(step(0, EgoWetnessExposure.IMMERSION, .05) > .06);
    }
    @Test void saturatedExposureNeverClaimsDrying() {
        for (var exposure : new EgoWetnessExposure[]{EgoWetnessExposure.IMMERSION,EgoWetnessExposure.RAIN}) {
            var result=EgoWetnessModel.advance(state(1), exposure, DRY, 10);
            assertEquals(1,result.state().wetness()); assertEquals(0,result.drying());
        }
    }
    @Test void wetClimateNeverCreatesBodyWetness() {
        assertEquals(0, EgoWetnessModel.advance(state(0), EgoWetnessExposure.DRY,WET,500).state().wetness());
    }
    @Test void dryClimateIsFasterButWetClimateStillReachesExactZero() {
        assertTrue(EgoWetnessModel.dryingRate(DRY)>EgoWetnessModel.dryingRate(WET));
        assertEquals(0,EgoWetnessModel.advance(state(1),EgoWetnessExposure.DRY,WET,350).state().wetness());
        assertEquals(0,step(1,EgoWetnessExposure.DRY,120));
    }
    @Test void leavingExposureAndReenteringIsContinuous() {
        double wet=step(0,EgoWetnessExposure.IMMERSION,2);
        double partial=step(wet,EgoWetnessExposure.DRY,3);
        assertTrue(partial>0 && partial<wet);
        assertTrue(step(partial,EgoWetnessExposure.RAIN,1)>partial);
    }
    @Test void constantExposurePartitionIsNumericallyStable() {
        for (var exposure:EgoWetnessExposure.values()) {
            double split=.4;
            for(int i=0;i<200;i++) split=step(split,exposure,.05);
            assertEquals(step(.4,exposure,10),split,1e-13);
        }
    }
    @Test void zeroThresholdIsGameplayTruncationNotDetectionTolerance() {
        assertEquals(0,step(.001,EgoWetnessExposure.DRY,.05));
        assertTrue(step(.0011,EgoWetnessExposure.DRY,.05)>0);
        double split=1;
        for(int i=0;i<7000;i++) split=EgoWetnessModel.advance(state(split),EgoWetnessExposure.DRY,WET,.05).state().wetness();
        assertEquals(0,split);
    }
    @Test void orderOfBriefAndAlternatingExposureIsPreserved() {
        double wetThenDry=step(step(0,EgoWetnessExposure.IMMERSION,.05),EgoWetnessExposure.DRY,1);
        double dryThenWet=step(step(0,EgoWetnessExposure.DRY,1),EgoWetnessExposure.IMMERSION,.05);
        assertTrue(dryThenWet>wetThenDry);
        double expected=0;
        for(int i=0;i<100;i++) {
            expected=step(expected,EgoWetnessExposure.RAIN,.05);
            expected=step(expected,EgoWetnessExposure.DRY,.05);
        }
        assertTrue(expected>0 && expected<step(0,EgoWetnessExposure.RAIN,5));
        // The real PlayerTick/outer scheduler integration is qualified by a server GameTest.
    }
    @Test void unsupportedStateIsPreservedAndSuspended() {
        for(var original:new EgoWetnessState[]{new EgoWetnessState(99,.7,false),new EgoWetnessState(1,.7,true)}) {
            var result=EgoWetnessModel.advance(original,EgoWetnessExposure.IMMERSION,DRY,10);
            assertEquals(original,result.state()); assertEquals(0,result.seconds());
        }
    }
    @Test void repeatedThermalPreviewsDoNotMutateStateOrInstantExposure() {
        var sample=new EgoExposureSample(DRY,EgoSeasonPhase.WINTER,1,0,1);
        var original=state(.2);
        for(int i=0;i<100;i++) assertEquals(.2,EgoThermalSnapshotCalculator.calculate(sample,original.wetness()).wetnessPressure());
        assertEquals(.2,original.wetness()); assertEquals(1,sample.wetness());
    }
    @Test void thermalUsesExactlyOneMaxBranchInsteadOfAddingLiquidOrClimate() {
        var profile=new EgoClimateProfile("controlled",0,.1,.6,0,.6,0);
        var sample=new EgoExposureSample(profile,EgoSeasonPhase.SPRING,1,0,1);
        var thermal=EgoThermalSnapshotCalculator.calculate(sample,.8);
        assertEquals(.8,thermal.wetnessPressure(),1e-14);
        assertEquals(EgoThermalSnapshotCalculator.calculate(new EgoExposureSample(profile,EgoSeasonPhase.SPRING,1,0,.8)),thermal);
        assertEquals(EgoThermalSnapshotCalculator.calculate(sample,0),
                EgoThermalSnapshotCalculator.calculate(new EgoExposureSample(profile,EgoSeasonPhase.SPRING,1,0,0)));
        assertThrows(IllegalArgumentException.class,()->EgoThermalSnapshotCalculator.calculate(sample,Double.NaN));
    }
    @Test void wetnessCanChangeWhileDominantHeatStrainIsUnchanged() {
        var sample=new EgoExposureSample(DRY,EgoSeasonPhase.SUMMER,1,0,0);
        assertEquals(EgoThermalSnapshotCalculator.calculate(sample,.1).thermalStrain(),
                EgoThermalSnapshotCalculator.calculate(sample,.9).thermalStrain());
        assertNotEquals(step(.1,EgoWetnessExposure.RAIN,1),.1);
    }
    @Test void tickClockCannotReplayDuplicatesGapsOrPausedIntervals() {
        var clock=new EgoWetnessTickClock(); Object entity=new Object();
        assertEquals(.05,clock.consume(entity,1,true));
        assertEquals(0,clock.consume(entity,1,true));
        assertEquals(0,clock.consume(entity,10000,false));
        assertEquals(0,clock.consume(entity,10000,true));
        assertEquals(.05,clock.consume(entity,20000,true));
        assertEquals(.05,clock.consume(entity,-20,true));
        assertEquals(3,clock.consumedTicks());
    }
    @Test void respawnAndOtherPlayersHaveIndependentClocks() {
        var clock=new EgoWetnessTickClock();
        assertEquals(.05,clock.consume(new Object(),1,true));
        assertEquals(.05,clock.consume(new Object(),1,true));
        assertEquals(.05,new EgoWetnessTickClock().consume(new Object(),1,true));
    }
    @Test void noticesHaveHysteresisAndCooldownAndNoRepeatedSaturation() {
        var gate=new EgoWetnessNoticeGate();
        assertNull(gate.observe(0,0));
        assertEquals("message.immersive_ego.wetness.damp",gate.observe(.2,1));
        assertNull(gate.observe(.7,2));
        assertEquals("message.immersive_ego.wetness.soaked",gate.observe(.7,201));
        assertNull(gate.observe(.64,500));
        assertNull(gate.observe(1,700));
        assertEquals("message.immersive_ego.wetness.damp",gate.observe(.49,701));
        assertNull(gate.observe(.14,1000));
        assertEquals("message.immersive_ego.wetness.dry",gate.observe(.02,1001));
    }
}
