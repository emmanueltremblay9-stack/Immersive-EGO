package com.oblixorprime.immersiveego.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class EgoVanillaFoodNutritionCatalogTest {
    @Test
    void catalogContainsUniqueVanillaFoodProfiles() {
        Set<String> itemPaths = new HashSet<>();
        for (EgoFoodNutritionProfile profile : EgoVanillaFoodNutritionCatalog.defaults()) {
            assertTrue(itemPaths.add(profile.itemPath()));
            assertTrue(profile.itemId().startsWith("minecraft:"));
            assertFalse(profile.groupAmounts().isEmpty());
            assertTrue(profile.groupAmounts().values().stream().allMatch(amount -> amount > 0.0D));
        }
    }

    @Test
    void classifiesExpectedFoodGroupsWithoutTreatingEggAsFood() {
        assertEquals(0.55D, EgoVanillaFoodNutritionCatalog.defaultByItemPath("bread")
                .orElseThrow()
                .amount(EgoNutritionGroup.GRAINS), 1.0E-12D);
        assertEquals(0.58D, EgoVanillaFoodNutritionCatalog.defaultByItemPath("cooked_beef")
                .orElseThrow()
                .amount(EgoNutritionGroup.PROTEIN), 1.0E-12D);
        assertEquals(0.0D, EgoVanillaFoodNutritionCatalog.defaultByItemPath("egg")
                .map(profile -> profile.amount(EgoNutritionGroup.PROTEIN))
                .orElse(0.0D), 1.0E-12D);
    }
}
