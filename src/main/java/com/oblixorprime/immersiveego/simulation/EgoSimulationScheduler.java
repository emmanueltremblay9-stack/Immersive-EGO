package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoader;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculator;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class EgoSimulationScheduler {
    private EgoSimulationScheduler() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoSimulationScheduler::onPlayerTick);
    }

    public static EgoSimulationFrame advanceFrame(long gameTime, EgoState state, EgoDynamicServerConfig config) {
        return new EgoSimulationFrame(
                gameTime,
                config.schedulerIntervalTicks(),
                state,
                EgoDerivedStateCalculator.calculate(state));
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
        EgoSimulationFrame frame = advanceFrame(gameTime, currentState, config);
        if (frame.changed()) {
            player.setData(EgoAttachments.EGO_STATE.get(), frame.outputState());
        }
        EgoAttributeMirrorService.updatePlayerMirrors(player, frame.outputState());
    }
}
