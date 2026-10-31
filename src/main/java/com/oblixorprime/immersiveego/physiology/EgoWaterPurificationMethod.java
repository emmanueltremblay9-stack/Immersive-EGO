package com.oblixorprime.immersiveego.physiology;

import java.util.Set;

public enum EgoWaterPurificationMethod {
    CHARCOAL_FILTER(1, 0.35D, Set.of("minecraft:charcoal", "minecraft:coal")),
    BOILING(2, 0.70D, Set.of("minecraft:campfire", "minecraft:furnace", "minecraft:smoker"));

    private final int qualitySteps;
    private final double contaminationReduction;
    private final Set<String> itemIds;

    EgoWaterPurificationMethod(int qualitySteps, double contaminationReduction, Set<String> itemIds) {
        this.qualitySteps = qualitySteps;
        this.contaminationReduction = contaminationReduction;
        this.itemIds = itemIds;
    }

    public int qualitySteps() {
        return qualitySteps;
    }

    public double contaminationReduction() {
        return contaminationReduction;
    }

    public Set<String> itemIds() {
        return itemIds;
    }
}
