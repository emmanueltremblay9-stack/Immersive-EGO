package com.oblixorprime.immersiveego.wetness;

/** Per-entity logical ticks only: gaps, world-time changes and repeated callbacks never add duration. */
public final class EgoWetnessTickClock {
    private int lastTick = Integer.MIN_VALUE;
    private Object owner;
    private long consumedTicks;

    public double consume(Object entity, int tick, boolean active) {
        if (owner == entity && lastTick == tick) {
            return 0.0D;
        }
        owner = entity;
        lastTick = tick;
        if (!active) {
            return 0.0D;
        }
        consumedTicks++;
        return 1.0D / 20.0D;
    }

    public long consumedTicks() {
        return consumedTicks;
    }
}
