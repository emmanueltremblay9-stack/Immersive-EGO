package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoStaminaService {
    private EgoStaminaService() {
    }

    public static EgoState applyActivity(EgoState state, EgoStaminaActivityContext context) {
        Objects.requireNonNull(state, "state");
        EgoStaminaActivityDelta delta = EgoStaminaActivityCalculator.calculate(context);
        return state.withDisplayValue(
                EgoAttributeCatalog.STAMINA,
                EgoMath.clamp01(state.stamina() + delta.netChange()));
    }
}
