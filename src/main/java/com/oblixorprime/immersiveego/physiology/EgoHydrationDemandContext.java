package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;

public record EgoHydrationDemandContext(int intervalTicks, double hydrationEfficiency) {
    public EgoHydrationDemandContext {
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
        if (!Double.isFinite(hydrationEfficiency) || hydrationEfficiency <= 0.0D) {
            throw new IllegalArgumentException("hydrationEfficiency must be finite and positive");
        }
    }
}
