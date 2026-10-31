package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoNutritionContext(
        int intervalTicks,
        EgoDietaryMemory dietaryMemory,
        double mealQuality,
        double gutHealth) {
    public EgoNutritionContext {
        if (intervalTicks <= 0) {
            throw new IllegalArgumentException("intervalTicks must be positive");
        }
        Objects.requireNonNull(dietaryMemory, "dietaryMemory");
        mealQuality = EgoMath.clamp01(mealQuality);
        gutHealth = EgoMath.clamp01(gutHealth);
    }

    public static EgoNutritionContext fromMemory(int intervalTicks, EgoDietaryMemory dietaryMemory) {
        EgoMealQuality quality = EgoMealQualityCalculator.calculate(dietaryMemory);
        return new EgoNutritionContext(intervalTicks, dietaryMemory, quality.mealQuality(), quality.gutHealth());
    }

    public double nutritionBalanceTarget() {
        return EgoMath.clamp01(0.65D * mealQuality + 0.35D * gutHealth);
    }
}
