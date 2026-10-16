package com.oblixorprime.immersiveego.origins;

public final class EgoAcclimationCalculator {
    private EgoAcclimationCalculator() {
    }

    public static EgoOriginState advance(EgoOriginState state, EgoAcclimationContext context) {
        EgoOriginProfile profile = context.profile();
        double step = context.step();
        return state.withAcclimation(
                moveToward(state.heatAcclimation(), target(context.heatExposure(), profile.heatAffinity()), step),
                moveToward(state.coldAcclimation(), target(context.coldExposure(), profile.coldAffinity()), step),
                moveToward(state.altitudeAcclimation(), target(context.altitudeExposure(), profile.altitudeAffinity()), step),
                moveToward(state.humidityAcclimation(), target(context.humidityExposure(), profile.humidityAffinity()), step));
    }

    private static double target(double exposure, double originAffinity) {
        return Math.clamp(exposure * 0.70D + originAffinity * 0.30D, 0.0D, 1.0D);
    }

    private static double moveToward(double current, double target, double step) {
        return current + (target - current) * step;
    }
}
