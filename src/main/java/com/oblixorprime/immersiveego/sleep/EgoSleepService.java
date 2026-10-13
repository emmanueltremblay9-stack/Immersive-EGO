package com.oblixorprime.immersiveego.sleep;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public final class EgoSleepService {
    private EgoSleepService() {
    }

    public static EgoState applySleep(EgoState state, EgoSleepContext context) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(context, "context");
        EgoSleepDelta delta = EgoSleepCalculator.calculate(new EgoSleepInput(
                context,
                state.fatigue(),
                state.thermalStrain(),
                state.stress(),
                state.comfort()));
        return state.withDisplayValue(
                EgoAttributeCatalog.FATIGUE,
                EgoMath.clamp01(state.fatigue() + delta.netFatigueChange()))
                .withDisplayValue(
                        EgoAttributeCatalog.RECOVERY_CAPACITY,
                        EgoMath.clamp01(state.recoveryCapacity() + delta.recoveryCapacityGain()))
                .withDisplayValue(
                        EgoAttributeCatalog.FOCUS,
                        EgoMath.clamp01(state.focus() + delta.focusRecovery()))
                .withDisplayValue(
                        EgoAttributeCatalog.COMFORT,
                        EgoMath.clamp01(state.comfort() + delta.comfortRecovery()));
    }
}
