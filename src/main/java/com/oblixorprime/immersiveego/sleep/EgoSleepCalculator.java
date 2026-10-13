package com.oblixorprime.immersiveego.sleep;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoSleepCalculator {
    private static final double BASE_PRESSURE_PER_SECOND = 0.002D;
    private static final double FATIGUE_RECOVERY_PER_SECOND = 0.018D;
    private static final double RECOVERY_CAPACITY_PER_SECOND = 0.012D;
    private static final double FOCUS_RECOVERY_PER_SECOND = 0.008D;
    private static final double COMFORT_RECOVERY_PER_SECOND = 0.006D;

    private EgoSleepCalculator() {
    }

    public static EgoSleepDelta calculate(EgoSleepInput input) {
        Objects.requireNonNull(input, "input");
        EgoSleepContext context = input.context();
        EgoCircadianPhase phase = EgoCircadianPhase.fromDayTime(context.dayTime());
        if (context.sleeping()) {
            return sleepingDelta(input, phase);
        }
        double pressureGain = phase.sleepPressure()
                * BASE_PRESSURE_PER_SECOND
                * context.seconds()
                * awakeStrainMultiplier(input);
        return new EgoSleepDelta(
                phase,
                false,
                false,
                false,
                0.0D,
                pressureGain,
                0.0D,
                0.0D,
                0.0D,
                0.0D);
    }

    private static EgoSleepDelta sleepingDelta(EgoSleepInput input, EgoCircadianPhase phase) {
        EgoSleepContext context = input.context();
        double quality = sleepQuality(input, phase);
        double effectiveSeconds = context.seconds()
                * context.recoveryMultiplier()
                * quality;
        return new EgoSleepDelta(
                phase,
                true,
                phase.napWindow(),
                context.phantomRestQualified(),
                quality,
                0.0D,
                FATIGUE_RECOVERY_PER_SECOND * effectiveSeconds,
                RECOVERY_CAPACITY_PER_SECOND * effectiveSeconds,
                FOCUS_RECOVERY_PER_SECOND * effectiveSeconds,
                COMFORT_RECOVERY_PER_SECOND * effectiveSeconds);
    }

    private static double sleepQuality(EgoSleepInput input, EgoCircadianPhase phase) {
        EgoSleepContext context = input.context();
        double durationFactor = Math.clamp(0.50D + (context.sleepTicks() / 200.0D), 0.50D, 1.0D);
        double comfortFactor = 0.65D + input.comfort() * 0.35D;
        double stressFactor = 1.0D - input.stress() * 0.35D;
        double thermalFactor = 1.0D - input.thermalStrain() * 0.30D;
        return EgoMath.clamp01(phase.restQualityMultiplier()
                * durationFactor
                * comfortFactor
                * stressFactor
                * thermalFactor);
    }

    private static double awakeStrainMultiplier(EgoSleepInput input) {
        return 1.0D
                + input.stress() * 0.35D
                + input.thermalStrain() * 0.25D
                + input.fatigue() * 0.15D;
    }
}
