package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.compat.marieslib.EgoMariesLibFacade;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class EgoNutritionRuntime {
    private EgoNutritionRuntime() {
    }

    public static Optional<EgoNutritionContext> context(ServerPlayer player, int intervalTicks) {
        if (!EgoNutritionModule.enabled()) {
            return Optional.empty();
        }
        return Optional.of(EgoNutritionContext.fromMemory(
                intervalTicks,
                EgoMariesLibFacade.readDietaryMemory(player)));
    }
}
