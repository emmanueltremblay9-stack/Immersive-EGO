package com.oblixorprime.immersiveego.sleep;

public enum EgoCircadianPhase {
    DAWN(0.45D, 0.70D),
    DAY(0.15D, 0.45D),
    DUSK(0.55D, 0.65D),
    NIGHT(1.0D, 1.0D);

    public static final long MINECRAFT_DAY_TICKS = 24000L;

    private final double sleepPressure;
    private final double restQualityMultiplier;

    EgoCircadianPhase(double sleepPressure, double restQualityMultiplier) {
        this.sleepPressure = sleepPressure;
        this.restQualityMultiplier = restQualityMultiplier;
    }

    public static EgoCircadianPhase fromDayTime(long dayTime) {
        long dayTick = Math.floorMod(dayTime, MINECRAFT_DAY_TICKS);
        if (dayTick >= 23000L || dayTick < 1000L) {
            return DAWN;
        }
        if (dayTick < 12000L) {
            return DAY;
        }
        if (dayTick < 13000L) {
            return DUSK;
        }
        return NIGHT;
    }

    public double sleepPressure() {
        return sleepPressure;
    }

    public double restQualityMultiplier() {
        return restQualityMultiplier;
    }

    public boolean napWindow() {
        return this != NIGHT;
    }
}
