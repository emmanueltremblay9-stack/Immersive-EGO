package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class EgoSynergyValidator {
    private static final Set<String> KNOWN_ATTRIBUTE_PATHS = EgoAttributeCatalog.specs().stream()
            .map(EgoAttributeSpec::path)
            .collect(Collectors.toUnmodifiableSet());
    private static final Set<String> DISPLAY_TARGET_PATHS = EgoAttributeCatalog.displayOnlySpecs().stream()
            .map(EgoAttributeSpec::path)
            .collect(Collectors.toUnmodifiableSet());

    private EgoSynergyValidator() {
    }

    public static List<EgoSynergyEdge> validateCatalogEdges(List<EgoSynergyEdge> edges) {
        List<EgoSynergyEdge> immutableEdges = List.copyOf(edges);
        Set<String> seenPairs = new HashSet<>();

        for (EgoSynergyEdge edge : immutableEdges) {
            if (!KNOWN_ATTRIBUTE_PATHS.contains(edge.sourcePath())) {
                throw new IllegalArgumentException("unknown synergy source path: " + edge.sourcePath());
            }
            if (!KNOWN_ATTRIBUTE_PATHS.contains(edge.targetPath())) {
                throw new IllegalArgumentException("unknown synergy target path: " + edge.targetPath());
            }
            if (!DISPLAY_TARGET_PATHS.contains(edge.targetPath())) {
                throw new IllegalArgumentException("synergy target must be a display-only state path: " + edge.targetPath());
            }
            if (!seenPairs.add(edge.key())) {
                throw new IllegalArgumentException("duplicate synergy edge: " + edge.key());
            }
        }

        EgoSynergyCycleDetector.validateAcyclic(immutableEdges);
        return immutableEdges;
    }
}
