package com.oblixorprime.immersiveego.physiology;

public enum EgoWaterQuality {
    CONTAMINATED(0, 0.75D, 0.20D),
    QUESTIONABLE(1, 0.90D, 0.08D),
    POTABLE(2, 1.00D, 0.00D),
    PURIFIED(3, 1.05D, 0.00D);

    private final int rank;
    private final double hydrationMultiplier;
    private final double maxStressPenalty;

    EgoWaterQuality(int rank, double hydrationMultiplier, double maxStressPenalty) {
        this.rank = rank;
        this.hydrationMultiplier = hydrationMultiplier;
        this.maxStressPenalty = maxStressPenalty;
    }

    public double hydrationMultiplier() {
        return hydrationMultiplier;
    }

    public double stressPenalty(double contaminationRisk) {
        if (!Double.isFinite(contaminationRisk) || contaminationRisk < 0.0D || contaminationRisk > 1.0D) {
            throw new IllegalArgumentException("contaminationRisk must be finite and normalized to 0..1");
        }
        return contaminationRisk * maxStressPenalty;
    }

    public EgoWaterQuality improve(int steps) {
        if (steps <= 0) {
            return this;
        }
        int targetRank = Math.min(PURIFIED.rank, rank + steps);
        for (EgoWaterQuality quality : values()) {
            if (quality.rank == targetRank) {
                return quality;
            }
        }
        throw new IllegalStateException("Unknown water quality rank: " + targetRank);
    }
}
