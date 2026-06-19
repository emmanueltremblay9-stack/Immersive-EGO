package com.oblixorprime.immersiveego.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class EgoCoreConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_SIMULATION = BUILDER
            .translation("config.immersive_ego.core.enable_simulation")
            .comment("Master switch for server-authoritative Immersive EGO simulation. Keep false until the first vertical slice is implemented.")
            .define("enableSimulation", false);

    public static final ModConfigSpec.DoubleValue MIRROR_UPDATE_EPSILON = BUILDER
            .translation("config.immersive_ego.core.mirror_update_epsilon")
            .comment("Minimum normalized display-value delta before a mirrored Apothic attribute update is emitted.")
            .defineInRange("mirrorUpdateEpsilon", 0.005D, 0.0D, 1.0D);

    public static final ModConfigSpec.IntValue STATE_SCHEMA_VERSION = BUILDER
            .translation("config.immersive_ego.core.state_schema_version")
            .comment("Current persisted EgoState schema version. Migrations must update this intentionally.")
            .defineInRange("stateSchemaVersion", 1, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private EgoCoreConfig() {
    }
}
