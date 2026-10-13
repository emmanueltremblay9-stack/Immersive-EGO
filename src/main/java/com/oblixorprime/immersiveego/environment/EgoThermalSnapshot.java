package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoThermalSnapshot(
        EgoThermalMode mode,
        double heatPressure,
        double coldPressure,
        double wetnessPressure,
        double exposurePressure,
        double thermalStrain) {
    public EgoThermalSnapshot {
        Objects.requireNonNull(mode, "mode");
        heatPressure = EgoMath.clamp01(heatPressure);
        coldPressure = EgoMath.clamp01(coldPressure);
        wetnessPressure = EgoMath.clamp01(wetnessPressure);
        exposurePressure = EgoMath.clamp01(exposurePressure);
        thermalStrain = EgoMath.clamp01(thermalStrain);
    }
}
