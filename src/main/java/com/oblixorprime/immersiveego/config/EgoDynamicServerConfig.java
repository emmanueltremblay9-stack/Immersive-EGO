package com.oblixorprime.immersiveego.config;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoDynamicServerConfig(
        boolean schedulerEnabled,
        int schedulerIntervalTicks,
        double mirrorUpdateEpsilon) {
    public static final boolean DEFAULT_SCHEDULER_ENABLED = false;
    public static final int DEFAULT_SCHEDULER_INTERVAL_TICKS = 20;
    public static final int MIN_SCHEDULER_INTERVAL_TICKS = 1;
    public static final int MAX_SCHEDULER_INTERVAL_TICKS = 1200;
    public static final double DEFAULT_MIRROR_UPDATE_EPSILON = 0.005D;

    public EgoDynamicServerConfig {
        schedulerIntervalTicks = clampTicks(schedulerIntervalTicks);
        mirrorUpdateEpsilon = EgoMath.clamp01(mirrorUpdateEpsilon);
    }

    public static EgoDynamicServerConfig defaults() {
        return new EgoDynamicServerConfig(
                DEFAULT_SCHEDULER_ENABLED,
                DEFAULT_SCHEDULER_INTERVAL_TICKS,
                DEFAULT_MIRROR_UPDATE_EPSILON);
    }

    public static int clampTicks(int ticks) {
        return Math.clamp(ticks, MIN_SCHEDULER_INTERVAL_TICKS, MAX_SCHEDULER_INTERVAL_TICKS);
    }
}
