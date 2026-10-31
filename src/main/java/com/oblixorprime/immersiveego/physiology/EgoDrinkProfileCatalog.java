package com.oblixorprime.immersiveego.physiology;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class EgoDrinkProfileCatalog {
    public static final String WATER_POTION = "minecraft:potion/water";
    public static final String MILK_BUCKET = "minecraft:milk_bucket";
    public static final String HONEY_BOTTLE = "minecraft:honey_bottle";

    private static final List<EgoDrinkProfile> DEFAULTS = List.of(
            new EgoDrinkProfile(WATER_POTION, EgoWaterQuality.POTABLE, 0.20D, 0.05D, 0.02D),
            new EgoDrinkProfile(MILK_BUCKET, EgoWaterQuality.POTABLE, 0.16D, 0.20D, 0.01D),
            new EgoDrinkProfile(HONEY_BOTTLE, EgoWaterQuality.POTABLE, 0.08D, 0.04D, 0.01D));
    private static final Map<String, EgoDrinkProfile> DEFAULTS_BY_ID = index(DEFAULTS);

    private EgoDrinkProfileCatalog() {
    }

    public static List<EgoDrinkProfile> defaults() {
        return DEFAULTS;
    }

    public static Optional<EgoDrinkProfile> defaultById(String id) {
        return Optional.ofNullable(DEFAULTS_BY_ID.get(id));
    }

    private static Map<String, EgoDrinkProfile> index(List<EgoDrinkProfile> profiles) {
        Map<String, EgoDrinkProfile> byId = new LinkedHashMap<>();
        for (EgoDrinkProfile profile : profiles) {
            EgoDrinkProfile previous = byId.put(profile.id(), profile);
            if (previous != null) {
                throw new IllegalArgumentException("duplicate drink profile id: " + profile.id());
            }
        }
        return Map.copyOf(byId);
    }
}
