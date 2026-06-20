package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

final class EgoCurveTest {
    @Test
    void evaluatesLinearSegmentsAndClampsInputs() {
        EgoCurve curve = EgoCurve.of(
                new EgoCurvePoint(0.0D, 0.0D),
                new EgoCurvePoint(0.5D, 0.2D),
                new EgoCurvePoint(1.0D, 1.0D));

        assertEquals(0.0D, curve.evaluate(-1.0D));
        assertEquals(0.0D, curve.evaluate(Double.NaN));
        assertEquals(0.1D, curve.evaluate(0.25D), 1.0E-12D);
        assertEquals(0.2D, curve.evaluate(0.5D), 1.0E-12D);
        assertEquals(0.6D, curve.evaluate(0.75D), 1.0E-12D);
        assertEquals(1.0D, curve.evaluate(2.0D));
    }

    @Test
    void identityCurvePreservesNormalizedInput() {
        EgoCurve identity = EgoCurve.identity();

        assertEquals(0.0D, identity.evaluate(0.0D));
        assertEquals(0.33D, identity.evaluate(0.33D), 1.0E-12D);
        assertEquals(1.0D, identity.evaluate(1.0D));
    }

    @Test
    void rejectsCurvesWithInsufficientCoverage() {
        assertThrows(IllegalArgumentException.class, () -> new EgoCurve(List.of(new EgoCurvePoint(0.0D, 0.0D))));
        assertThrows(IllegalArgumentException.class, () -> EgoCurve.of(
                new EgoCurvePoint(0.25D, 0.0D),
                new EgoCurvePoint(1.0D, 1.0D)));
        assertThrows(IllegalArgumentException.class, () -> EgoCurve.of(
                new EgoCurvePoint(0.0D, 0.0D),
                new EgoCurvePoint(0.75D, 1.0D)));
    }

    @Test
    void rejectsUnsortedOrDuplicateInputs() {
        assertThrows(IllegalArgumentException.class, () -> EgoCurve.of(
                new EgoCurvePoint(0.0D, 0.0D),
                new EgoCurvePoint(0.5D, 0.5D),
                new EgoCurvePoint(0.5D, 0.75D),
                new EgoCurvePoint(1.0D, 1.0D)));
        assertThrows(IllegalArgumentException.class, () -> EgoCurve.of(
                new EgoCurvePoint(0.0D, 0.0D),
                new EgoCurvePoint(0.75D, 0.75D),
                new EgoCurvePoint(0.5D, 0.5D),
                new EgoCurvePoint(1.0D, 1.0D)));
    }

    @Test
    void rejectsNonNormalizedPoints() {
        assertThrows(IllegalArgumentException.class, () -> new EgoCurvePoint(-0.01D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoCurvePoint(1.01D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoCurvePoint(Double.POSITIVE_INFINITY, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoCurvePoint(0.0D, -0.01D));
        assertThrows(IllegalArgumentException.class, () -> new EgoCurvePoint(0.0D, Double.NaN));
    }

    @Test
    void copiesInputPointsIntoImmutableList() {
        List<EgoCurvePoint> points = new ArrayList<>();
        points.add(new EgoCurvePoint(0.0D, 0.0D));
        points.add(new EgoCurvePoint(1.0D, 1.0D));

        EgoCurve curve = new EgoCurve(points);
        points.add(new EgoCurvePoint(1.0D, 0.0D));

        assertEquals(2, curve.points().size());
        assertThrows(UnsupportedOperationException.class, () -> curve.points().add(new EgoCurvePoint(1.0D, 1.0D)));
        assertTrue(curve.points().contains(new EgoCurvePoint(1.0D, 1.0D)));
    }
}
