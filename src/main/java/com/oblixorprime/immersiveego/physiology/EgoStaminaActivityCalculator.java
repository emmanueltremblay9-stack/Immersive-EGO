package com.oblixorprime.immersiveego.physiology;

import java.util.Objects;

public final class EgoStaminaActivityCalculator {
    private static final double REST_RECOVERY_PER_SECOND = 0.012D;
    private static final double LIGHT_RECOVERY_PER_SECOND = 0.004D;
    private static final double USING_ITEM_RECOVERY_MULTIPLIER = 0.50D;
    private static final double MOVEMENT_COST_PER_SECOND = 0.010D;
    private static final double SPRINT_COST_PER_SECOND = 0.030D;
    private static final double SWIMMING_COST_PER_SECOND = 0.018D;
    private static final double AIRBORNE_COST_PER_SECOND = 0.006D;

    private EgoStaminaActivityCalculator() {
    }

    public static EgoStaminaActivityDelta calculate(EgoStaminaActivityContext context) {
        Objects.requireNonNull(context, "context");
        double seconds = context.seconds();
        double costMultiplier = context.costMultiplier();
        double movementCost = context.movementIntensity() * MOVEMENT_COST_PER_SECOND * seconds * costMultiplier;
        double sprintCost = context.sprinting() ? SPRINT_COST_PER_SECOND * seconds * costMultiplier : 0.0D;
        double swimmingCost = context.swimming() ? SWIMMING_COST_PER_SECOND * seconds * costMultiplier : 0.0D;
        double airborneCost = context.airborne() ? AIRBORNE_COST_PER_SECOND * seconds * costMultiplier : 0.0D;
        double recovery = recoveryRate(context) * seconds * context.recoveryMultiplier();
        return new EgoStaminaActivityDelta(
                movementCost,
                sprintCost,
                swimmingCost,
                airborneCost,
                recovery,
                recovery - movementCost - sprintCost - swimmingCost - airborneCost);
    }

    private static double recoveryRate(EgoStaminaActivityContext context) {
        if (context.sprinting() || context.swimming() || context.airborne()) {
            return 0.0D;
        }
        double rate = context.movementIntensity() == 0.0D
                ? REST_RECOVERY_PER_SECOND
                : LIGHT_RECOVERY_PER_SECOND;
        return context.usingItem() ? rate * USING_ITEM_RECOVERY_MULTIPLIER : rate;
    }
}
