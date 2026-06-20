package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeRole;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class EgoSynergyEvaluator {
    private static final Map<String, EgoAttributeSpec> SPECS_BY_PATH = EgoAttributeCatalog.specs().stream()
            .collect(Collectors.toUnmodifiableMap(EgoAttributeSpec::path, spec -> spec));
    private static final Map<String, EgoAttributeSpec> DISPLAY_SPECS_BY_PATH = EgoAttributeCatalog.displayOnlySpecs().stream()
            .collect(Collectors.toUnmodifiableMap(EgoAttributeSpec::path, spec -> spec));

    private EgoSynergyEvaluator() {
    }

    public static EgoSynergyEvaluationResult evaluate(EgoState state, List<EgoSynergyEdge> edges) {
        return evaluate(state, edges, Map.of());
    }

    public static EgoSynergyEvaluationResult evaluate(EgoState state, List<EgoSynergyEdge> edges, Map<String, Double> externalSourceValues) {
        List<EgoSynergyEdge> validatedEdges = EgoSynergyValidator.validateCatalogEdges(edges);
        Map<String, Double> values = displayValues(state);
        Map<String, Double> externalValues = normalizedExternalValues(externalSourceValues);
        List<EgoSynergyEvaluationStep> steps = new ArrayList<>();

        EvaluationGraph graph = buildEvaluationGraph(validatedEdges);
        ArrayDeque<String> readyNodes = new ArrayDeque<>();
        for (String node : graph.indegreeByNode().keySet()) {
            if (graph.indegreeByNode().get(node) == 0) {
                readyNodes.addLast(node);
            }
        }

        while (!readyNodes.isEmpty()) {
            String sourcePath = readyNodes.removeFirst();
            double sourceValue = sourceValue(sourcePath, values, externalValues);
            for (EgoSynergyEdge edge : graph.outgoingBySource().getOrDefault(sourcePath, List.of())) {
                EgoAttributeSpec targetSpec = DISPLAY_SPECS_BY_PATH.get(edge.targetPath());
                double beforeValue = values.get(edge.targetPath());
                double delta = sourceValue * edge.weight();
                double afterValue = EgoMath.clamp01(beforeValue + delta);
                values.put(edge.targetPath(), afterValue);
                steps.add(new EgoSynergyEvaluationStep(
                        edge.sourcePath(),
                        edge.targetPath(),
                        sourceValue,
                        edge.weight(),
                        delta,
                        beforeValue,
                        afterValue));

                int nextIndegree = graph.indegreeByNode().merge(edge.targetPath(), -1, Integer::sum);
                if (nextIndegree == 0) {
                    readyNodes.addLast(edge.targetPath());
                }

                if (targetSpec == null) {
                    throw new IllegalArgumentException("synergy target has no display state path: " + edge.targetPath());
                }
            }
        }

        return new EgoSynergyEvaluationResult(applyDisplayValues(state, values), steps);
    }

    private static EvaluationGraph buildEvaluationGraph(List<EgoSynergyEdge> edges) {
        Map<String, List<EgoSynergyEdge>> outgoingBySource = new LinkedHashMap<>();
        Map<String, Integer> indegreeByNode = new LinkedHashMap<>();
        for (EgoSynergyEdge edge : edges) {
            outgoingBySource.computeIfAbsent(edge.sourcePath(), ignored -> new ArrayList<>()).add(edge);
            indegreeByNode.putIfAbsent(edge.sourcePath(), 0);
            indegreeByNode.merge(edge.targetPath(), 1, Integer::sum);
        }
        return new EvaluationGraph(outgoingBySource, indegreeByNode);
    }

    private static Map<String, Double> displayValues(EgoState state) {
        Map<String, Double> values = new HashMap<>();
        for (EgoAttributeSpec spec : EgoAttributeCatalog.displayOnlySpecs()) {
            values.put(spec.path(), state.displayValue(spec));
        }
        return values;
    }

    private static Map<String, Double> normalizedExternalValues(Map<String, Double> externalSourceValues) {
        Map<String, Double> values = new HashMap<>();
        for (Map.Entry<String, Double> entry : externalSourceValues.entrySet()) {
            values.put(entry.getKey(), EgoMath.clamp01(entry.getValue()));
        }
        return values;
    }

    private static double sourceValue(String sourcePath, Map<String, Double> values, Map<String, Double> externalValues) {
        if (values.containsKey(sourcePath)) {
            return values.get(sourcePath);
        }
        if (externalValues.containsKey(sourcePath)) {
            return externalValues.get(sourcePath);
        }

        EgoAttributeSpec spec = Optional.ofNullable(SPECS_BY_PATH.get(sourcePath))
                .orElseThrow(() -> new IllegalArgumentException("unknown synergy source path: " + sourcePath));
        if (spec.role() == EgoAttributeRole.CAPABILITY) {
            throw new IllegalArgumentException("missing normalized external source value for capability path: " + sourcePath);
        }
        throw new IllegalArgumentException("missing source value for synergy path: " + sourcePath);
    }

    private static EgoState applyDisplayValues(EgoState state, Map<String, Double> values) {
        EgoState result = state;
        for (EgoAttributeSpec spec : EgoAttributeCatalog.displayOnlySpecs()) {
            result = result.withDisplayValue(spec, values.get(spec.path()));
        }
        return result;
    }

    private record EvaluationGraph(
            Map<String, List<EgoSynergyEdge>> outgoingBySource,
            Map<String, Integer> indegreeByNode) {
    }
}
