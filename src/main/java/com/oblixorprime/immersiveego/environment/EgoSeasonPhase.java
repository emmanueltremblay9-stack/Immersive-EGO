package com.oblixorprime.immersiveego.environment;

public enum EgoSeasonPhase {
    SPRING(0.05D, 0.05D),
    SUMMER(0.25D, 0.0D),
    AUTUMN(0.0D, 0.10D),
    WINTER(0.0D, 0.25D);

    private final double heatBias;
    private final double coldBias;

    EgoSeasonPhase(double heatBias, double coldBias) {
        this.heatBias = heatBias;
        this.coldBias = coldBias;
    }

    public double heatBias() {
        return heatBias;
    }

    public double coldBias() {
        return coldBias;
    }
}
