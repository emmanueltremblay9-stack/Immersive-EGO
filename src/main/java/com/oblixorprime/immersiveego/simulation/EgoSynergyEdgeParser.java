package com.oblixorprime.immersiveego.simulation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EgoSynergyEdgeParser {
    private static final Pattern EDGE_PATTERN = Pattern.compile(
            "^\\s*([a-z0-9_]+)\\s*->\\s*([a-z0-9_]+)\\s*:\\s*([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)\\s*$");

    private EgoSynergyEdgeParser() {
    }

    public static EgoSynergyEdge parse(String rawEdge) {
        if (rawEdge == null) {
            throw new IllegalArgumentException("synergy edge cannot be null");
        }

        Matcher matcher = EDGE_PATTERN.matcher(rawEdge);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("synergy edge must use format 'source -> target : weight'");
        }

        return new EgoSynergyEdge(
                matcher.group(1),
                matcher.group(2),
                Double.parseDouble(matcher.group(3)));
    }
}
