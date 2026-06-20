package com.oblixorprime.immersiveego.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class EgoServerModuleConfigsTest {
    private static final Set<String> REQUIRED_SERVER_CONFIG_FILES = Set.of(
            "immersive_ego-environment.toml",
            "immersive_ego-physiology.toml",
            "immersive_ego-sleep.toml",
            "immersive_ego-nutrition.toml",
            "immersive_ego-psychology.toml",
            "immersive_ego-origins.toml",
            "immersive_ego-encumbrance.toml",
            "immersive_ego-synergies.toml",
            "immersive_ego-apothic.toml",
            "immersive_ego-performance.toml");

    @Test
    void includesEveryRequiredServerModuleConfig() {
        Set<String> actualFiles = new HashSet<>();

        for (EgoServerModuleConfigCatalog.Entry config : EgoServerModuleConfigCatalog.all()) {
            actualFiles.add(config.fileName());
        }

        assertEquals(REQUIRED_SERVER_CONFIG_FILES, actualFiles);
    }

    @Test
    void moduleConfigMetadataIsUniqueAndStable() {
        Set<String> keys = new HashSet<>();
        Set<String> fileNames = new HashSet<>();

        for (EgoServerModuleConfigCatalog.Entry config : EgoServerModuleConfigCatalog.all()) {
            assertTrue(keys.add(config.key()), "duplicate config key: " + config.key());
            assertTrue(fileNames.add(config.fileName()), "duplicate config file: " + config.fileName());
            assertEquals("immersive_ego-" + config.key() + ".toml", config.fileName());
            assertFalse(config.description().isBlank(), config.key());
            assertFalse(config.defaultEnabled(), config.key());
            assertFalse(config.defaultDebugLogging(), config.key());
        }
    }
}
