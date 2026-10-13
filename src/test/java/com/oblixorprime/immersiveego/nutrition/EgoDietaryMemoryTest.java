package com.oblixorprime.immersiveego.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class EgoDietaryMemoryTest {
    @Test
    void nonFiniteNutritionLevelsAreTreatedAsMissing() {
        Map<EgoNutritionGroup, Double> levels = new HashMap<>();
        levels.put(EgoNutritionGroup.GRAINS, Double.POSITIVE_INFINITY);
        levels.put(EgoNutritionGroup.PROTEIN, Double.NaN);

        EgoDietaryMemory memory = new EgoDietaryMemory(levels, 2, Double.POSITIVE_INFINITY);

        assertEquals(0.0D, memory.level(EgoNutritionGroup.GRAINS), 1.0E-12D);
        assertEquals(0.0D, memory.level(EgoNutritionGroup.PROTEIN), 1.0E-12D);
        assertEquals(0.0D, memory.varietyScore(), 1.0E-12D);
        assertEquals(0.0D, EgoDietaryMemory.sanitizeNormalized(Float.POSITIVE_INFINITY), 1.0E-12D);
        assertEquals(0.0D, EgoDietaryMemory.sanitizeNormalized(Float.NEGATIVE_INFINITY), 1.0E-12D);
    }

    @Test
    void finiteNutritionLevelsAreStillClamped() {
        EgoDietaryMemory memory = new EgoDietaryMemory(Map.of(
                EgoNutritionGroup.GRAINS, -0.25D,
                EgoNutritionGroup.PROTEIN, 1.25D), 2, 1.25D);

        assertEquals(0.0D, memory.level(EgoNutritionGroup.GRAINS), 1.0E-12D);
        assertEquals(1.0D, memory.level(EgoNutritionGroup.PROTEIN), 1.0E-12D);
        assertEquals(1.0D, memory.varietyScore(), 1.0E-12D);
    }
}
