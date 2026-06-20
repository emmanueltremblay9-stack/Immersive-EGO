package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

final class EgoSynergyCycleDetectorTest {
    @Test
    void returnsEmptyForAcyclicEdges() {
        Optional<List<String>> cycle = EgoSynergyCycleDetector.findFirstCycle(List.of(
                new EgoSynergyEdge("fatigue", "ego_load", 0.5D),
                new EgoSynergyEdge("ego_load", "readiness", -0.5D),
                new EgoSynergyEdge("stress", "focus", -0.25D)));

        assertTrue(cycle.isEmpty());
    }

    @Test
    void findsFirstCycleInInputOrder() {
        Optional<List<String>> cycle = EgoSynergyCycleDetector.findFirstCycle(List.of(
                new EgoSynergyEdge("fatigue", "stress", 0.5D),
                new EgoSynergyEdge("stress", "ego_load", 0.5D),
                new EgoSynergyEdge("ego_load", "fatigue", 0.5D)));

        assertEquals(List.of("fatigue", "stress", "ego_load", "fatigue"), cycle.orElseThrow());
    }

    @Test
    void validateAcyclicRejectsCycles() {
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyCycleDetector.validateAcyclic(List.of(
                new EgoSynergyEdge("readiness", "focus", 0.25D),
                new EgoSynergyEdge("focus", "readiness", 0.25D))));
    }

    @Test
    void catalogValidatorRejectsCyclesAfterEndpointValidation() {
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyValidator.validateCatalogEdges(List.of(
                new EgoSynergyEdge("fatigue", "stress", 0.5D),
                new EgoSynergyEdge("stress", "ego_load", 0.5D),
                new EgoSynergyEdge("ego_load", "fatigue", 0.5D))));
    }
}
