package com.oblixorprime.immersiveego.wetness;

/** State-only messages: no invented rain/immersion cause, and no "drying" claim while saturated. */
public final class EgoWetnessNoticeGate {
    private int band;
    private long lastNotice = Long.MIN_VALUE / 2;

    public String observe(double wetness, long consumedTicks) {
        int next = band;
        if (wetness <= 0.02D) next = 0;
        else if (wetness >= 0.65D) next = 2;
        else if (wetness >= 0.15D && (band != 2 || wetness <= 0.50D)) next = 1;
        if (next == band || consumedTicks - lastNotice < 200L) return null;
        band = next;
        lastNotice = consumedTicks;
        return switch (band) {
            case 2 -> "message.immersive_ego.wetness.soaked";
            case 1 -> "message.immersive_ego.wetness.damp";
            default -> "message.immersive_ego.wetness.dry";
        };
    }
}
