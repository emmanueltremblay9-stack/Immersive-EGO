package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoMath;

public final class EgoPsychologyService {
    private EgoPsychologyService() {
    }

    public static EgoState applyPsychology(EgoState state, EgoPsychologyContext context) {
        EgoPsychologyDelta delta = EgoPsychologyCalculator.calculate(state, context);
        return state
                .withDisplayValue(EgoAttributeCatalog.STRESS, EgoMath.clamp01(state.stress() + delta.stressDelta()))
                .withDisplayValue(EgoAttributeCatalog.FOCUS, EgoMath.clamp01(state.focus() + delta.focusDelta()))
                .withDisplayValue(EgoAttributeCatalog.COMFORT, EgoMath.clamp01(state.comfort() + delta.comfortDelta()))
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, EgoMath.clamp01(state.recoveryCapacity() + delta.recoveryCapacityDelta()));
    }
}
