package com.oblixorprime.immersiveego.registry;

import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.data.EgoStateCodecs;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.origins.EgoOriginStateCodecs;
import com.oblixorprime.immersiveego.psychology.EgoInjuryMemory;
import com.oblixorprime.immersiveego.psychology.EgoInjuryMemoryCodecs;
import com.oblixorprime.immersiveego.wetness.EgoWetnessSerializer;
import com.oblixorprime.immersiveego.wetness.EgoWetnessState;
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

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EgoOriginState>> EGO_ORIGIN_STATE =
            ATTACHMENT_TYPES.register("ego_origin_state", () -> AttachmentType.builder(EgoOriginState::defaultState)
                    .serialize(EgoOriginStateCodecs.EGO_ORIGIN_STATE)
                    .copyOnDeath()
                    .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EgoInjuryMemory>> EGO_INJURY_MEMORY =
            ATTACHMENT_TYPES.register("ego_injury_memory", () -> AttachmentType.builder(EgoInjuryMemory::defaultState)
                    .serialize(EgoInjuryMemoryCodecs.EGO_INJURY_MEMORY)
                    .copyOnDeath()
                    .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<EgoWetnessState>> EGO_WETNESS =
            ATTACHMENT_TYPES.register("wetness", () -> AttachmentType.builder(EgoWetnessState::dry)
                    .serialize(new EgoWetnessSerializer())
                    .build());

    private EgoAttachments() {
    }

    public static void register(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
