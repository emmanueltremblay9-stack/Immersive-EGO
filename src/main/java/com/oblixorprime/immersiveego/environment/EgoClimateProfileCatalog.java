package com.oblixorprime.immersiveego.environment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class EgoClimateProfileCatalog {
    public static final String TEMPERATE = "temperate";
    public static final String HOT_DRY = "hot_dry";
    public static final String HOT_HUMID = "hot_humid";
    public static final String COLD_DRY = "cold_dry";
    public static final String COLD_WET = "cold_wet";

    private static final List<EgoClimateProfile> DEFAULTS = List.of(
            new EgoClimateProfile(TEMPERATE, 0.15D, 0.15D, 0.45D, 0.25D, 0.35D, 0.20D),
            new EgoClimateProfile(HOT_DRY, 0.80D, 0.05D, 0.10D, 0.30D, 0.05D, 0.60D),
            new EgoClimateProfile(HOT_HUMID, 0.70D, 0.02D, 0.90D, 0.15D, 0.60D, 0.65D),
            new EgoClimateProfile(COLD_DRY, 0.02D, 0.75D, 0.20D, 0.50D, 0.20D, 0.70D),
            new EgoClimateProfile(COLD_WET, 0.02D, 0.65D, 0.80D, 0.45D, 0.70D, 0.75D));

    private static final Map<String, EgoClimateProfile> DEFAULTS_BY_ID = index(DEFAULTS);

    private EgoClimateProfileCatalog() {
    }

    public static List<EgoClimateProfile> defaults() {
        return DEFAULTS;
    }

    public static Optional<EgoClimateProfile> defaultById(String id) {
        return Optional.ofNullable(DEFAULTS_BY_ID.get(id));
    }

    private static Map<String, EgoClimateProfile> index(List<EgoClimateProfile> profiles) {
        Map<String, EgoClimateProfile> byId = new LinkedHashMap<>();
        for (EgoClimateProfile profile : profiles) {
            EgoClimateProfile previous = byId.put(profile.id(), profile);
            if (previous != null) {
                throw new IllegalArgumentException("duplicate climate profile id: " + profile.id());
            }
        }
        return Map.copyOf(byId);
    }
}
