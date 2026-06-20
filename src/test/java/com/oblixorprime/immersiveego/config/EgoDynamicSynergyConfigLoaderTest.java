package com.oblixorprime.immersiveego.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

final class EgoDynamicSynergyConfigLoaderTest {
    @TempDir
    private Path tempDir;

    @Test
    void createsDefaultEmptySynergyFile() throws IOException {
        EgoDynamicSynergyConfig config = EgoDynamicSynergyConfigLoader.load(tempDir);

        assertTrue(config.edges().isEmpty());
        assertTrue(Files.exists(configPath()));
        assertTrue(EgoDynamicSynergyConfigLoader.load(tempDir).edges().isEmpty());
    }

    @Test
    void loadsValidConfiguredEdges() throws IOException {
        Files.createDirectories(configPath().getParent());
        Files.writeString(configPath(), """
                [synergies]
                edges = [
                    "fatigue -> stress : 0.25",
                    "stress -> ego_load : 0.4"
                ]
                """);

        EgoDynamicSynergyConfig config = EgoDynamicSynergyConfigLoader.load(tempDir);

        assertEquals(2, config.edges().size());
        assertEquals("fatigue", config.edges().getFirst().sourcePath());
        assertEquals("ego_load", config.edges().get(1).targetPath());
    }

    @Test
    void repairsInvalidConfiguredEdgesToEmpty() throws IOException {
        Files.createDirectories(configPath().getParent());
        Files.writeString(configPath(), """
                [synergies]
                edges = [
                    "fatigue -> missing : 0.25"
                ]
                """);

        EgoDynamicSynergyConfig config = EgoDynamicSynergyConfigLoader.load(tempDir);

        assertTrue(config.edges().isEmpty());
        assertTrue(EgoDynamicSynergyConfigLoader.load(tempDir).edges().isEmpty());
    }

    @Test
    void repairsCyclicConfiguredEdgesToEmpty() throws IOException {
        Files.createDirectories(configPath().getParent());
        Files.writeString(configPath(), """
                [synergies]
                edges = [
                    "fatigue -> stress : 0.25",
                    "stress -> fatigue : 0.25"
                ]
                """);

        EgoDynamicSynergyConfig config = EgoDynamicSynergyConfigLoader.load(tempDir);

        assertTrue(config.edges().isEmpty());
    }

    private Path configPath() {
        return tempDir.resolve("immersive_ego").resolve(EgoDynamicSynergyConfigLoader.FILE_NAME);
    }
}
