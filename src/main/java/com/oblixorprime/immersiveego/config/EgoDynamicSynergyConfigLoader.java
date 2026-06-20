package com.oblixorprime.immersiveego.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEdge;
import com.oblixorprime.immersiveego.simulation.EgoSynergyEdgeParser;
import com.oblixorprime.immersiveego.simulation.EgoSynergyValidator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

public final class EgoDynamicSynergyConfigLoader {
    public static final String FILE_NAME = "synergies.toml";

    private static final String EDGES = "synergies.edges";

    private static EgoDynamicSynergyConfig current = EgoDynamicSynergyConfig.defaults();

    private EgoDynamicSynergyConfigLoader() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoDynamicSynergyConfigLoader::onServerStarting);
    }

    public static EgoDynamicSynergyConfig current() {
        return current;
    }

    public static EgoDynamicSynergyConfig load(Path serverConfigRoot) {
        Path configDirectory = serverConfigRoot.resolve(ImmersiveEgo.MOD_ID);
        Path configPath = configDirectory.resolve(FILE_NAME);

        try {
            Files.createDirectories(configDirectory);
            if (Files.notExists(configPath)) {
                Files.createFile(configPath);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to prepare Immersive EGO synergy config at " + configPath, exception);
        }

        try (CommentedFileConfig fileConfig = CommentedFileConfig.of(configPath)) {
            fileConfig.load();
            EgoDynamicSynergyConfig loaded = readAndRepair(fileConfig);
            fileConfig.save();
            return loaded;
        }
    }

    private static EgoDynamicSynergyConfig readAndRepair(CommentedConfig config) {
        config.setComment(EDGES, "Declarative synergy edges using 'source -> target : weight'. Invalid or cyclic lists are repaired to empty.");
        Object value = config.get(EDGES);
        if (!(value instanceof List<?> rawEdges)) {
            config.set(EDGES, List.of());
            return EgoDynamicSynergyConfig.defaults();
        }

        try {
            List<EgoSynergyEdge> edges = parseEdges(rawEdges);
            config.set(EDGES, edgeStrings(edges));
            return new EgoDynamicSynergyConfig(edges);
        } catch (IllegalArgumentException exception) {
            config.set(EDGES, List.of());
            return EgoDynamicSynergyConfig.defaults();
        }
    }

    private static List<EgoSynergyEdge> parseEdges(List<?> rawEdges) {
        List<EgoSynergyEdge> edges = new ArrayList<>();
        for (Object rawEdge : rawEdges) {
            if (!(rawEdge instanceof String edgeText)) {
                throw new IllegalArgumentException("synergy edges must be strings");
            }
            edges.add(EgoSynergyEdgeParser.parse(edgeText));
        }
        return EgoSynergyValidator.validateCatalogEdges(edges);
    }

    private static List<String> edgeStrings(List<EgoSynergyEdge> edges) {
        return edges.stream()
                .map(edge -> edge.sourcePath() + " -> " + edge.targetPath() + " : " + edge.weight())
                .toList();
    }

    private static void onServerStarting(ServerStartingEvent event) {
        Path serverConfigRoot = event.getServer()
                .getWorldPath(LevelResource.ROOT)
                .resolve("serverconfig");
        current = load(serverConfigRoot);
        ImmersiveEgo.LOGGER.info("Loaded Immersive EGO synergy config from {}", serverConfigRoot.resolve(ImmersiveEgo.MOD_ID).resolve(FILE_NAME));
    }
}
