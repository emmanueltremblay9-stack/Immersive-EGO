package com.oblixorprime.immersiveego.nutrition;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum EgoNutritionGroup {
    GRAINS("ego_grains", "EGO Grains", 0xD6A94C),
    PROTEIN("ego_protein", "EGO Protein", 0xB34F4A),
    VEGETABLES("ego_vegetables", "EGO Vegetables", 0x4E9B5A),
    FRUIT("ego_fruit", "EGO Fruit", 0xD9678B),
    FATS("ego_fats", "EGO Fats", 0xD2C14E),
    MINERALS("ego_minerals", "EGO Minerals", 0x6C9AA8);

    private static final List<EgoNutritionGroup> ALL = List.copyOf(Arrays.asList(values()));

    private final String valueKey;
    private final String displayName;
    private final int color;

    EgoNutritionGroup(String valueKey, String displayName, int color) {
        if (!valueKey.matches("[a-z0-9_]{1,64}")) {
            throw new IllegalArgumentException("Invalid MariesLib value key: " + valueKey);
        }
        this.valueKey = valueKey;
        this.displayName = displayName;
        this.color = color;
    }

    public static List<EgoNutritionGroup> all() {
        return ALL;
    }

    public static Optional<EgoNutritionGroup> fromValueKey(String valueKey) {
        return ALL.stream()
                .filter(group -> group.valueKey.equals(valueKey))
                .findFirst();
    }

    public String valueKey() {
        return valueKey;
    }

    public String displayName() {
        return displayName;
    }

    public int color() {
        return color;
    }
}
