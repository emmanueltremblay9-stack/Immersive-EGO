package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;

public final class EgoPsychologyModule {
    public static final String PSYCHOLOGY_MODULE_KEY = "psychology";

    private EgoPsychologyModule() {
    }

    public static boolean enabled() {
        return EgoCoreConfig.ENABLE_SIMULATION.get()
                && EgoServerModuleConfigs.isEnabled(PSYCHOLOGY_MODULE_KEY);
    }
}
