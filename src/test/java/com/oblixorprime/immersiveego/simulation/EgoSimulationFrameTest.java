package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.environment.EgoClimateProfileCatalog;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentSnapshot;
import com.oblixorprime.immersiveego.environment.EgoExposureSample;
import com.oblixorprime.immersiveego.environment.EgoSeasonPhase;
import com.oblixorprime.immersiveego.nutrition.EgoDietaryMemory;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionContext;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionGroup;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.physiology.EgoEncumbranceContext;
import com.oblixorprime.immersiveego.physiology.EgoEncumbranceTier;
import com.oblixorprime.immersiveego.physiology.EgoHydrationDemandContext;
import com.oblixorprime.immersiveego.physiology.EgoRespiratorySample;
import com.oblixorprime.immersiveego.physiology.EgoRespiratoryStrainCalculator;
import com.oblixorprime.immersiveego.physiology.EgoStaminaActivityContext;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyContext;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.sleep.EgoSleepContext;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

final class EgoSimulationFrameTest {
    @Test
    void noOpFramePreservesInputState() {
        EgoState state = EgoState.defaultState();

        EgoSimulationFrame frame = EgoSimulationFrame.noOp(200L, 20, state);

        assertEquals(200L, frame.gameTime());
        assertEquals(20, frame.intervalTicks());
        assertEquals(state, frame.inputState());
        assertEquals(state, frame.outputState());
        assertFalse(frame.changed());
    }

    @Test
    void changedReportsOutputStateDifference() {
        EgoState state = EgoState.defaultState();
        EgoState changed = state.withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.25D);

        EgoSimulationFrame frame = new EgoSimulationFrame(1L, 20, state, changed);

        assertTrue(frame.changed());
    }

    @Test
    void schedulerFrameAppliesEnvironmentThermalSnapshotBeforeDerivedValues() {
        EgoState state = EgoState.defaultState();
        EgoEnvironmentSnapshot snapshot = EgoEnvironmentSnapshot.from(240L, new EgoExposureSample(
                EgoClimateProfileCatalog.defaultById(EgoClimateProfileCatalog.COLD_WET).orElseThrow(),
                EgoSeasonPhase.WINTER,
                1.0D,
                0.0D,
                1.0D));

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                240L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.of(snapshot));

        assertEquals(snapshot.thermalSnapshot().thermalStrain(), frame.outputState().thermalStrain(), 1.0E-9D);
        assertTrue(frame.outputState().egoLoad() > 0.0D);
        assertTrue(frame.outputState().readiness() < 1.0D);
    }

    @Test
    void schedulerFrameCanApplyHydrationDemandBeforeDerivedValues() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.75D)
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, 1.0D)
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, 1.0D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 1.0D);

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                1200L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.empty(),
                Optional.of(new EgoHydrationDemandContext(20, 1.0D)));

        assertTrue(frame.outputState().hydration() < state.hydration());
        assertTrue(frame.outputState().egoLoad() > 0.0D);
        assertTrue(frame.outputState().readiness() < 1.0D);
    }

    @Test
    void schedulerFrameCanApplyRespiratorySampleBeforeHydrationAndDerivedValues() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.75D);
        EgoRespiratorySample sample = new EgoRespiratorySample(0.5D, 0.25D, 0.0D, 0.35D, 1.0D);

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                1200L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.empty(),
                Optional.of(sample),
                Optional.of(new EgoHydrationDemandContext(20, 1.0D)));

        assertEquals(
                EgoRespiratoryStrainCalculator.calculate(sample),
                frame.outputState().respiratoryStrain(),
                1.0E-12D);
        assertTrue(frame.outputState().hydration() < state.hydration());
        assertTrue(frame.outputState().egoLoad() > 0.0D);
        assertTrue(frame.outputState().readiness() < 1.0D);
    }

    @Test
    void schedulerFrameCanApplyStaminaActivityBeforeDerivedValues() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.80D);

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                1200L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new EgoStaminaActivityContext(
                        1.0D,
                        true,
                        false,
                        false,
                        false,
                        20,
                        1.0D)));

        assertEquals(0.76D, frame.outputState().stamina(), 1.0E-12D);
        assertTrue(frame.outputState().readiness() < state.readiness());
    }

    @Test
    void schedulerFrameCanApplyEncumbranceLoadBeforeDerivedValues() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.80D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.10D);

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                1200L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new EgoEncumbranceContext(
                        EgoEncumbranceTier.OVERBURDENED,
                        1.0D,
                        1.0D,
                        20)));

        assertEquals(0.788D, frame.outputState().stamina(), 1.0E-12D);
        assertEquals(0.104D, frame.outputState().fatigue(), 1.0E-12D);
        assertTrue(frame.outputState().egoLoad() > 0.0D);
        assertTrue(frame.outputState().readiness() < state.readiness());
    }

    @Test
    void schedulerFrameCanApplySleepRecoveryBeforeDerivedValues() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.FOCUS, 0.50D);

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                18000L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new EgoSleepContext(
                        18000L,
                        true,
                        200,
                        20,
                        1.0D)));

        assertEquals(0.482D, frame.outputState().fatigue(), 1.0E-12D);
        assertEquals(0.512D, frame.outputState().recoveryCapacity(), 1.0E-12D);
        assertEquals(0.508D, frame.outputState().focus(), 1.0E-12D);
        assertTrue(frame.outputState().egoLoad() < state.fatigue());
        assertTrue(frame.outputState().readiness() > 0.0D);
    }

    @Test
    void schedulerFrameCanApplyNutritionMemoryBeforeDerivedValues() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.NUTRITION_BALANCE, 0.40D);
        EgoDietaryMemory memory = new EgoDietaryMemory(Map.of(
                EgoNutritionGroup.GRAINS, 0.80D,
                EgoNutritionGroup.PROTEIN, 0.80D,
                EgoNutritionGroup.VEGETABLES, 0.80D,
                EgoNutritionGroup.FRUIT, 0.80D,
                EgoNutritionGroup.FATS, 0.80D,
                EgoNutritionGroup.MINERALS, 0.80D), 6, 1.0D);

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                20000L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(EgoNutritionContext.fromMemory(20, memory)));

        assertEquals(0.41D, frame.outputState().nutritionBalance(), 1.0E-12D);
        assertTrue(frame.outputState().readiness() < state.readiness());
    }

    @Test
    void schedulerFrameCanApplyPsychologyAndOriginBenefitsBeforeDerivedValues() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, 0.50D);

        EgoSimulationFrame frame = EgoSimulationFrameCalculator.advanceFrame(
                22000L,
                state,
                EgoDynamicServerConfig.defaults(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new EgoPsychologyContext(1.0D, 1.0D, 0.0D, 0.0D, 20, 1.0D)),
                Optional.of(new EgoOriginState(1, "highland", 0.50D, 0.0D, 0.50D, 0.0D)));

        assertEquals(0.46D, frame.outputState().thermalStrain(), 1.0E-12D);
        assertEquals(0.47D, frame.outputState().respiratoryStrain(), 1.0E-12D);
        assertTrue(frame.outputState().stress() > state.stress());
        assertTrue(frame.outputState().readiness() < state.readiness());
    }

    @Test
    void rejectsInvalidFrameClockValues() {
        EgoState state = EgoState.defaultState();

        assertThrows(IllegalArgumentException.class, () -> EgoSimulationFrame.noOp(-1L, 20, state));
        assertThrows(IllegalArgumentException.class, () -> EgoSimulationFrame.noOp(1L, 0, state));
    }
}
