package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoHydrationService {
    private EgoHydrationService() {
    }

    public static EgoState applyDemand(EgoState state, EgoHydrationDemandContext context) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(context, "context");
        EgoHydrationDemand demand = EgoHydrationDemandCalculator.calculate(new EgoHydrationDemandInput(
                state.thermalStrain(),
                state.respiratoryStrain(),
                state.fatigue(),
                context.intervalTicks(),
                context.hydrationEfficiency()));
        return state.withDisplayValue(
                EgoAttributeCatalog.HYDRATION,
                state.hydration() - demand.total());
    }

    public static EgoState applyDrink(EgoState state, EgoDrinkProfile profile, double hydrationEfficiency) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(profile, "profile");
        if (!Double.isFinite(hydrationEfficiency) || hydrationEfficiency <= 0.0D) {
            throw new IllegalArgumentException("hydrationEfficiency must be finite and positive");
        }
        double effectiveGain = profile.hydrationGain()
                * profile.waterQuality().hydrationMultiplier()
                * Math.clamp(hydrationEfficiency, 0.25D, 4.0D);
        double stressPenalty = profile.waterQuality().stressPenalty(profile.contaminationRisk());
        return state.withDisplayValue(
                EgoAttributeCatalog.HYDRATION,
                EgoMath.clamp01(state.hydration() + effectiveGain))
                .withDisplayValue(
                        EgoAttributeCatalog.STRESS,
                        EgoMath.clamp01(state.stress() + stressPenalty));
    }
}
