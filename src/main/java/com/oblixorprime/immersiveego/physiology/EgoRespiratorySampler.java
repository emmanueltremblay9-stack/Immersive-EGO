package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.registry.EgoAttributes;
import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public final class EgoRespiratorySampler {
    private EgoRespiratorySampler() {
    }

    public static EgoRespiratorySample sample(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        int blockY = player.blockPosition().getY();
        int seaLevel = level.getSeaLevel();
        boolean inWater = player.isInWater();
        boolean underwater = player.isUnderWater();

        return new EgoRespiratorySample(
                EgoRespiratoryPressureCalculator.altitudePressure(blockY, seaLevel),
                EgoRespiratoryPressureCalculator.depthPressure(blockY, seaLevel),
                EgoRespiratoryPressureCalculator.submersionPressure(inWater, underwater),
                EgoRespiratoryPressureCalculator.dimensionPressure(dimensionFrom(level.dimension())),
                player.getAttributeValue(EgoAttributes.RESPIRATION_EFFICIENCY));
    }

    public static EgoRespiratoryDimension dimensionFrom(ResourceKey<Level> dimension) {
        Objects.requireNonNull(dimension, "dimension");
        if (Level.NETHER.equals(dimension)) {
            return EgoRespiratoryDimension.NETHER;
        }
        if (Level.END.equals(dimension)) {
            return EgoRespiratoryDimension.END;
        }
        if (Level.OVERWORLD.equals(dimension)) {
            return EgoRespiratoryDimension.OVERWORLD;
        }
        return EgoRespiratoryDimension.OTHER;
    }
}
