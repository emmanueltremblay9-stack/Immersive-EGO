package com.oblixorprime.immersiveego.simulation;

import java.util.Arrays;
import java.util.List;

public record EgoCurve(List<EgoCurvePoint> points) {
    public EgoCurve {
        points = List.copyOf(points);
        validate(points);
    }

    public static EgoCurve identity() {
        return new EgoCurve(List.of(
                new EgoCurvePoint(0.0D, 0.0D),
                new EgoCurvePoint(1.0D, 1.0D)));
    }

    public static EgoCurve of(EgoCurvePoint... points) {
        return new EgoCurve(Arrays.asList(points));
    }

    public double evaluate(double input) {
        double normalizedInput = EgoMath.clamp01(input);
        EgoCurvePoint previous = points.getFirst();
        if (normalizedInput <= previous.input()) {
            return previous.output();
        }

        for (int index = 1; index < points.size(); index++) {
            EgoCurvePoint next = points.get(index);
            if (normalizedInput <= next.input()) {
                return interpolate(previous, next, normalizedInput);
            }
            previous = next;
        }

        return points.getLast().output();
    }

    private static double interpolate(EgoCurvePoint previous, EgoCurvePoint next, double input) {
        double span = next.input() - previous.input();
        double progress = (input - previous.input()) / span;
        return EgoMath.clamp01(previous.output() + ((next.output() - previous.output()) * progress));
    }

    private static void validate(List<EgoCurvePoint> points) {
        if (points.size() < 2) {
            throw new IllegalArgumentException("curve must contain at least two points");
        }
        if (points.getFirst().input() != 0.0D) {
            throw new IllegalArgumentException("curve must start at input 0.0");
        }
        if (points.getLast().input() != 1.0D) {
            throw new IllegalArgumentException("curve must end at input 1.0");
        }

        double previousInput = points.getFirst().input();
        for (int index = 1; index < points.size(); index++) {
            double currentInput = points.get(index).input();
            if (currentInput <= previousInput) {
                throw new IllegalArgumentException("curve inputs must be strictly increasing");
            }
            previousInput = currentInput;
        }
    }
}
