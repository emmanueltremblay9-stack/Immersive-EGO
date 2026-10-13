package com.oblixorprime.immersiveego.nutrition;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

final class EgoMealQualityCalculatorTest {
    @Test
    void balancedDietScoresHigherThanSparseDiet() {
        EgoMealQuality balanced = EgoMealQualityCalculator.calculate(new EgoDietaryMemory(Map.of(
                EgoNutritionGroup.GRAINS, 0.80D,
                EgoNutritionGroup.PROTEIN, 0.80D,
                EgoNutritionGroup.VEGETABLES, 0.80D,
                EgoNutritionGroup.FRUIT, 0.80D,
                EgoNutritionGroup.FATS, 0.80D,
                EgoNutritionGroup.MINERALS, 0.80D), 6, 1.0D));
        EgoMealQuality sparse = EgoMealQualityCalculator.calculate(new EgoDietaryMemory(Map.of(
                EgoNutritionGroup.GRAINS, 0.80D), 1, 0.125D));

        assertTrue(balanced.mealQuality() > sparse.mealQuality());
        assertTrue(balanced.gutHealth() > sparse.gutHealth());
    }
}
