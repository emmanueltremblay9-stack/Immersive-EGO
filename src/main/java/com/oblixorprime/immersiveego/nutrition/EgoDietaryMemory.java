package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public record EgoDietaryMemory(
        Map<EgoNutritionGroup, Double> groupLevels,
        int recentSourceCount,
        double varietyScore) {
    public EgoDietaryMemory {
        Objects.requireNonNull(groupLevels, "groupLevels");
        if (recentSourceCount < 0) {
            throw new IllegalArgumentException("recentSourceCount must be non-negative");
        }
        EnumMap<EgoNutritionGroup, Double> sanitized = new EnumMap<>(EgoNutritionGroup.class);
        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            sanitized.put(group, sanitizeNormalized(groupLevels.get(group)));
        }
        groupLevels = Collections.unmodifiableMap(sanitized);
        varietyScore = sanitizeNormalized(varietyScore);
    }

    public static EgoDietaryMemory empty() {
        return new EgoDietaryMemory(Map.of(), 0, 0.0D);
    }

    public double level(EgoNutritionGroup group) {
        Objects.requireNonNull(group, "group");
        return groupLevels.getOrDefault(group, 0.0D);
    }

    public double averageLevel() {
        double total = 0.0D;
        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            total += level(group);
        }
        return total / EgoNutritionGroup.all().size();
    }

    public double minimumLevel() {
        double minimum = 1.0D;
        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            minimum = Math.min(minimum, level(group));
        }
        return minimum;
    }

    public double coverage(double threshold) {
        if (!Double.isFinite(threshold)) {
            throw new IllegalArgumentException("threshold must be finite");
        }
        int covered = 0;
        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            if (level(group) >= threshold) {
                covered++;
            }
        }
        return covered / (double) EgoNutritionGroup.all().size();
    }

    public static double sanitizeNormalized(Number value) {
        if (value == null) {
            return 0.0D;
        }
        double parsed = value.doubleValue();
        if (!Double.isFinite(parsed)) {
            return 0.0D;
        }
        return EgoMath.clamp01(parsed);
    }
}
