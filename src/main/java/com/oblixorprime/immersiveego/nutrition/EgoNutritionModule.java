package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;

public final class EgoNutritionModule {
    public static final String NUTRITION_MODULE_KEY = "nutrition";

    private EgoNutritionModule() {
    }

    public static boolean enabled() {
        return EgoCoreConfig.ENABLE_SIMULATION.get()
                && EgoServerModuleConfigs.isEnabled(NUTRITION_MODULE_KEY);
    }
}
