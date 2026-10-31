package com.oblixorprime.immersiveego.origins;

import com.oblixorprime.immersiveego.environment.EgoEnvironmentSnapshot;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class EgoOriginsRuntime {
    private EgoOriginsRuntime() {
    }

    public static Optional<EgoOriginState> update(
            ServerPlayer player,
            Optional<EgoEnvironmentSnapshot> environmentSnapshot,
            int intervalTicks) {
        if (!EgoOriginsModule.enabled()) {
            return Optional.empty();
        }

        EgoOriginState assigned = EgoOriginsService.assignIfMissing(player);
        EgoOriginState updated = EgoOriginsService.advanceAcclimation(
                assigned,
                environmentSnapshot,
                player.getAttributeValue(EgoAttributes.ACCLIMATION_GAIN),
                intervalTicks);
        if (!updated.equals(assigned)) {
            player.setData(EgoAttachments.EGO_ORIGIN_STATE.get(), updated);
        }
        return Optional.of(updated);
    }
}
