package com.oblixorprime.immersiveego.sleep;

import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class EgoSleepRuntime {
    private EgoSleepRuntime() {
    }

    public static Optional<EgoSleepContext> context(ServerPlayer player, int intervalTicks) {
        if (!EgoSleepModule.enabled()) {
            return Optional.empty();
        }
        return Optional.of(EgoSleepSampler.sample(player, intervalTicks));
    }
}
