package com.oblixorprime.immersiveego.nutrition;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public record EgoFoodNutritionProfile(
        String itemPath,
        Map<EgoNutritionGroup, Double> groupAmounts) {
    private static final Pattern VANILLA_ITEM_PATH = Pattern.compile("[a-z0-9_/]+");

    public EgoFoodNutritionProfile {
        Objects.requireNonNull(itemPath, "itemPath");
        Objects.requireNonNull(groupAmounts, "groupAmounts");
        if (!VANILLA_ITEM_PATH.matcher(itemPath).matches()) {
            throw new IllegalArgumentException("itemPath must be a vanilla resource path: " + itemPath);
        }

        EnumMap<EgoNutritionGroup, Double> sanitized = new EnumMap<>(EgoNutritionGroup.class);
        for (Map.Entry<EgoNutritionGroup, Double> entry : groupAmounts.entrySet()) {
            EgoNutritionGroup group = Objects.requireNonNull(entry.getKey(), "group");
            double amount = Objects.requireNonNull(entry.getValue(), "amount");
            if (!Double.isFinite(amount) || amount < 0.0D) {
                throw new IllegalArgumentException("nutrition amount must be finite and non-negative");
            }
            if (amount > 0.0D) {
                sanitized.put(group, amount);
            }
        }
        if (sanitized.isEmpty()) {
            throw new IllegalArgumentException("food profile must include at least one nutrition group");
        }
        groupAmounts = Collections.unmodifiableMap(sanitized);
    }

    public String itemId() {
        return "minecraft:" + itemPath;
    }

    public double amount(EgoNutritionGroup group) {
        Objects.requireNonNull(group, "group");
        return groupAmounts.getOrDefault(group, 0.0D);
    }
}
