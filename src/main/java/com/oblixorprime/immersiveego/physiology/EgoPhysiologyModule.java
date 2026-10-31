package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;

public final class EgoPhysiologyModule {
    public static final String PHYSIOLOGY_MODULE_KEY = "physiology";

    private EgoPhysiologyModule() {
    }

    public static boolean enabled() {
        return EgoCoreConfig.ENABLE_SIMULATION.get()
                && EgoServerModuleConfigs.isEnabled(PHYSIOLOGY_MODULE_KEY);
    }
}
