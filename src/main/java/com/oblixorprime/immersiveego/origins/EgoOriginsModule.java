package com.oblixorprime.immersiveego.origins;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;

public final class EgoOriginsModule {
    public static final String ORIGINS_MODULE_KEY = "origins";

    private EgoOriginsModule() {
    }

    public static boolean enabled() {
        return EgoCoreConfig.ENABLE_SIMULATION.get()
                && EgoServerModuleConfigs.isEnabled(ORIGINS_MODULE_KEY);
    }
}
