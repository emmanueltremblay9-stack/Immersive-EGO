package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorService;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public final class EgoHydrationEvents {
    private EgoHydrationEvents() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoHydrationEvents::onItemUseFinished);
    }

    public static Optional<EgoHydrationDemandContext> demandContext(ServerPlayer player, int intervalTicks) {
        if (!physiologyModuleEnabled()) {
            return Optional.empty();
        }
        return Optional.of(new EgoHydrationDemandContext(
                intervalTicks,
                player.getAttributeValue(EgoAttributes.HYDRATION_EFFICIENCY)));
    }

    public static boolean physiologyModuleEnabled() {
        return EgoPhysiologyModule.enabled();
    }

    private static void onItemUseFinished(LivingEntityUseItemEvent.Finish event) {
        if (!physiologyModuleEnabled()) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        EgoDrinkItemResolver.resolve(event.getItem()).ifPresent(profile -> {
            EgoState currentState = player.getData(EgoAttachments.EGO_STATE.get());
            EgoState updatedState = EgoHydrationService.applyDrink(
                    currentState,
                    profile,
                    player.getAttributeValue(EgoAttributes.HYDRATION_EFFICIENCY));
            if (!updatedState.equals(currentState)) {
                player.setData(EgoAttachments.EGO_STATE.get(), updatedState);
                EgoAttributeMirrorService.updatePlayerMirrors(player, updatedState);
            }
        });
    }
}
