package com.oblixorprime.immersiveego.wetness;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoader;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentSampler;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentService;
import com.oblixorprime.immersiveego.environment.EgoExposureSample;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/** Observes one server tick, calculates a pure step, then writes the single attachment owner. */
public final class EgoWetnessRuntime {
    private static final Map<UUID, Session> SESSIONS = new HashMap<>();
    private static final Set<UUID> MUTED = new HashSet<>();

    private EgoWetnessRuntime() {}

    public record Applied(String dimension, int playerTick, EgoWetnessExposure exposure, String climate,
            double before, EgoWetnessModel.Step step, long consumedTicks) {}

    private static final class Session {
        final EgoWetnessTickClock clock = new EgoWetnessTickClock();
        final EgoWetnessNoticeGate notices = new EgoWetnessNoticeGate();
        EgoExposureSample previousSample;
        Applied lastApplied;
    }

    public static void register(IEventBus bus) {
        bus.addListener(EventPriority.LOWEST, EgoWetnessRuntime::onClone);
        bus.addListener(EgoWetnessRuntime::onDimension);
        bus.addListener(EgoWetnessRuntime::onStopped);
    }

    public static String activity(ServerPlayer player) {
        if (!player.isAlive()) return "dead";
        if (!EgoCoreConfig.ENABLE_SIMULATION.get()
                || !EgoEnvironmentService.environmentModuleEnabled()
                || !EgoDynamicServerConfigLoader.current().schedulerEnabled()) return "disabled";
        GameType mode = player.gameMode.getGameModeForPlayer();
        if (mode != GameType.SURVIVAL && mode != GameType.ADVENTURE) return "mode_paused";
        if (!player.getData(EgoAttachments.EGO_WETNESS.get()).supported()) return "data_quarantined";
        return "active";
    }

    public static double thermalWetness(ServerPlayer player) {
        return activity(player).equals("active") ? player.getData(EgoAttachments.EGO_WETNESS.get()).wetness() : 0.0D;
    }

    public static EgoWetnessExposure exposure(ServerPlayer player) {
        boolean bodyInWater = player.isInWater();
        boolean rain = player.serverLevel().isRainingAt(player.blockPosition())
                && player.serverLevel().getBiome(player.blockPosition()).value()
                        .getPrecipitationAt(player.blockPosition()) == Biome.Precipitation.RAIN;
        return EgoWetnessExposure.observed(bodyInWater, rain);
    }

    /** Invoked once before the thermal frame; never invoked by a getter, save or cache refresh. */
    public static void advancePlayerTick(ServerPlayer player) {
        Session session = SESSIONS.computeIfAbsent(player.getUUID(), ignored -> new Session());
        boolean active = activity(player).equals("active");
        double seconds = session.clock.consume(player, player.tickCount, active);
        if (!active) {
            session.previousSample = null;
            EgoEnvironmentService.remove(player.getUUID());
            return;
        }
        if (seconds == 0.0D) return;
        EgoExposureSample sample = EgoEnvironmentSampler.sample(player);
        if (!sample.equals(session.previousSample)) {
            EgoEnvironmentService.remove(player.getUUID());
        }
        session.previousSample = sample;
        EgoWetnessExposure source = exposure(player);
        EgoWetnessState before = player.getData(EgoAttachments.EGO_WETNESS.get());
        EgoWetnessModel.Step step = EgoWetnessModel.advance(before, source, sample.climateProfile(), seconds);
        // Independent of thermal/frame.changed(): W must be saved even under a dominant unchanged heat load.
        if (!before.equals(step.state())) player.setData(EgoAttachments.EGO_WETNESS.get(), step.state());
        session.lastApplied = new Applied(player.level().dimension().location().toString(), player.tickCount,
                source, sample.climateProfile().id(), before.wetness(), step, session.clock.consumedTicks());
        String notice = session.notices.observe(step.state().wetness(), session.clock.consumedTicks());
        if (notice != null && EgoCoreConfig.SHOW_WETNESS_NOTICES.get() && !MUTED.contains(player.getUUID())) {
            player.displayClientMessage(Component.translatable(notice), true);
        }
    }

    public static Applied lastApplied(ServerPlayer player) {
        Session session = SESSIONS.get(player.getUUID());
        return session == null ? null : session.lastApplied;
    }

    public static void setNotices(ServerPlayer player, boolean enabled) {
        if (enabled) MUTED.remove(player.getUUID()); else MUTED.add(player.getUUID());
    }

    public static void remove(UUID id) {
        SESSIONS.remove(id);
        MUTED.remove(id);
    }

    private static void onClone(PlayerEvent.Clone event) {
        if (event.getEntity().level().isClientSide()) return;
        event.getEntity().setData(EgoAttachments.EGO_WETNESS.get(), event.isWasDeath()
                ? EgoWetnessState.dry() : event.getOriginal().getData(EgoAttachments.EGO_WETNESS.get()));
        SESSIONS.remove(event.getOriginal().getUUID());
        SESSIONS.remove(event.getEntity().getUUID());
        EgoEnvironmentService.remove(event.getOriginal().getUUID());
        EgoEnvironmentService.remove(event.getEntity().getUUID());
    }

    private static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        // Keep the duplicate-tick guard: a dimension transition must not consume the same tick twice.
        Session session = SESSIONS.get(event.getEntity().getUUID());
        if (session != null) {
            session.previousSample = null;
            session.lastApplied = null;
        }
        EgoEnvironmentService.remove(event.getEntity().getUUID());
    }

    private static void onStopped(ServerStoppedEvent event) {
        SESSIONS.clear();
        MUTED.clear();
    }
}
