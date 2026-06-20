package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeRole;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import java.util.List;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class EgoAttributeMirrorService {
    private static final double EPSILON = 1.0E-7D;

    private EgoAttributeMirrorService() {
    }

    public static void register(IEventBus gameEventBus) {
        gameEventBus.addListener(EgoAttributeMirrorService::onPlayerLoggedIn);
        gameEventBus.addListener(EgoAttributeMirrorService::onPlayerRespawn);
    }

    public static void updatePlayerMirrors(Player player) {
        Objects.requireNonNull(player, "player");
        if (player.level().isClientSide()) {
            return;
        }

        updatePlayerMirrors(player, player.getData(EgoAttachments.EGO_STATE.get()));
    }

    public static void updatePlayerMirrors(Player player, EgoState state) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(state, "state");
        if (player.level().isClientSide()) {
            return;
        }

        List<EgoAttributeSpec> specs = EgoAttributeCatalog.displayOnlySpecs();
        List<DeferredHolder<Attribute, Attribute>> attributes = EgoAttributes.displayAttributes();
        if (specs.size() != attributes.size()) {
            throw new IllegalStateException("EGO display attribute catalog and registry are out of sync");
        }

        for (int i = 0; i < specs.size(); i++) {
            EgoAttributeSpec spec = specs.get(i);
            AttributeInstance instance = player.getAttribute(attributes.get(i));
            if (instance == null) {
                ImmersiveEgo.LOGGER.warn("Cannot mirror EGO state field {} because the player lacks its attribute", spec.path());
                continue;
            }

            applyMirror(instance, spec, state.displayValue(spec));
        }
    }

    public static ResourceLocation mirrorModifierId(EgoAttributeSpec spec) {
        Objects.requireNonNull(spec, "spec");
        if (spec.role() != EgoAttributeRole.DISPLAY_ONLY) {
            throw new IllegalArgumentException("Only display-only EGO attributes can be mirrored: " + spec.path());
        }

        return ResourceLocation.fromNamespaceAndPath(ImmersiveEgo.MOD_ID, "mirror/" + spec.path());
    }

    private static void applyMirror(AttributeInstance instance, EgoAttributeSpec spec, double targetValue) {
        ResourceLocation modifierId = mirrorModifierId(spec);
        instance.removeModifier(modifierId);

        double amount = targetValue - spec.defaultValue();
        if (Math.abs(amount) > EPSILON) {
            instance.addTransientModifier(new AttributeModifier(
                    modifierId,
                    amount,
                    AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        updatePlayerMirrors(event.getEntity());
    }

    private static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        updatePlayerMirrors(event.getEntity());
    }
}
