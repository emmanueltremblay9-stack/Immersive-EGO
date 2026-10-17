package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public final class EgoThermalSnapshotCalculator {
    private static final double MODE_DEADBAND = 0.05D;

    private EgoThermalSnapshotCalculator() {
    }

    public static EgoThermalSnapshot calculate(EgoExposureSample sample) {
        return calculate(sample, sample.wetness());
    }

    /** Replace the one liquid branch only; climatic pressure is not stored body wetness. */
    public static EgoThermalSnapshot calculate(EgoExposureSample sample, double bodyWetness) {
        return calculate(sample, bodyWetness, 1.0D);
    }

    /** Apply tolerance once, after the existing normalized climate/W calculation. */
    public static EgoThermalSnapshot calculate(
            EgoExposureSample sample, double bodyWetness, double thermalTolerance) {
        if (!Double.isFinite(bodyWetness) || bodyWetness < 0.0D || bodyWetness > 1.0D) {
            throw new IllegalArgumentException("Thermal body wetness must be finite and normalized");
        }
        if (!Double.isFinite(thermalTolerance) || thermalTolerance <= 0.0D) {
            throw new IllegalArgumentException("Thermal tolerance must be finite and positive");
        }
        double tolerance = Math.clamp(thermalTolerance, 0.25D, 4.0D);
        EgoClimateProfile profile = sample.climateProfile();
        double exposure = sample.unshelteredExposure();
        double shelterRelief = sample.shelter() * profile.shelterDemand() * 0.45D;
        double exposureAmplifier = 0.5D + (exposure * 0.5D);
        double wetnessPressure = EgoMath.clamp01(Math.max(
                bodyWetness,
                profile.wetnessPressure() * exposure));

        double rawHeatPressure = EgoMath.clamp01(
                ((profile.heatLoad() + sample.seasonPhase().heatBias()) * exposureAmplifier)
                        + (profile.humidity() * profile.heatLoad() * exposure * 0.25D)
                        - shelterRelief);
        double rawColdPressure = EgoMath.clamp01(
                ((profile.coldLoad() + sample.seasonPhase().coldBias()) * exposureAmplifier)
                        + (profile.windExposure() * exposure * 0.25D)
                        + (wetnessPressure * exposure * 0.30D)
                        - shelterRelief);
        double heatPressure = EgoMath.clamp01(rawHeatPressure / tolerance);
        double coldPressure = EgoMath.clamp01(rawColdPressure / tolerance);
        double thermalStrain = Math.max(heatPressure, coldPressure);

        return new EgoThermalSnapshot(
                mode(heatPressure, coldPressure),
                heatPressure,
                coldPressure,
                wetnessPressure,
                exposure,
                thermalStrain,
                rawHeatPressure,
                rawColdPressure,
                tolerance);
    }

    private static EgoThermalMode mode(double heatPressure, double coldPressure) {
        double difference = heatPressure - coldPressure;
        if (Math.abs(difference) < MODE_DEADBAND) {
            return EgoThermalMode.NEUTRAL;
        }
        return difference > 0.0D ? EgoThermalMode.HEAT : EgoThermalMode.COLD;
    }
}
