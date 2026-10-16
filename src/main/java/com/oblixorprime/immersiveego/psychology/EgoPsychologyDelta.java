package com.oblixorprime.immersiveego.psychology;

public record EgoPsychologyDelta(
        double stressDelta,
        double focusDelta,
        double comfortDelta,
        double recoveryCapacityDelta) {
    public EgoPsychologyDelta {
        stressDelta = requireFinite("stressDelta", stressDelta);
        focusDelta = requireFinite("focusDelta", focusDelta);
        comfortDelta = requireFinite("comfortDelta", comfortDelta);
        recoveryCapacityDelta = requireFinite("recoveryCapacityDelta", recoveryCapacityDelta);
    }

    private static double requireFinite(String name, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite");
        }
        return value;
    }
}
