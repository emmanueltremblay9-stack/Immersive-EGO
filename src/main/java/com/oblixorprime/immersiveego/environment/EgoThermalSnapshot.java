package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoThermalSnapshot(
        EgoThermalMode mode,
        double heatPressure,
        double coldPressure,
        double wetnessPressure,
        double exposurePressure,
        double thermalStrain,
        double rawHeatPressure,
        double rawColdPressure,
        double thermalTolerance) {
    public EgoThermalSnapshot {
        Objects.requireNonNull(mode, "mode");
        if (!Double.isFinite(thermalTolerance) || thermalTolerance < 0.25D || thermalTolerance > 4.0D) {
            throw new IllegalArgumentException("Thermal tolerance must be finite and within 0.25..4");
        }
        heatPressure = EgoMath.clamp01(heatPressure);
        coldPressure = EgoMath.clamp01(coldPressure);
        wetnessPressure = EgoMath.clamp01(wetnessPressure);
        exposurePressure = EgoMath.clamp01(exposurePressure);
        thermalStrain = EgoMath.clamp01(thermalStrain);
        rawHeatPressure = EgoMath.clamp01(rawHeatPressure);
        rawColdPressure = EgoMath.clamp01(rawColdPressure);
    }

    /** Compatibility constructor for snapshots with neutral tolerance. */
    public EgoThermalSnapshot(EgoThermalMode mode, double heatPressure, double coldPressure,
            double wetnessPressure, double exposurePressure, double thermalStrain) {
        this(mode, heatPressure, coldPressure, wetnessPressure, exposurePressure, thermalStrain,
                heatPressure, coldPressure, 1.0D);
    }
}
