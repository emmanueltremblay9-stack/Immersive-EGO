package com.oblixorprime.immersiveego.physiology;

public final class EgoHydrationDemandCalculator {
    private static final double BASELINE_PER_SECOND = 0.00025D;
    private static final double THERMAL_PER_SECOND = 0.00075D;
    private static final double RESPIRATORY_PER_SECOND = 0.00050D;
    private static final double FATIGUE_PER_SECOND = 0.00025D;

    private EgoHydrationDemandCalculator() {
    }

    public static EgoHydrationDemand calculate(EgoHydrationDemandInput input) {
        double seconds = input.seconds();
        double baseline = BASELINE_PER_SECOND * seconds;
        double thermal = input.thermalStrain() * THERMAL_PER_SECOND * seconds;
        double respiratory = input.respiratoryStrain() * RESPIRATORY_PER_SECOND * seconds;
        double fatigue = input.fatigue() * FATIGUE_PER_SECOND * seconds;
        double efficiencyMultiplier = input.efficiencyMultiplier();
        double total = (baseline + thermal + respiratory + fatigue) * efficiencyMultiplier;
        return new EgoHydrationDemand(baseline, thermal, respiratory, fatigue, efficiencyMultiplier, total);
    }
}
