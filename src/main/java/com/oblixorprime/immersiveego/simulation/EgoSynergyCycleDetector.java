package com.oblixorprime.immersiveego.simulation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class EgoSynergyCycleDetector {
    private EgoSynergyCycleDetector() {
    }

    public static void validateAcyclic(List<EgoSynergyEdge> edges) {
        findFirstCycle(edges).ifPresent(cycle -> {
            throw new IllegalArgumentException("synergy graph contains a cycle: " + String.join(" -> ", cycle));
        });
    }

    public static Optional<List<String>> findFirstCycle(List<EgoSynergyEdge> edges) {
        Map<String, List<String>> graph = buildGraph(edges);
        Set<String> visited = new HashSet<>();
        Set<String> visiting = new HashSet<>();
        ArrayDeque<String> path = new ArrayDeque<>();

        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                Optional<List<String>> cycle = visit(node, graph, visited, visiting, path);
                if (cycle.isPresent()) {
                    return cycle;
                }
            }
        }

        return Optional.empty();
    }

    private static Map<String, List<String>> buildGraph(List<EgoSynergyEdge> edges) {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        for (EgoSynergyEdge edge : edges) {
            graph.computeIfAbsent(edge.sourcePath(), ignored -> new ArrayList<>()).add(edge.targetPath());
            graph.computeIfAbsent(edge.targetPath(), ignored -> new ArrayList<>());
        }
        return graph;
    }

    private static Optional<List<String>> visit(
            String node,
            Map<String, List<String>> graph,
            Set<String> visited,
            Set<String> visiting,
            ArrayDeque<String> path) {
        visiting.add(node);
        path.addLast(node);

        for (String next : graph.getOrDefault(node, List.of())) {
            if (visiting.contains(next)) {
                return Optional.of(cycleFrom(path, next));
            }
            if (!visited.contains(next)) {
                Optional<List<String>> cycle = visit(next, graph, visited, visiting, path);
                if (cycle.isPresent()) {
                    return cycle;
                }
            }
        }

        path.removeLast();
        visiting.remove(node);
        visited.add(node);
        return Optional.empty();
    }

    private static List<String> cycleFrom(ArrayDeque<String> path, String repeatedNode) {
        List<String> currentPath = new ArrayList<>(path);
        int startIndex = currentPath.indexOf(repeatedNode);
        List<String> cycle = new ArrayList<>(currentPath.subList(startIndex, currentPath.size()));
        cycle.add(repeatedNode);
        return List.copyOf(cycle);
    }
}
