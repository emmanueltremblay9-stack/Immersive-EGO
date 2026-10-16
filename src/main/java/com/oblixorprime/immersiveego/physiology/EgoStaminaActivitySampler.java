package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.registry.EgoAttributes;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public final class EgoStaminaActivitySampler {
    private static final double FULL_INTENSITY_HORIZONTAL_SPEED = 0.20D;

    private EgoStaminaActivitySampler() {
    }

    public static EgoStaminaActivityContext sample(ServerPlayer player, int intervalTicks) {
        Vec3 deltaMovement = player.getDeltaMovement();
        double horizontalSpeed = Math.sqrt(
                deltaMovement.x * deltaMovement.x
                        + deltaMovement.z * deltaMovement.z);
        return new EgoStaminaActivityContext(
                EgoMath.clamp01(horizontalSpeed / FULL_INTENSITY_HORIZONTAL_SPEED),
                player.isSprinting(),
                player.isSwimming(),
                !player.onGround(),
                player.isUsingItem(),
                intervalTicks,
                player.getAttributeValue(EgoAttributes.STAMINA_CAPACITY));
    }
}
