package com.oblixorprime.immersiveego.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class EgoClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOW_CONTEXT_WARNINGS = BUILDER
            .translation("config.immersive_ego.client.show_context_warnings")
            .comment("Show lightweight contextual warnings when the server sends EGO state changes.")
            .define("showContextWarnings", true);

    public static final ModConfigSpec.BooleanValue ENABLE_DEBUG_OVERLAY = BUILDER
            .translation("config.immersive_ego.client.enable_debug_overlay")
            .comment("Enable the client debug overlay when authorized debug data is received from the server.")
            .define("enableDebugOverlay", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private EgoClientConfig() {
    }
}
