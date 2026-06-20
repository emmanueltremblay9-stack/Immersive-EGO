package com.oblixorprime.immersiveego.derived;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoCurve;
import com.oblixorprime.immersiveego.simulation.EgoMath;

public final class EgoDerivedStateCalculator {
    private static final double[] STRAIN_WEIGHTS = {1.0D, 1.0D, 1.0D, 1.0D};
    private static final double[] READINESS_WEIGHTS = {1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 2.0D};
    private static final EgoCurve EGO_LOAD_RESPONSE = EgoCurve.identity();
    private static final EgoCurve READINESS_RESPONSE = EgoCurve.identity();

    private EgoDerivedStateCalculator() {
    }

    public static EgoState calculate(EgoState state) {
        double egoLoad = calculateEgoLoad(state);
        double readiness = calculateReadiness(state, egoLoad);

        return state
                .withDisplayValue(EgoAttributeCatalog.EGO_LOAD, egoLoad)
                .withDisplayValue(EgoAttributeCatalog.READINESS, readiness);
    }

    private static double calculateEgoLoad(EgoState state) {
        double[] strains = {
                state.fatigue(),
                state.thermalStrain(),
                state.respiratoryStrain(),
                state.stress()
        };
        double weightedMean = EgoMath.weightedMean(strains, STRAIN_WEIGHTS);
        double maxUrgency = 0.0D;
        for (double strain : strains) {
            maxUrgency = Math.max(maxUrgency, EgoMath.clamp01(strain));
        }

        return EGO_LOAD_RESPONSE.evaluate(EgoLoadCalculator.calculate(weightedMean, maxUrgency, state.egoLoad()));
    }

    private static double calculateReadiness(EgoState state, double egoLoad) {
        double[] readinessInputs = {
                state.hydration(),
                state.stamina(),
                state.nutritionBalance(),
                state.focus(),
                state.comfort(),
                state.recoveryCapacity(),
                1.0D - egoLoad
        };

        return READINESS_RESPONSE.evaluate(EgoMath.weightedMean(readinessInputs, READINESS_WEIGHTS));
    }
}
