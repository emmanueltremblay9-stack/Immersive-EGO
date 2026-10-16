package com.oblixorprime.immersiveego.nutrition;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class EgoVanillaFoodNutritionCatalog {
    private static final List<EgoFoodNutritionProfile> DEFAULTS = List.of(
            profile("apple", 0.0D, 0.0D, 0.0D, 0.35D, 0.0D, 0.04D),
            profile("baked_potato", 0.0D, 0.0D, 0.40D, 0.0D, 0.0D, 0.10D),
            profile("beef", 0.0D, 0.38D, 0.0D, 0.0D, 0.14D, 0.06D),
            profile("beetroot", 0.0D, 0.0D, 0.22D, 0.0D, 0.0D, 0.08D),
            profile("beetroot_soup", 0.0D, 0.0D, 0.50D, 0.0D, 0.0D, 0.14D),
            profile("bread", 0.55D, 0.0D, 0.0D, 0.0D, 0.0D, 0.04D),
            profile("carrot", 0.0D, 0.0D, 0.30D, 0.0D, 0.0D, 0.06D),
            profile("chicken", 0.0D, 0.34D, 0.0D, 0.0D, 0.08D, 0.04D),
            profile("chorus_fruit", 0.0D, 0.0D, 0.0D, 0.32D, 0.0D, 0.05D),
            profile("cod", 0.0D, 0.30D, 0.0D, 0.0D, 0.06D, 0.08D),
            profile("cooked_beef", 0.0D, 0.58D, 0.0D, 0.0D, 0.22D, 0.08D),
            profile("cooked_chicken", 0.0D, 0.48D, 0.0D, 0.0D, 0.12D, 0.06D),
            profile("cooked_cod", 0.0D, 0.42D, 0.0D, 0.0D, 0.08D, 0.12D),
            profile("cooked_mutton", 0.0D, 0.48D, 0.0D, 0.0D, 0.18D, 0.07D),
            profile("cooked_porkchop", 0.0D, 0.56D, 0.0D, 0.0D, 0.24D, 0.08D),
            profile("cooked_rabbit", 0.0D, 0.42D, 0.0D, 0.0D, 0.10D, 0.05D),
            profile("cooked_salmon", 0.0D, 0.46D, 0.0D, 0.0D, 0.16D, 0.12D),
            profile("cookie", 0.25D, 0.0D, 0.0D, 0.0D, 0.10D, 0.02D),
            profile("dried_kelp", 0.0D, 0.0D, 0.12D, 0.0D, 0.0D, 0.18D),
            profile("enchanted_golden_apple", 0.0D, 0.0D, 0.0D, 0.46D, 0.0D, 0.24D),
            profile("glow_berries", 0.0D, 0.0D, 0.0D, 0.24D, 0.0D, 0.05D),
            profile("golden_apple", 0.0D, 0.0D, 0.0D, 0.42D, 0.0D, 0.18D),
            profile("golden_carrot", 0.0D, 0.0D, 0.42D, 0.0D, 0.0D, 0.18D),
            profile("honey_bottle", 0.0D, 0.0D, 0.0D, 0.18D, 0.0D, 0.03D),
            profile("melon_slice", 0.0D, 0.0D, 0.0D, 0.24D, 0.0D, 0.04D),
            profile("mushroom_stew", 0.0D, 0.08D, 0.34D, 0.0D, 0.04D, 0.12D),
            profile("mutton", 0.0D, 0.36D, 0.0D, 0.0D, 0.14D, 0.05D),
            profile("poisonous_potato", 0.0D, 0.0D, 0.16D, 0.0D, 0.0D, 0.04D),
            profile("porkchop", 0.0D, 0.40D, 0.0D, 0.0D, 0.18D, 0.06D),
            profile("potato", 0.0D, 0.0D, 0.24D, 0.0D, 0.0D, 0.06D),
            profile("pufferfish", 0.0D, 0.16D, 0.0D, 0.0D, 0.04D, 0.05D),
            profile("pumpkin_pie", 0.20D, 0.0D, 0.20D, 0.0D, 0.10D, 0.05D),
            profile("rabbit", 0.0D, 0.30D, 0.0D, 0.0D, 0.07D, 0.04D),
            profile("rabbit_stew", 0.12D, 0.34D, 0.26D, 0.0D, 0.08D, 0.12D),
            profile("rotten_flesh", 0.0D, 0.18D, 0.0D, 0.0D, 0.06D, 0.02D),
            profile("salmon", 0.0D, 0.34D, 0.0D, 0.0D, 0.10D, 0.10D),
            profile("spider_eye", 0.0D, 0.08D, 0.0D, 0.0D, 0.02D, 0.02D),
            profile("suspicious_stew", 0.0D, 0.08D, 0.34D, 0.0D, 0.04D, 0.12D),
            profile("sweet_berries", 0.0D, 0.0D, 0.0D, 0.24D, 0.0D, 0.04D),
            profile("tropical_fish", 0.0D, 0.24D, 0.0D, 0.0D, 0.04D, 0.08D));
    private static final Map<String, EgoFoodNutritionProfile> DEFAULTS_BY_ITEM_PATH = index(DEFAULTS);

    private EgoVanillaFoodNutritionCatalog() {
    }

    public static List<EgoFoodNutritionProfile> defaults() {
        return DEFAULTS;
    }

    public static Optional<EgoFoodNutritionProfile> defaultByItemPath(String itemPath) {
        return Optional.ofNullable(DEFAULTS_BY_ITEM_PATH.get(itemPath));
    }

    private static EgoFoodNutritionProfile profile(
            String itemPath,
            double grains,
            double protein,
            double vegetables,
            double fruit,
            double fats,
            double minerals) {
        EnumMap<EgoNutritionGroup, Double> amounts = new EnumMap<>(EgoNutritionGroup.class);
        put(amounts, EgoNutritionGroup.GRAINS, grains);
        put(amounts, EgoNutritionGroup.PROTEIN, protein);
        put(amounts, EgoNutritionGroup.VEGETABLES, vegetables);
        put(amounts, EgoNutritionGroup.FRUIT, fruit);
        put(amounts, EgoNutritionGroup.FATS, fats);
        put(amounts, EgoNutritionGroup.MINERALS, minerals);
        return new EgoFoodNutritionProfile(itemPath, amounts);
    }

    private static void put(EnumMap<EgoNutritionGroup, Double> amounts, EgoNutritionGroup group, double amount) {
        if (amount > 0.0D) {
            amounts.put(group, amount);
        }
    }

    private static Map<String, EgoFoodNutritionProfile> index(List<EgoFoodNutritionProfile> profiles) {
        Map<String, EgoFoodNutritionProfile> byItemPath = new LinkedHashMap<>();
        for (EgoFoodNutritionProfile profile : profiles) {
            EgoFoodNutritionProfile previous = byItemPath.put(profile.itemPath(), profile);
            if (previous != null) {
                throw new IllegalArgumentException("duplicate food nutrition profile: " + profile.itemPath());
            }
        }
        return Map.copyOf(byItemPath);
    }
}
