package com.oblixorprime.immersiveego.simulation;

public record EgoCurvePoint(double input, double output) {
    public EgoCurvePoint {
        input = requireNormalized("input", input);
        output = requireNormalized("output", output);
    }

    private static double requireNormalized(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D || value > 1.0D) {
            throw new IllegalArgumentException(name + " must be finite and normalized to 0..1");
        }
        return value;
    }
}
