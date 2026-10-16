package com.oblixorprime.immersiveego.physiology;

import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class EgoRespirationRuntime {
    private EgoRespirationRuntime() {
    }

    public static Optional<EgoRespiratorySample> sample(ServerPlayer player) {
        if (!EgoPhysiologyModule.enabled()) {
            return Optional.empty();
        }
        return Optional.of(EgoRespiratorySampler.sample(player));
    }
}
