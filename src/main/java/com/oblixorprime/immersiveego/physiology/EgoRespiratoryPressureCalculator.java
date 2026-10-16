package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

final class EgoRespiratoryPressureCalculator {
    private static final double ALTITUDE_PRESSURE_START_BLOCKS = 96.0D;
    private static final double ALTITUDE_PRESSURE_RANGE_BLOCKS = 128.0D;
    private static final double DEPTH_PRESSURE_START_BELOW_SEA_LEVEL_BLOCKS = 32.0D;
    private static final double DEPTH_PRESSURE_RANGE_BLOCKS = 96.0D;
    private static final double PARTIAL_SUBMERSION_PRESSURE = 0.35D;
    private static final double UNDERWATER_PRESSURE = 1.0D;
    private static final double NETHER_DIMENSION_PRESSURE = 0.35D;
    private static final double END_DIMENSION_PRESSURE = 0.25D;
    private static final double OTHER_DIMENSION_PRESSURE = 0.15D;

    private EgoRespiratoryPressureCalculator() {
    }

    static double altitudePressure(int blockY, int seaLevel) {
        double pressureStart = seaLevel + ALTITUDE_PRESSURE_START_BLOCKS;
        return EgoMath.clamp01((blockY - pressureStart) / ALTITUDE_PRESSURE_RANGE_BLOCKS);
    }

    static double depthPressure(int blockY, int seaLevel) {
        double pressureStart = seaLevel - DEPTH_PRESSURE_START_BELOW_SEA_LEVEL_BLOCKS;
        return EgoMath.clamp01((pressureStart - blockY) / DEPTH_PRESSURE_RANGE_BLOCKS);
    }

    static double submersionPressure(boolean inWater, boolean underwater) {
        if (underwater) {
            return UNDERWATER_PRESSURE;
        }
        return inWater ? PARTIAL_SUBMERSION_PRESSURE : 0.0D;
    }

    static double dimensionPressure(EgoRespiratoryDimension dimension) {
        return switch (Objects.requireNonNull(dimension, "dimension")) {
            case OVERWORLD -> 0.0D;
            case NETHER -> NETHER_DIMENSION_PRESSURE;
            case END -> END_DIMENSION_PRESSURE;
            case OTHER -> OTHER_DIMENSION_PRESSURE;
        };
    }
}
