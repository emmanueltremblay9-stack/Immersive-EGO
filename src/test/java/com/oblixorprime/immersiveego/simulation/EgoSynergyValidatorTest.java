package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

final class EgoSynergyValidatorTest {
    @Test
    void validatesCatalogEdgesAndCopiesList() {
        List<EgoSynergyEdge> input = new ArrayList<>();
        input.add(new EgoSynergyEdge("fatigue", "ego_load", 0.5D));
        input.add(new EgoSynergyEdge("stamina_capacity", "stamina", 0.25D));

        List<EgoSynergyEdge> validated = EgoSynergyValidator.validateCatalogEdges(input);
        input.add(new EgoSynergyEdge("stress", "readiness", -0.5D));

        assertEquals(2, validated.size());
        assertTrue(validated.contains(new EgoSynergyEdge("fatigue", "ego_load", 0.5D)));
        assertThrows(UnsupportedOperationException.class, () -> validated.add(new EgoSynergyEdge("stress", "readiness", -0.5D)));
    }

    @Test
    void rejectsUnknownEndpoints() {
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyValidator.validateCatalogEdges(List.of(
                new EgoSynergyEdge("missing", "ego_load", 0.5D))));
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyValidator.validateCatalogEdges(List.of(
                new EgoSynergyEdge("fatigue", "missing", 0.5D))));
    }

    @Test
    void rejectsCapabilityTargets() {
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyValidator.validateCatalogEdges(List.of(
                new EgoSynergyEdge("fatigue", "stamina_capacity", 0.5D))));
    }

    @Test
    void rejectsDuplicateSourceTargetPairs() {
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyValidator.validateCatalogEdges(List.of(
                new EgoSynergyEdge("fatigue", "ego_load", 0.5D),
                new EgoSynergyEdge("fatigue", "ego_load", 0.25D))));
    }
}
