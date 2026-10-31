package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.simulation.EgoMath;

public final class EgoPsychologyCalculator {
    private EgoPsychologyCalculator() {
    }

    public static EgoPsychologyDelta calculate(EgoState state, EgoPsychologyContext context) {
        double seconds = context.seconds();
        double vulnerability = context.vulnerabilityMultiplier();
        double pressure = EgoMath.clamp01(context.fearPressure() * 0.60D + context.painPressure() * 0.40D);
        EgoRecoveryBudget budget = EgoRecoveryBudget.allocate(state, seconds, context.resilienceMultiplier());

        double stressGain = pressure * seconds * 0.018D * vulnerability;
        double stabilityRelief = context.stabilitySupport() * seconds * 0.010D * context.resilienceMultiplier();
        double focusLoss = (pressure * 0.014D + state.stress() * 0.006D) * seconds * vulnerability;
        double focusSupport = context.stabilitySupport() * seconds * 0.005D * context.resilienceMultiplier();
        double comfortLoss = (context.painPressure() * 0.016D + context.fearPressure() * 0.006D) * seconds * vulnerability;
        double comfortGain = context.comfortSupport() * seconds * 0.008D * context.resilienceMultiplier();
        double recoveryDrain = pressure * seconds * 0.006D * vulnerability;
        double recoveryGain = (context.stabilitySupport() + context.comfortSupport()) * seconds * 0.004D;

        return new EgoPsychologyDelta(
                stressGain - stabilityRelief - budget.stressRelief(),
                focusSupport + budget.focusRecovery() - focusLoss,
                comfortGain + budget.comfortRecovery() - comfortLoss,
                recoveryGain - recoveryDrain - budget.capacityCost());
    }
}
