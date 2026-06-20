package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

final class EgoSynergyEdgeParserTest {
    @Test
    void parsesCanonicalEdgeFormat() {
        EgoSynergyEdge edge = EgoSynergyEdgeParser.parse("fatigue -> ego_load : 0.6");

        assertEquals("fatigue", edge.sourcePath());
        assertEquals("ego_load", edge.targetPath());
        assertEquals(0.6D, edge.weight(), 1.0E-12D);
    }

    @Test
    void parsesCompactNegativeWeights() {
        EgoSynergyEdge edge = EgoSynergyEdgeParser.parse("stress->readiness:-0.25");

        assertEquals("stress", edge.sourcePath());
        assertEquals("readiness", edge.targetPath());
        assertEquals(-0.25D, edge.weight(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyEdgeParser.parse(null));
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyEdgeParser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyEdgeParser.parse("fatigue ego_load 0.6"));
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyEdgeParser.parse("Fatigue -> ego_load : 0.6"));
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyEdgeParser.parse("fatigue -> ego-load : 0.6"));
        assertThrows(IllegalArgumentException.class, () -> EgoSynergyEdgeParser.parse("fatigue -> ego_load : NaN"));
    }

    @Test
    void edgeRejectsUnsafeWeightsAndSelfTargets() {
        assertThrows(IllegalArgumentException.class, () -> new EgoSynergyEdge("fatigue", "fatigue", 0.5D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSynergyEdge("fatigue", "ego_load", 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSynergyEdge("fatigue", "ego_load", Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> new EgoSynergyEdge("fatigue", "ego_load", 4.01D));
        assertThrows(IllegalArgumentException.class, () -> new EgoSynergyEdge("fatigue", "ego_load", -4.01D));
    }
}
