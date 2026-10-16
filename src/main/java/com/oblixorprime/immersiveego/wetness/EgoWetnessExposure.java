package com.oblixorprime.immersiveego.wetness;

public enum EgoWetnessExposure {
    DRY, RAIN, IMMERSION;

    public static EgoWetnessExposure observed(boolean bodyInWater, boolean receivedRain) {
        return bodyInWater ? IMMERSION : receivedRain ? RAIN : DRY;
    }
}
