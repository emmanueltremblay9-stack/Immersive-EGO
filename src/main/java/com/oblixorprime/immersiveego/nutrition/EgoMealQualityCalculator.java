package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoMealQualityCalculator {
    private static final double COVERAGE_THRESHOLD = 0.15D;

    private EgoMealQualityCalculator() {
    }

    public static EgoMealQuality calculate(EgoDietaryMemory memory) {
        Objects.requireNonNull(memory, "memory");
        double average = memory.averageLevel();
        double coverage = memory.coverage(COVERAGE_THRESHOLD);
        double variety = EgoMath.clamp01(memory.recentSourceCount() / 6.0D);
        double minimum = memory.minimumLevel();
        double excessPenalty = excessPenalty(memory);

        double mealQuality = EgoMath.clamp01(0.45D * average + 0.35D * coverage + 0.20D * variety);
        double gutHealth = EgoMath.clamp01(0.50D * coverage + 0.30D * memory.varietyScore() + 0.20D * (1.0D - excessPenalty));
        if (coverage >= 1.0D) {
            gutHealth = EgoMath.clamp01(0.80D * gutHealth + 0.20D * minimum);
        }
        return new EgoMealQuality(mealQuality, gutHealth);
    }

    private static double excessPenalty(EgoDietaryMemory memory) {
        double penalty = 0.0D;
        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            penalty = Math.max(penalty, Math.max(0.0D, memory.level(group) - 0.90D) / 0.10D);
        }
        return EgoMath.clamp01(penalty);
    }
}
