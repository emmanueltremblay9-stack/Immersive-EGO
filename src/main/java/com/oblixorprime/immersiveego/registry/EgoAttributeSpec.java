package com.oblixorprime.immersiveego.registry;

import com.oblixorprime.immersiveego.ImmersiveEgo;

public record EgoAttributeSpec(
        String path,
        EgoAttributeRole role,
        double defaultValue,
        double minValue,
        double maxValue,
        EgoAttributeSentiment sentiment) {
    public EgoAttributeSpec {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Attribute path cannot be blank");
        }
        if (role == null) {
            throw new IllegalArgumentException("Attribute role cannot be null");
        }
        if (!Double.isFinite(defaultValue) || !Double.isFinite(minValue) || !Double.isFinite(maxValue)) {
            throw new IllegalArgumentException("Attribute bounds must be finite");
        }
        if (minValue > maxValue) {
            throw new IllegalArgumentException("Attribute min cannot exceed max");
        }
        if (defaultValue < minValue || defaultValue > maxValue) {
            throw new IllegalArgumentException("Attribute default must be inside min/max");
        }
        if (sentiment == null) {
            throw new IllegalArgumentException("Attribute sentiment cannot be null");
        }
    }

    public String descriptionId() {
        return ImmersiveEgo.MOD_ID + ":" + this.path;
    }
}
