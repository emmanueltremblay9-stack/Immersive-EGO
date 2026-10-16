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
        if (!Double.isFinite(bodyWetness) || bodyWetness < 0.0D || bodyWetness > 1.0D) {
            throw new IllegalArgumentException("Thermal body wetness must be finite and normalized");
        }
        EgoClimateProfile profile = sample.climateProfile();
        double exposure = sample.unshelteredExposure();
        double shelterRelief = sample.shelter() * profile.shelterDemand() * 0.45D;
        double exposureAmplifier = 0.5D + (exposure * 0.5D);
        double wetnessPressure = EgoMath.clamp01(Math.max(
                bodyWetness,
                profile.wetnessPressure() * exposure));

        double heatPressure = EgoMath.clamp01(
                ((profile.heatLoad() + sample.seasonPhase().heatBias()) * exposureAmplifier)
                        + (profile.humidity() * profile.heatLoad() * exposure * 0.25D)
                        - shelterRelief);
        double coldPressure = EgoMath.clamp01(
                ((profile.coldLoad() + sample.seasonPhase().coldBias()) * exposureAmplifier)
                        + (profile.windExposure() * exposure * 0.25D)
                        + (wetnessPressure * exposure * 0.30D)
                        - shelterRelief);
        double thermalStrain = Math.max(heatPressure, coldPressure);

        return new EgoThermalSnapshot(
                mode(heatPressure, coldPressure),
                heatPressure,
                coldPressure,
                wetnessPressure,
                exposure,
                thermalStrain);
    }

    private static EgoThermalMode mode(double heatPressure, double coldPressure) {
        double difference = heatPressure - coldPressure;
        if (Math.abs(difference) < MODE_DEADBAND) {
            return EgoThermalMode.NEUTRAL;
        }
        return difference > 0.0D ? EgoThermalMode.HEAT : EgoThermalMode.COLD;
    }
}
