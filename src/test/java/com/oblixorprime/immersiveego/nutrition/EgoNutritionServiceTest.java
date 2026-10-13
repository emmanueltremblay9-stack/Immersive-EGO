package com.oblixorprime.immersiveego.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class EgoNutritionServiceTest {
    @Test
    void nutritionBalanceMovesTowardMealQualityTarget() {
        EgoState low = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.NUTRITION_BALANCE, 0.40D);
        EgoNutritionContext highQualityContext = EgoNutritionContext.fromMemory(20, new EgoDietaryMemory(Map.of(
                EgoNutritionGroup.GRAINS, 0.80D,
                EgoNutritionGroup.PROTEIN, 0.80D,
                EgoNutritionGroup.VEGETABLES, 0.80D,
                EgoNutritionGroup.FRUIT, 0.80D,
                EgoNutritionGroup.FATS, 0.80D,
                EgoNutritionGroup.MINERALS, 0.80D), 6, 1.0D));

        EgoState improved = EgoNutritionService.applyNutrition(low, highQualityContext);

        assertEquals(0.41D, improved.nutritionBalance(), 1.0E-12D);
    }

    @Test
    void nutritionBalanceRateLimitScalesBelowOneSecond() {
        EgoState low = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.NUTRITION_BALANCE, 0.40D);
        EgoNutritionContext oneTickContext = EgoNutritionContext.fromMemory(1, new EgoDietaryMemory(Map.of(
                EgoNutritionGroup.GRAINS, 0.80D,
                EgoNutritionGroup.PROTEIN, 0.80D,
                EgoNutritionGroup.VEGETABLES, 0.80D,
                EgoNutritionGroup.FRUIT, 0.80D,
                EgoNutritionGroup.FATS, 0.80D,
                EgoNutritionGroup.MINERALS, 0.80D), 6, 1.0D));

        EgoState improved = EgoNutritionService.applyNutrition(low, oneTickContext);

        assertEquals(0.4005D, improved.nutritionBalance(), 1.0E-12D);
    }

    @Test
    void emptyDietaryMemoryGraduallyLowersHighNutritionBalance() {
        EgoState high = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.NUTRITION_BALANCE, 0.80D);
        EgoNutritionContext emptyContext = EgoNutritionContext.fromMemory(20, EgoDietaryMemory.empty());

        EgoState reduced = EgoNutritionService.applyNutrition(high, emptyContext);

        assertEquals(0.79D, reduced.nutritionBalance(), 1.0E-12D);
    }
}
