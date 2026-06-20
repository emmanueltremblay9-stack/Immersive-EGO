package com.oblixorprime.immersiveego;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

final class EgoProjectBoundaryTest {
    private static final Path PROJECT_ROOT = Path.of("").toAbsolutePath().normalize();

    private static final List<Path> FORBIDDEN_RESOURCE_DIRECTORIES = List.of(
            Path.of("src", "main", "resources", "assets", "immersive_ego", "blockstates"),
            Path.of("src", "main", "resources", "assets", "immersive_ego", "models", "block"),
            Path.of("src", "main", "resources", "assets", "immersive_ego", "models", "item"),
            Path.of("src", "main", "resources", "assets", "immersive_ego", "textures", "block"),
            Path.of("src", "main", "resources", "assets", "immersive_ego", "textures", "item"),
            Path.of("src", "main", "resources", "assets", "immersive_ego", "textures", "entity"),
            Path.of("src", "main", "resources", "assets", "immersive_ego", "geo"),
            Path.of("src", "main", "resources", "assets", "immersive_ego", "animations"),
            Path.of("src", "main", "resources", "data", "immersive_ego", "recipes"),
            Path.of("src", "main", "resources", "data", "immersive_ego", "loot_tables"));

    private static final List<String> FORBIDDEN_SOURCE_SNIPPETS = List.of(
            "DeferredRegister.create(Registries.BLOCK",
            "DeferredRegister.create(Registries.ITEM",
            "DeferredRegister.create(Registries.ENTITY_TYPE",
            "DeferredRegister.create(Registries.FLUID",
            "DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE",
            "DeferredRegister.create(Registries.MENU",
            "DeferredRegister.createBlocks(",
            "DeferredRegister.createItems(",
            "BlockEntityType.Builder",
            "EntityType.Builder",
            "MenuType<",
            "new Block(",
            "new Item(");

    private static final List<String> BANNED_INTEGRATION_TOKENS = List.of(
            "cold_sweat",
            "coldsweat",
            "Cold Sweat",
            "toughasnails",
            "tough_as_nails",
            "Tough As Nails");

    @Test
    void forbiddenCustomResourceDirectoriesDoNotExist() {
        for (Path directory : FORBIDDEN_RESOURCE_DIRECTORIES) {
            assertFalse(Files.exists(PROJECT_ROOT.resolve(directory)), directory.toString());
        }
    }

    @Test
    void mainSourcesDoNotRegisterForbiddenGameplayObjects() throws IOException {
        List<String> violations = new ArrayList<>();

        for (Path file : filesUnder("src/main/java", path -> path.toString().endsWith(".java"))) {
            String text = readString(file);
            for (String snippet : FORBIDDEN_SOURCE_SNIPPETS) {
                if (text.contains(snippet)) {
                    violations.add(PROJECT_ROOT.relativize(file) + " contains " + snippet);
                }
            }
        }

        assertTrue(violations.isEmpty(), () -> "Forbidden registration snippets found:\n" + String.join("\n", violations));
    }

    @Test
    void mainSourcesAndResourcesDoNotReferenceBannedIntegrations() throws IOException {
        List<String> violations = new ArrayList<>();

        for (Path file : filesUnder("src/main", EgoProjectBoundaryTest::isTextFile)) {
            String text = readString(file);
            for (String token : BANNED_INTEGRATION_TOKENS) {
                if (text.contains(token)) {
                    violations.add(PROJECT_ROOT.relativize(file) + " contains " + token);
                }
            }
        }

        assertTrue(violations.isEmpty(), () -> "Banned integration references found:\n" + String.join("\n", violations));
    }

    private static List<Path> filesUnder(String relativeRoot, Predicate<Path> predicate) throws IOException {
        Path root = PROJECT_ROOT.resolve(relativeRoot);
        try (Stream<Path> stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(predicate)
                    .toList();
        }
    }

    private static boolean isTextFile(Path path) {
        String fileName = path.getFileName().toString();
        return fileName.endsWith(".java")
                || fileName.endsWith(".json")
                || fileName.endsWith(".toml")
                || fileName.endsWith(".mcmeta")
                || fileName.endsWith(".properties");
    }

    private static String readString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
