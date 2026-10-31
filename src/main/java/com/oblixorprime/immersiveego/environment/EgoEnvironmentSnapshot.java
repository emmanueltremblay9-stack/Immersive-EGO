package com.oblixorprime.immersiveego.environment;

import java.util.Objects;

public record EgoEnvironmentSnapshot(
        long gameTime,
        EgoExposureSample exposureSample,
        EgoThermalSnapshot thermalSnapshot) {
    public EgoEnvironmentSnapshot {
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime must be non-negative");
        }
        Objects.requireNonNull(exposureSample, "exposureSample");
        Objects.requireNonNull(thermalSnapshot, "thermalSnapshot");
    }

    public static EgoEnvironmentSnapshot from(long gameTime, EgoExposureSample exposureSample) {
        return new EgoEnvironmentSnapshot(
                gameTime,
                exposureSample,
                EgoThermalSnapshotCalculator.calculate(exposureSample));
    }
}
