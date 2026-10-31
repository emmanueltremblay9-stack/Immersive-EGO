package com.oblixorprime.immersiveego.nutrition;

import com.oblixorprime.immersiveego.compat.marieslib.EgoMariesLibFacade;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorService;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public final class EgoNutritionEvents {
    private static final int FOOD_EVENT_INTERVAL_TICKS = 20;

    private EgoNutritionEvents() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoNutritionEvents::onItemUseFinished);
    }

    private static void onItemUseFinished(LivingEntityUseItemEvent.Finish event) {
        if (!EgoNutritionModule.enabled()) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        FoodProperties food = event.getItem().getFoodProperties(player);
        if (food == null) {
            return;
        }

        EgoMariesLibFacade.fireFoodConsumed(player, event.getItem());
        EgoNutritionContext context = EgoNutritionContext.fromMemory(
                FOOD_EVENT_INTERVAL_TICKS,
                EgoMariesLibFacade.readDietaryMemory(player));
        EgoState currentState = player.getData(EgoAttachments.EGO_STATE.get());
        EgoState updatedState = EgoNutritionService.applyNutrition(currentState, context);
        if (!updatedState.equals(currentState)) {
            player.setData(EgoAttachments.EGO_STATE.get(), updatedState);
            EgoAttributeMirrorService.updatePlayerMirrors(player, updatedState);
        }
    }
}
