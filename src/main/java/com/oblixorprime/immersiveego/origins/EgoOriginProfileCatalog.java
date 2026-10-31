package com.oblixorprime.immersiveego.origins;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EgoOriginProfileCatalog {
    public static final String TEMPERATE = "temperate";
    public static final String ARID = "arid";
    public static final String COLD = "cold";
    public static final String HIGHLAND = "highland";
    public static final String HUMID = "humid";

    private static final List<EgoOriginProfile> PROFILES = List.of(
            new EgoOriginProfile(TEMPERATE, 0.35D, 0.35D, 0.20D, 0.35D),
            new EgoOriginProfile(ARID, 0.75D, 0.15D, 0.20D, 0.10D),
            new EgoOriginProfile(COLD, 0.10D, 0.75D, 0.25D, 0.25D),
            new EgoOriginProfile(HIGHLAND, 0.30D, 0.45D, 0.80D, 0.20D),
            new EgoOriginProfile(HUMID, 0.55D, 0.15D, 0.15D, 0.80D));

    private static final Map<String, EgoOriginProfile> BY_ID = PROFILES.stream()
            .collect(Collectors.toUnmodifiableMap(EgoOriginProfile::id, Function.identity()));

    private EgoOriginProfileCatalog() {
    }

    public static List<EgoOriginProfile> all() {
        return PROFILES;
    }

    public static Optional<EgoOriginProfile> defaultById(String id) {
        return Optional.ofNullable(BY_ID.get(id));
    }

    public static EgoOriginProfile defaultProfile() {
        return BY_ID.get(TEMPERATE);
    }

    public static EgoOriginProfile forBiomePath(String biomePath) {
        String path = biomePath == null ? "" : biomePath.toLowerCase(Locale.ROOT);
        if (path.contains("desert") || path.contains("badlands") || path.contains("savanna")) {
            return BY_ID.get(ARID);
        }
        if (path.contains("snow") || path.contains("frozen") || path.contains("ice") || path.contains("taiga")) {
            return BY_ID.get(COLD);
        }
        if (path.contains("mountain") || path.contains("peak") || path.contains("slope") || path.contains("windswept")) {
            return BY_ID.get(HIGHLAND);
        }
        if (path.contains("jungle") || path.contains("swamp") || path.contains("mangrove")) {
            return BY_ID.get(HUMID);
        }
        return defaultProfile();
    }
}
