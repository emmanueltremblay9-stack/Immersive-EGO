package com.oblixorprime.immersiveego.nutrition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

final class EgoNutritionGroupTest {
    @Test
    void exposesSixStableMarieslibValueKeys() {
        assertEquals(6, EgoNutritionGroup.all().size());
        assertEquals(EgoNutritionGroup.all().size(), new HashSet<>(EgoNutritionGroup.all()).size());

        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            assertTrue(group.valueKey().matches("[a-z0-9_]{1,64}"));
            assertTrue(group.valueKey().startsWith("ego_"));
            assertTrue(EgoNutritionGroup.fromValueKey(group.valueKey()).isPresent());
        }
    }
}
