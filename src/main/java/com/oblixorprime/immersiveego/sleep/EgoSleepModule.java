package com.oblixorprime.immersiveego.sleep;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;

public final class EgoSleepModule {
    public static final String SLEEP_MODULE_KEY = "sleep";

    private EgoSleepModule() {
    }

    public static boolean enabled() {
        return EgoCoreConfig.ENABLE_SIMULATION.get()
                && EgoServerModuleConfigs.isEnabled(SLEEP_MODULE_KEY);
    }
}
