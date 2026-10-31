package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;

public final class EgoEncumbranceModule {
    public static final String ENCUMBRANCE_MODULE_KEY = "encumbrance";

    private EgoEncumbranceModule() {
    }

    public static boolean enabled() {
        return EgoCoreConfig.ENABLE_SIMULATION.get()
                && EgoServerModuleConfigs.isEnabled(ENCUMBRANCE_MODULE_KEY);
    }
}
