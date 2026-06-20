package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;

public record EgoSimulationSchedule(boolean enabled, int intervalTicks) {
    public EgoSimulationSchedule {
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
    }

    public static EgoSimulationSchedule from(EgoDynamicServerConfig config) {
        return new EgoSimulationSchedule(config.schedulerEnabled(), config.schedulerIntervalTicks());
    }

    public boolean shouldRun(long gameTime) {
        return this.enabled && gameTime >= 0L && gameTime % this.intervalTicks == 0L;
    }
}
