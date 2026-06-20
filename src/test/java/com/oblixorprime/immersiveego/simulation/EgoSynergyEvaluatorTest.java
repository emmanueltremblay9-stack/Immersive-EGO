package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class EgoSynergyEvaluatorTest {
    @Test
    void evaluatesEdgesInTopologicalOrder() {
        EgoState input = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.5D)
                .withDisplayValue(EgoAttributeCatalog.STRESS, 0.0D)
                .withDisplayValue(EgoAttributeCatalog.EGO_LOAD, 0.0D);

        EgoSynergyEvaluationResult result = EgoSynergyEvaluator.evaluate(input, List.of(
                new EgoSynergyEdge("stress", "ego_load", 0.5D),
                new EgoSynergyEdge("fatigue", "stress", 1.0D)));

        assertEquals(0.5D, result.state().stress(), 1.0E-12D);
        assertEquals(0.25D, result.state().egoLoad(), 1.0E-12D);
        assertEquals("fatigue", result.steps().get(0).sourcePath());
        assertEquals("stress", result.steps().get(1).sourcePath());
    }

    @Test
    void clampsTargetsAndLeavesInputUnchanged() {
        EgoState input = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STRESS, 1.0D)
                .withDisplayValue(EgoAttributeCatalog.READINESS, 0.1D);

        EgoSynergyEvaluationResult result = EgoSynergyEvaluator.evaluate(input, List.of(
                new EgoSynergyEdge("stress", "readiness", -0.5D)));

        assertEquals(0.0D, result.state().readiness(), 1.0E-12D);
        assertEquals(0.1D, input.readiness(), 1.0E-12D);
    }

    @Test
    void recordsEvaluationSteps() {
        EgoState input = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.4D)
                .withDisplayValue(EgoAttributeCatalog.EGO_LOAD, 0.1D);

        EgoSynergyEvaluationResult result = EgoSynergyEvaluator.evaluate(input, List.of(
                new EgoSynergyEdge("fatigue", "ego_load", 0.5D)));
        EgoSynergyEvaluationStep step = result.steps().getFirst();

        assertEquals("fatigue", step.sourcePath());
        assertEquals("ego_load", step.targetPath());
        assertEquals(0.4D, step.sourceValue(), 1.0E-12D);
        assertEquals(0.5D, step.weight(), 1.0E-12D);
        assertEquals(0.2D, step.delta(), 1.0E-12D);
        assertEquals(0.1D, step.beforeValue(), 1.0E-12D);
        assertEquals(0.3D, step.afterValue(), 1.0E-12D);
    }

    @Test
    void canUseNormalizedExternalSourceValues() {
        EgoState input = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.2D);

        EgoSynergyEvaluationResult result = EgoSynergyEvaluator.evaluate(input, List.of(
                new EgoSynergyEdge("stamina_capacity", "stamina", 0.4D)), Map.of("stamina_capacity", 0.5D));

        assertEquals(0.4D, result.state().stamina(), 1.0E-12D);
    }

    @Test
    void rejectsMissingExternalCapabilitySourceValues() {
        EgoState input = EgoState.defaultState();

        assertThrows(IllegalArgumentException.class, () -> EgoSynergyEvaluator.evaluate(input, List.of(
                new EgoSynergyEdge("stamina_capacity", "stamina", 0.4D))));
    }
}
