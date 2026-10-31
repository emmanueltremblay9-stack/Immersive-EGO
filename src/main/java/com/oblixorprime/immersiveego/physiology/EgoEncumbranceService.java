package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoEncumbranceService {
    private EgoEncumbranceService() {
    }

    public static EgoState applyLoad(EgoState state, EgoEncumbranceContext context) {
        Objects.requireNonNull(state, "state");
        EgoEncumbranceLoadDelta delta = EgoEncumbranceLoadCalculator.calculate(context);
        return state.withDisplayValue(
                EgoAttributeCatalog.STAMINA,
                EgoMath.clamp01(state.stamina() - delta.staminaCost()))
                .withDisplayValue(
                        EgoAttributeCatalog.FATIGUE,
                        EgoMath.clamp01(state.fatigue() + delta.fatigueGain()));
    }
}
