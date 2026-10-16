package com.oblixorprime.immersiveego.physiology;

import java.util.Objects;
import java.util.Set;

public final class EgoItemWeightResolver {
    private static final double SINGLE_ITEM_WEIGHT = 4.0D;
    private static final double BLOCK_ITEM_WEIGHT = 1.0D;
    private static final double BULKY_STACK_ITEM_WEIGHT = 0.25D;
    private static final double LIGHT_STACK_ITEM_WEIGHT = 0.10D;
    private static final Set<String> VANILLA_BLOCKLIKE_ITEMS = Set.of(
            "andesite",
            "basalt",
            "blackstone",
            "cobbled_deepslate",
            "cobblestone",
            "deepslate",
            "diorite",
            "dirt",
            "end_stone",
            "granite",
            "gravel",
            "netherrack",
            "sand",
            "stone");

    private EgoItemWeightResolver() {
    }

    public static double stackWeight(EgoWeightedStack stack) {
        Objects.requireNonNull(stack, "stack");
        return unitWeight(stack) * stack.count();
    }

    public static double unitWeight(EgoWeightedStack stack) {
        Objects.requireNonNull(stack, "stack");
        String path = stack.path();
        if (stack.maxStackSize() == 1 || stack.damageable()) {
            return SINGLE_ITEM_WEIGHT;
        }
        if (isBlocklike(path)) {
            return BLOCK_ITEM_WEIGHT;
        }
        if (stack.maxStackSize() <= 16) {
            return BULKY_STACK_ITEM_WEIGHT;
        }
        return LIGHT_STACK_ITEM_WEIGHT;
    }

    private static boolean isBlocklike(String path) {
        return VANILLA_BLOCKLIKE_ITEMS.contains(path)
                || path.endsWith("_block")
                || path.endsWith("_ore")
                || path.endsWith("_log")
                || path.endsWith("_planks")
                || path.endsWith("_stone")
                || path.endsWith("_bricks");
    }
}
