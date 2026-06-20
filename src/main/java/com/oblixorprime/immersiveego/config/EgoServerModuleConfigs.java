package com.oblixorprime.immersiveego.config;

import java.util.List;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class EgoServerModuleConfigs {
    private static final List<ModuleConfig> ALL = EgoServerModuleConfigCatalog.all().stream()
            .map(EgoServerModuleConfigs::build)
            .toList();

    private EgoServerModuleConfigs() {
    }

    public static List<ModuleConfig> all() {
        return ALL;
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
