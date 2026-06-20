package com.oblixorprime.immersiveego.registry;

import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.data.EgoStateCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class EgoAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ImmersiveEgo.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EgoState>> EGO_STATE =
            ATTACHMENT_TYPES.register("ego_state", () -> AttachmentType.builder(EgoState::defaultState)
                    .serialize(EgoStateCodecs.EGO_STATE)
                    .copyOnDeath()
                    .build());

    private EgoAttachments() {
    }

    public static void register(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
