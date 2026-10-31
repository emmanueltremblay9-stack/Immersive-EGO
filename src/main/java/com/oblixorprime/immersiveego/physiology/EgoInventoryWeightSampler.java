package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.registry.EgoAttributes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class EgoInventoryWeightSampler {
    private EgoInventoryWeightSampler() {
    }

    public static EgoInventoryWeightSnapshot sample(ServerPlayer player, long gameTime) {
        Inventory inventory = player.getInventory();
        List<EgoWeightedStack> stacks = new ArrayList<>();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack.isEmpty()) {
                continue;
            }
            stacks.add(new EgoWeightedStack(
                    BuiltInRegistries.ITEM.getKey(stack.getItem()).toString(),
                    stack.getCount(),
                    stack.getMaxStackSize(),
                    stack.isDamageableItem()));
        }
        return EgoInventoryWeightCalculator.calculate(
                gameTime,
                stacks,
                player.getAttributeValue(EgoAttributes.LOAD_TOLERANCE));
    }
}
