package com.oblixorprime.immersiveego.simulation;

public record EgoSynergyEdge(String sourcePath, String targetPath, double weight) {
    public static final double MAX_ABSOLUTE_WEIGHT = 4.0D;

    public EgoSynergyEdge {
        sourcePath = requirePath("sourcePath", sourcePath);
        targetPath = requirePath("targetPath", targetPath);
        if (sourcePath.equals(targetPath)) {
            throw new IllegalArgumentException("synergy edge cannot target its own source");
        }
        if (!Double.isFinite(weight) || weight == 0.0D || Math.abs(weight) > MAX_ABSOLUTE_WEIGHT) {
            throw new IllegalArgumentException("synergy edge weight must be finite, non-zero, and within +/-" + MAX_ABSOLUTE_WEIGHT);
        }
    }

    public String key() {
        return sourcePath + "->" + targetPath;
    }

    private static String requirePath(String name, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " cannot be blank");
        }
        return value;
    }
}
