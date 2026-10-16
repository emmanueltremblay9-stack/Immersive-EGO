package com.oblixorprime.immersiveego.physiology;

public enum EgoEncumbranceTier {
    NORMAL(0.0D),
    LOADED(0.25D),
    STRAINED(0.60D),
    OVERBURDENED(1.0D);

    private final double pressure;

    EgoEncumbranceTier(double pressure) {
        this.pressure = pressure;
    }

    public double pressure() {
        return pressure;
    }
}
