package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoader;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentSnapshot;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentService;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionContext;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionRuntime;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.origins.EgoOriginsRuntime;
import com.oblixorprime.immersiveego.physiology.EgoEncumbranceContext;
import com.oblixorprime.immersiveego.physiology.EgoEncumbranceRuntime;
import com.oblixorprime.immersiveego.physiology.EgoHydrationDemandContext;
import com.oblixorprime.immersiveego.physiology.EgoHydrationEvents;
import com.oblixorprime.immersiveego.physiology.EgoInventoryWeightSnapshot;
import com.oblixorprime.immersiveego.physiology.EgoInventoryWeightService;
import com.oblixorprime.immersiveego.physiology.EgoRespirationRuntime;
import com.oblixorprime.immersiveego.physiology.EgoRespiratorySample;
import com.oblixorprime.immersiveego.physiology.EgoStaminaActivityContext;
import com.oblixorprime.immersiveego.physiology.EgoStaminaRuntime;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyContext;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyRuntime;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.sleep.EgoSleepContext;
import com.oblixorprime.immersiveego.sleep.EgoSleepRuntime;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class EgoSimulationScheduler {
    private EgoSimulationScheduler() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoSimulationScheduler::onPlayerTick);
        gameEventBus.addListener(EgoSimulationScheduler::onPlayerLoggedOut);
    }

    public static EgoSimulationFrame advanceFrame(long gameTime, EgoState state, EgoDynamicServerConfig config) {
        return EgoSimulationFrameCalculator.advanceFrame(gameTime, state, config);
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot) {
        return EgoSimulationFrameCalculator.advanceFrame(gameTime, state, config, environmentSnapshot);
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoHydrationDemandContext> hydrationDemandContext) {
        return EgoSimulationFrameCalculator.advanceFrame(gameTime, state, config, environmentSnapshot, hydrationDemandContext);
    }

    public static EgoSimulationFrame advanceFrame(
            long gameTime,
            EgoState state,
            EgoDynamicServerConfig config,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            Optional<EgoRespiratorySample> respiratorySample,
            Optional<EgoHydrationDemandContext> hydrationDemandContext) {
        return EgoSimulationFrameCalculator.advanceFrame(
                gameTime,
                state,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext);
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
        return EgoSimulationFrameCalculator.advanceFrame(
                gameTime,
                state,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext,
                staminaActivityContext,
                encumbranceContext,
                sleepContext,
                nutritionContext);
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
        return EgoSimulationFrameCalculator.advanceFrame(
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
                psychologyContext,
                originState);
    }

    private static void onPlayerTick(PlayerTickEvent.Post event) {
        Player entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }
        if (!EgoCoreConfig.ENABLE_SIMULATION.get()) {
            return;
        }

        EgoDynamicServerConfig config = EgoDynamicServerConfigLoader.current();
        EgoSimulationSchedule schedule = EgoSimulationSchedule.from(config);
        long gameTime = player.serverLevel().getGameTime();
        if (!schedule.shouldRun(gameTime)) {
            return;
        }

        EgoState currentState = player.getData(EgoAttachments.EGO_STATE.get());
        Optional<EgoEnvironmentSnapshot> environmentSnapshot = EgoEnvironmentService.refreshPlayer(player, gameTime);
        Optional<EgoInventoryWeightSnapshot> inventoryWeightSnapshot =
                EgoInventoryWeightService.refreshPlayer(player, gameTime);
        Optional<EgoEncumbranceContext> encumbranceContext = EgoEncumbranceRuntime.context(
                player.getUUID(),
                inventoryWeightSnapshot,
                config.schedulerIntervalTicks());
        Optional<EgoRespiratorySample> respiratorySample = EgoRespirationRuntime.sample(player);
        Optional<EgoHydrationDemandContext> hydrationDemandContext =
                EgoHydrationEvents.demandContext(player, config.schedulerIntervalTicks());
        Optional<EgoStaminaActivityContext> staminaActivityContext =
                EgoStaminaRuntime.activityContext(player, config.schedulerIntervalTicks());
        Optional<EgoSleepContext> sleepContext =
                EgoSleepRuntime.context(player, config.schedulerIntervalTicks());
        Optional<EgoNutritionContext> nutritionContext =
                EgoNutritionRuntime.context(player, config.schedulerIntervalTicks());
        Optional<EgoPsychologyContext> psychologyContext =
                EgoPsychologyRuntime.context(player, currentState, config.schedulerIntervalTicks());
        Optional<EgoOriginState> originState =
                EgoOriginsRuntime.update(player, environmentSnapshot, config.schedulerIntervalTicks());
        EgoSimulationFrame frame = advanceFrame(
                gameTime,
                currentState,
                config,
                environmentSnapshot,
                respiratorySample,
                hydrationDemandContext,
                staminaActivityContext,
                encumbranceContext,
                sleepContext,
                nutritionContext,
                psychologyContext,
                originState);
        if (frame.changed()) {
            player.setData(EgoAttachments.EGO_STATE.get(), frame.outputState());
        }
        EgoAttributeMirrorService.updatePlayerMirrors(player, frame.outputState(), config.mirrorUpdateEpsilon());
    }

    private static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        UUID playerId = event.getEntity().getUUID();
        EgoEnvironmentService.remove(playerId);
        EgoInventoryWeightService.remove(playerId);
        EgoEncumbranceRuntime.remove(playerId);
    }
}
