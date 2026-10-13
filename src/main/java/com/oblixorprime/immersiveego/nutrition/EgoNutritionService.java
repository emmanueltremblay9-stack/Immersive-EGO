package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoNutritionService {
    private static final double MAX_BALANCE_CHANGE_PER_SECOND = 0.01D;

    private EgoNutritionService() {
    }

    public static EgoState applyNutrition(EgoState state, EgoNutritionContext context) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(context, "context");
        double seconds = context.intervalTicks() / 20.0D;
        double changeLimit = MAX_BALANCE_CHANGE_PER_SECOND * seconds;
        double target = context.nutritionBalanceTarget();
        double delta = Math.clamp(target - state.nutritionBalance(), -changeLimit, changeLimit);
        return state.withDisplayValue(
                EgoAttributeCatalog.NUTRITION_BALANCE,
                EgoMath.clamp01(state.nutritionBalance() + delta));
    }
}
