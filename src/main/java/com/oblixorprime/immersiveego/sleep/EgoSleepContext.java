package com.oblixorprime.immersiveego.sleep;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;

public record EgoSleepContext(
        long dayTime,
        boolean sleeping,
        int sleepTicks,
        int timeSinceRestTicks,
        int intervalTicks,
        double sleepRecovery) {
    public static final int PHANTOM_REST_QUALIFYING_TICKS = 100;
    public static final int PHANTOM_SPAWN_RESTLESS_TICKS = 72000;

    public EgoSleepContext(
            long dayTime,
            boolean sleeping,
            int sleepTicks,
            int intervalTicks,
            double sleepRecovery) {
        this(dayTime, sleeping, sleepTicks, 0, intervalTicks, sleepRecovery);
    }

    public EgoSleepContext {
        if (dayTime < 0L) {
            throw new IllegalArgumentException("dayTime must be non-negative");
        }
        if (sleepTicks < 0) {
            throw new IllegalArgumentException("sleepTicks must be non-negative");
        }
        if (timeSinceRestTicks < 0) {
            throw new IllegalArgumentException("timeSinceRestTicks must be non-negative");
        }
        if (!sleeping) {
            sleepTicks = 0;
        }
        intervalTicks = EgoDynamicServerConfig.clampTicks(intervalTicks);
        if (!Double.isFinite(sleepRecovery) || sleepRecovery <= 0.0D) {
            throw new IllegalArgumentException("sleepRecovery must be finite and positive");
        }
    }

    double seconds() {
        return intervalTicks / 20.0D;
    }

    double recoveryMultiplier() {
        return Math.clamp(sleepRecovery, 0.25D, 4.0D);
    }

    public boolean phantomRestQualified() {
        return sleeping && sleepTicks >= PHANTOM_REST_QUALIFYING_TICKS;
    }

    public boolean phantomSpawnEligible() {
        return timeSinceRestTicks >= PHANTOM_SPAWN_RESTLESS_TICKS;
    }
}
