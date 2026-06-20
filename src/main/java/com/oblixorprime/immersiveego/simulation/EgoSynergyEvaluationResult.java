package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.data.EgoState;
import java.util.List;

public record EgoSynergyEvaluationResult(EgoState state, List<EgoSynergyEvaluationStep> steps) {
    public EgoSynergyEvaluationResult {
        steps = List.copyOf(steps);
    }
}
