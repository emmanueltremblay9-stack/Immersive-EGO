package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculator;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentSnapshot;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionContext;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionService;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.origins.EgoOriginsService;
import com.oblixorprime.immersiveego.physiology.EgoEncumbranceContext;
import com.oblixorprime.immersiveego.physiology.EgoEncumbranceService;
import com.oblixorprime.immersiveego.physiology.EgoHydrationDemandContext;
import com.oblixorprime.immersiveego.physiology.EgoHydrationService;
import com.oblixorprime.immersiveego.physiology.EgoRespirationService;
import com.oblixorprime.immersiveego.physiology.EgoRespiratorySample;
import com.oblixorprime.immersiveego.physiology.EgoStaminaActivityContext;
import com.oblixorprime.immersiveego.physiology.EgoStaminaService;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyContext;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyService;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.sleep.EgoSleepContext;
import com.oblixorprime.immersiveego.sleep.EgoSleepService;
import java.util.Optional;

public final class EgoSimulationFrameCalculator {
    private EgoSimulationFrameCalculator() {
    }

    public static EgoSimulationFrame advanceFrame(long gameTime, EgoState state, EgoDynamicServerConfig config) {
        return advanceFrame(gameTime, state, config, Optional.empty());
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot) {
        return advanceFrame(gameTime, state, config, environmentSnapshot, Optional.empty());
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoHydrationDemandContext> hydrationDemandContext) {
        return advanceFrame(gameTime, state, config, environmentSnapshot, Optional.empty(), hydrationDemandContext);
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoRespiratorySample> respiratorySample,
            Optional<EgoHydrationDemandContext> hydrationDemandContext) {
        return advanceFrame(
                gameTime,
                state,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext,
                Optional.empty());
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoRespiratorySample> respiratorySample,
            Optional<EgoHydrationDemandContext> hydrationDemandContext,
            Optional<EgoStaminaActivityContext> staminaActivityContext) {
        return advanceFrame(
                gameTime,
                state,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext,
                staminaActivityContext,
                Optional.empty());
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoRespiratorySample> respiratorySample,
            Optional<EgoHydrationDemandContext> hydrationDemandContext,
            Optional<EgoStaminaActivityContext> staminaActivityContext,
            Optional<EgoEncumbranceContext> encumbranceContext) {
        return advanceFrame(
                gameTime,
                state,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext,
                staminaActivityContext,
                encumbranceContext,
                Optional.empty());
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoRespiratorySample> respiratorySample,
            Optional<EgoHydrationDemandContext> hydrationDemandContext,
            Optional<EgoStaminaActivityContext> staminaActivityContext,
            Optional<EgoEncumbranceContext> encumbranceContext,
            Optional<EgoSleepContext> sleepContext) {
        return advanceFrame(
                gameTime,
                state,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext,
                staminaActivityContext,
                encumbranceContext,
                sleepContext,
                Optional.empty());
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoRespiratorySample> respiratorySample,
            Optional<EgoHydrationDemandContext> hydrationDemandContext,
            Optional<EgoStaminaActivityContext> staminaActivityContext,
            Optional<EgoEncumbranceContext> encumbranceContext,
            Optional<EgoSleepContext> sleepContext,
            Optional<EgoNutritionContext> nutritionContext) {
        return advanceFrame(
                gameTime,
                state,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext,
                staminaActivityContext,
                encumbranceContext,
                sleepContext,
                nutritionContext,
                Optional.empty(),
                Optional.empty());
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoRespiratorySample> respiratorySample,
            Optional<EgoHydrationDemandContext> hydrationDemandContext,
            Optional<EgoStaminaActivityContext> staminaActivityContext,
            Optional<EgoEncumbranceContext> encumbranceContext,
            Optional<EgoSleepContext> sleepContext,
            Optional<EgoNutritionContext> nutritionContext,
            Optional<EgoPsychologyContext> psychologyContext,
            Optional<EgoOriginState> originState) {
        EgoState environmentState = applyEnvironmentSnapshot(state, environmentSnapshot);
        EgoState respiratoryState = applyRespiratorySample(environmentState, respiratorySample);
        EgoState hydrationState = applyHydrationDemand(respiratoryState, hydrationDemandContext);
        EgoState staminaState = applyStaminaActivity(hydrationState, staminaActivityContext);
        EgoState physiologyState = applyEncumbranceLoad(staminaState, encumbranceContext);
        EgoState sleepState = applySleep(physiologyState, sleepContext);
        EgoState nutritionState = applyNutrition(sleepState, nutritionContext);
        EgoState psychologyState = applyPsychology(nutritionState, psychologyContext);
        EgoState originAdjustedState = applyOriginBenefits(psychologyState, originState);
        return new EgoSimulationFrame(
                gameTime,
                config.schedulerIntervalTicks(),
                state,
                EgoDerivedStateCalculator.calculate(originAdjustedState));
    }

    private static EgoState applyEnvironmentSnapshot(
            EgoState state,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot) {
        return environmentSnapshot
                .map(snapshot -> state.withDisplayValue(
                        EgoAttributeCatalog.THERMAL_STRAIN,
                        snapshot.thermalSnapshot().thermalStrain()))
                .orElse(state);
    }

    private static EgoState applyRespiratorySample(
            EgoState state,
            Optional<EgoRespiratorySample> respiratorySample) {
        return respiratorySample
                .map(sample -> EgoRespirationService.applySample(state, sample))
                .orElse(state);
    }

    private static EgoState applyStaminaActivity(
            EgoState state,
            Optional<EgoStaminaActivityContext> staminaActivityContext) {
        return staminaActivityContext
                .map(context -> EgoStaminaService.applyActivity(state, context))
                .orElse(state);
    }

    private static EgoState applyEncumbranceLoad(
            EgoState state,
            Optional<EgoEncumbranceContext> encumbranceContext) {
        return encumbranceContext
                .map(context -> EgoEncumbranceService.applyLoad(state, context))
                .orElse(state);
    }

    private static EgoState applySleep(
            EgoState state,
            Optional<EgoSleepContext> sleepContext) {
        return sleepContext
                .map(context -> EgoSleepService.applySleep(state, context))
                .orElse(state);
    }

    private static EgoState applyHydrationDemand(
            EgoState state,
            Optional<EgoHydrationDemandContext> hydrationDemandContext) {
        return hydrationDemandContext
                .map(context -> EgoHydrationService.applyDemand(state, context))
                .orElse(state);
    }

    private static EgoState applyNutrition(
            EgoState state,
            Optional<EgoNutritionContext> nutritionContext) {
        return nutritionContext
                .map(context -> EgoNutritionService.applyNutrition(state, context))
                .orElse(state);
    }

    private static EgoState applyPsychology(
            EgoState state,
            Optional<EgoPsychologyContext> psychologyContext) {
        return psychologyContext
                .map(context -> EgoPsychologyService.applyPsychology(state, context))
                .orElse(state);
    }

    private static EgoState applyOriginBenefits(
            EgoState state,
            Optional<EgoOriginState> originState) {
        return originState
                .map(acclimation -> EgoOriginsService.applyBenefits(state, acclimation))
                .orElse(state);
    }
}
