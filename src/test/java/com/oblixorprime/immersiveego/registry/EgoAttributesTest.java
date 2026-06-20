package com.oblixorprime.immersiveego.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EgoAttributesTest {
    @Test
    void attributeSpecsHaveUniquePathsAndValidRanges() {
        Set<String> paths = new HashSet<>();

        for (EgoAttributeSpec spec : EgoAttributeCatalog.specs()) {
            assertTrue(paths.add(spec.path()), "duplicate attribute path: " + spec.path());
            assertTrue(spec.minValue() <= spec.defaultValue(), spec.path());
            assertTrue(spec.defaultValue() <= spec.maxValue(), spec.path());
            assertTrue(spec.maxValue() > spec.minValue(), spec.path());
        }
    }

    @Test
    void capabilityAttributesArePositiveMultipliers() {
        assertEquals(8, EgoAttributeCatalog.capabilitySpecs().size());

        for (EgoAttributeSpec spec : EgoAttributeCatalog.capabilitySpecs()) {
            assertEquals(1.0D, spec.defaultValue(), spec.path());
            assertEquals(0.0D, spec.minValue(), spec.path());
            assertEquals(4.0D, spec.maxValue(), spec.path());
            assertEquals(EgoAttributeSentiment.POSITIVE, spec.sentiment(), spec.path());
        }
    }

    @Test
    void displayOnlyAttributesAreNormalizedMirrors() {
        assertEquals(12, EgoAttributeCatalog.displayOnlySpecs().size());

        for (EgoAttributeSpec spec : EgoAttributeCatalog.displayOnlySpecs()) {
            assertEquals(0.0D, spec.minValue(), spec.path());
            assertEquals(1.0D, spec.maxValue(), spec.path());
        }
    }

    @Test
    void harmfulMirrorsUseNegativeSentiment() {
        Set<String> negativeMirrors = Set.of("ego_load", "fatigue", "thermal_strain", "respiratory_strain", "stress");

        for (EgoAttributeSpec spec : EgoAttributeCatalog.displayOnlySpecs()) {
            EgoAttributeSentiment expected = negativeMirrors.contains(spec.path())
                    ? EgoAttributeSentiment.NEGATIVE
                    : EgoAttributeSentiment.POSITIVE;
            assertEquals(expected, spec.sentiment(), spec.path());
        }
    }
}
