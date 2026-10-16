package com.oblixorprime.immersiveego.physiology;

public record EgoStaminaActivityDelta(
        double movementCost,
        double sprintCost,
        double swimmingCost,
        double airborneCost,
        double recovery,
        double netChange) {
    public EgoStaminaActivityDelta {
        movementCost = requireNonNegative("movementCost", movementCost);
        sprintCost = requireNonNegative("sprintCost", sprintCost);
        swimmingCost = requireNonNegative("swimmingCost", swimmingCost);
        airborneCost = requireNonNegative("airborneCost", airborneCost);
        recovery = requireNonNegative("recovery", recovery);
        if (!Double.isFinite(netChange)) {
            throw new IllegalArgumentException("netChange must be finite");
        }
    }

    public double totalCost() {
        return movementCost + sprintCost + swimmingCost + airborneCost;
    }

    private static double requireNonNegative(String name, double value) {
        if (!Double.isFinite(value) || value < 0.0D) {
            throw new IllegalArgumentException(name + " must be finite and non-negative");
        }
        return value;
    }
}
