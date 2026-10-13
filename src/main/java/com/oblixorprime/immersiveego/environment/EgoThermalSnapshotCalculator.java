package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public final class EgoThermalSnapshotCalculator {
    private static final double MODE_DEADBAND = 0.05D;

    private EgoThermalSnapshotCalculator() {
    }

    public static EgoThermalSnapshot calculate(EgoExposureSample sample) {
        EgoClimateProfile profile = sample.climateProfile();
        double exposure = sample.unshelteredExposure();
        double shelterRelief = sample.shelter() * profile.shelterDemand() * 0.45D;
        double exposureAmplifier = 0.5D + (exposure * 0.5D);
        double wetnessPressure = EgoMath.clamp01(Math.max(
                sample.wetness(),
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
