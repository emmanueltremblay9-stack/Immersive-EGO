package com.oblixorprime.immersiveego.config;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class EgoServerModuleConfigs {
    private static final List<ModuleConfig> ALL = EgoServerModuleConfigCatalog.all().stream()
            .map(EgoServerModuleConfigs::build)
            .toList();
    private static final Map<String, ModuleConfig> BY_KEY = index(ALL);

    private EgoServerModuleConfigs() {
    }

    public static List<ModuleConfig> all() {
        return ALL;
    }

    public static Optional<ModuleConfig> byKey(String key) {
        return Optional.ofNullable(BY_KEY.get(key));
    }

    public static boolean isEnabled(String key) {
        return byKey(key)
                .map(config -> config.enabled().get())
                .orElse(false);
    }

    public static void registerAll(ModContainer modContainer) {
        for (ModuleConfig config : ALL) {
            modContainer.registerConfig(ModConfig.Type.SERVER, config.spec(), config.fileName());
        }
    }

    private static ModuleConfig build(EgoServerModuleConfigCatalog.Entry entry) {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        ModConfigSpec.BooleanValue enabled = builder
                .translation("config.immersive_ego." + entry.key() + ".enabled")
                .comment(entry.description() + " Default false until this slice is implemented.")
                .define("enabled", entry.defaultEnabled());
        ModConfigSpec.BooleanValue debugLogging = builder
                .translation("config.immersive_ego." + entry.key() + ".debug_logging")
                .comment("Emit diagnostic logs for the " + entry.key() + " slice once implemented. Default false.")
                .define("debugLogging", entry.defaultDebugLogging());
        return new ModuleConfig(
                entry.key(),
                entry.fileName(),
                builder.build(),
                enabled,
                debugLogging,
                entry.defaultEnabled(),
                entry.defaultDebugLogging());
    }

    private static Map<String, ModuleConfig> index(List<ModuleConfig> configs) {
        Map<String, ModuleConfig> byKey = new LinkedHashMap<>();
        for (ModuleConfig config : configs) {
            ModuleConfig previous = byKey.put(config.key(), config);
            if (previous != null) {
                throw new IllegalArgumentException("duplicate module config key: " + config.key());
            }
        }
        return Map.copyOf(byKey);
    }

    public record ModuleConfig(
            String key,
            String fileName,
            ModConfigSpec spec,
            ModConfigSpec.BooleanValue enabled,
            ModConfigSpec.BooleanValue debugLogging,
            boolean defaultEnabled,
            boolean defaultDebugLogging) {
    }
}
