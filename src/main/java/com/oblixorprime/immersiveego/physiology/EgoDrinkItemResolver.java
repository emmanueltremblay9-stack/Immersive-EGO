package com.oblixorprime.immersiveego.physiology;

import java.util.Optional;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public final class EgoDrinkItemResolver {
    private EgoDrinkItemResolver() {
    }

    public static Optional<EgoDrinkProfile> resolve(ItemStack stack) {
        if (stack.is(Items.POTION)) {
            PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
            if (contents != null && contents.is(Potions.WATER)) {
                return EgoDrinkProfileCatalog.defaultById(EgoDrinkProfileCatalog.WATER_POTION);
            }
            return Optional.empty();
        }
        if (stack.is(Items.MILK_BUCKET)) {
            return EgoDrinkProfileCatalog.defaultById(EgoDrinkProfileCatalog.MILK_BUCKET);
        }
        if (stack.is(Items.HONEY_BOTTLE)) {
            return EgoDrinkProfileCatalog.defaultById(EgoDrinkProfileCatalog.HONEY_BOTTLE);
        }
        return Optional.empty();
    }
}
