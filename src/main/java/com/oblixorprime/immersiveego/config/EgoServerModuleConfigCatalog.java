package com.oblixorprime.immersiveego.config;

import java.util.List;

public final class EgoServerModuleConfigCatalog {
    private static final List<Entry> ALL = List.of(
            entry("environment", "Controls future climate, exposure, shelter, wetness, and thermal snapshot simulation."),
            entry("physiology", "Controls future hydration, respiration, stamina, fatigue, pain, and recovery simulation."),
            entry("sleep", "Controls future circadian phase, sleep pressure, sleep quality, and nap simulation."),
            entry("nutrition", "Controls future dietary memory, meal quality, gut health, and MariesLib nutrition integration."),
            entry("psychology", "Controls future stress, fear, stability, focus, and comfort simulation."),
            entry("origins", "Controls future biome origin, birth-biome assignment, and acclimation simulation."),
            entry("encumbrance", "Controls future inventory, equipment weight, and load tolerance simulation."),
            entry("synergies", "Controls future server-level synergy graph gates and presets."),
            entry("apothic", "Controls future Apothic Attributes presentation and compatibility settings."),
            entry("performance", "Controls future simulation budget, cache, and diagnostic performance settings."));

    private EgoServerModuleConfigCatalog() {
    }

    public static List<Entry> all() {
        return ALL;
    }

    private static Entry entry(String key, String description) {
        return new Entry(
                key,
                "immersive_ego-" + key + ".toml",
                description,
                false,
                false);
    }

    public record Entry(
            String key,
            String fileName,
            String description,
            boolean defaultEnabled,
            boolean defaultDebugLogging) {
    }
}
