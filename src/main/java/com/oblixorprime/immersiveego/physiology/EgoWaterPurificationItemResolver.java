package com.oblixorprime.immersiveego.physiology;

import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public final class EgoWaterPurificationItemResolver {
    private EgoWaterPurificationItemResolver() {
    }

    public static Optional<EgoWaterPurificationMethod> resolve(ItemStack stack) {
        return EgoWaterPurificationService.methodForItemId(
                BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
    }
}
