package com.oblixorprime.immersiveego.origins;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.environment.EgoEnvironmentSnapshot;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class EgoOriginsService {
    private EgoOriginsService() {
    }

    public static EgoOriginState assignIfMissing(ServerPlayer player) {
        EgoOriginState state = player.getData(EgoAttachments.EGO_ORIGIN_STATE.get());
        if (state.assigned()) {
            return state;
        }
        EgoOriginProfile profile = EgoOriginProfileCatalog.forBiomePath(currentBiomePath(player));
        EgoOriginState assigned = state.withOrigin(profile);
        player.setData(EgoAttachments.EGO_ORIGIN_STATE.get(), assigned);
        return assigned;
    }

    public static EgoOriginState advanceAcclimation(
            EgoOriginState state,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            double acclimationGain,
            int intervalTicks) {
        EgoOriginProfile profile = EgoOriginProfileCatalog.defaultById(state.originId())
                .orElse(EgoOriginProfileCatalog.defaultProfile());
        if (environmentSnapshot.isEmpty()) {
            return state;
        }
        EgoEnvironmentSnapshot snapshot = environmentSnapshot.orElseThrow();
        EgoAcclimationContext context = new EgoAcclimationContext(
                intervalTicks,
                profile,
                snapshot.thermalSnapshot().heatPressure(),
                snapshot.thermalSnapshot().coldPressure(),
                0.0D,
                snapshot.exposureSample().wetness(),
                acclimationGain);
        return EgoAcclimationCalculator.advance(state, context);
    }

    public static EgoState applyBenefits(EgoState state, EgoOriginState originState) {
        double thermalRelief = Math.max(originState.heatAcclimation(), originState.coldAcclimation()) * 0.08D;
        double respiratoryRelief = originState.altitudeAcclimation() * 0.06D;
        double comfortGain = originState.humidityAcclimation() * 0.015D;
        return state
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, EgoMath.clamp01(state.thermalStrain() - thermalRelief))
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, EgoMath.clamp01(state.respiratoryStrain() - respiratoryRelief))
                .withDisplayValue(EgoAttributeCatalog.COMFORT, EgoMath.clamp01(state.comfort() + comfortGain));
    }

    private static String currentBiomePath(ServerPlayer player) {
        return player.serverLevel()
                .getBiome(player.blockPosition())
                .unwrapKey()
                .map(key -> key.location().getPath())
                .orElse(EgoOriginProfileCatalog.TEMPERATE);
    }
}
