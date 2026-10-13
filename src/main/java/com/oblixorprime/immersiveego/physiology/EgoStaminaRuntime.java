package com.oblixorprime.immersiveego.physiology;

import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class EgoStaminaRuntime {
    private EgoStaminaRuntime() {
    }

    public static Optional<EgoStaminaActivityContext> activityContext(ServerPlayer player, int intervalTicks) {
        if (!EgoPhysiologyModule.enabled()) {
            return Optional.empty();
        }
        return Optional.of(EgoStaminaActivitySampler.sample(player, intervalTicks));
    }
}
