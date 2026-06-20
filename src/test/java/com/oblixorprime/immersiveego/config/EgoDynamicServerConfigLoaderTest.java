package com.oblixorprime.immersiveego.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

final class EgoDynamicServerConfigLoaderTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void createsDefaultSimulationConfigWhenMissing() throws IOException {
        EgoDynamicServerConfig config = EgoDynamicServerConfigLoader.load(temporaryDirectory);

        assertFalse(config.schedulerEnabled());
        assertEquals(EgoDynamicServerConfig.DEFAULT_SCHEDULER_INTERVAL_TICKS, config.schedulerIntervalTicks());
        assertEquals(EgoDynamicServerConfig.DEFAULT_MIRROR_UPDATE_EPSILON, config.mirrorUpdateEpsilon());

        Path generatedConfig = temporaryDirectory
                .resolve("immersive_ego")
                .resolve(EgoDynamicServerConfigLoader.FILE_NAME);
        assertTrue(Files.exists(generatedConfig));

        EgoDynamicServerConfig persisted = EgoDynamicServerConfigLoader.load(temporaryDirectory);
        assertEquals(config, persisted);
    }

    @Test
    void clampsOutOfRangeValuesAndPersistsRepairedConfig() throws IOException {
        Path generatedConfig = temporaryDirectory
                .resolve("immersive_ego")
                .resolve(EgoDynamicServerConfigLoader.FILE_NAME);
        Files.createDirectories(generatedConfig.getParent());
        Files.writeString(generatedConfig, """
                [scheduler]
                enabled = true
                intervalTicks = -200

                [display]
                mirrorUpdateEpsilon = 8.0
                """);

        EgoDynamicServerConfig config = EgoDynamicServerConfigLoader.load(temporaryDirectory);

        assertTrue(config.schedulerEnabled());
        assertEquals(EgoDynamicServerConfig.MIN_SCHEDULER_INTERVAL_TICKS, config.schedulerIntervalTicks());
        assertEquals(1.0D, config.mirrorUpdateEpsilon());

        EgoDynamicServerConfig repaired = EgoDynamicServerConfigLoader.load(temporaryDirectory);
        assertEquals(config, repaired);
    }
}
