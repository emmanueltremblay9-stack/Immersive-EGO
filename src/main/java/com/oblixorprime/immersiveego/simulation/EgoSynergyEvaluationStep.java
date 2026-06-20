package com.oblixorprime.immersiveego.simulation;

public record EgoSynergyEvaluationStep(
        String sourcePath,
        String targetPath,
        double sourceValue,
        double weight,
        double delta,
        double beforeValue,
        double afterValue) {
}
