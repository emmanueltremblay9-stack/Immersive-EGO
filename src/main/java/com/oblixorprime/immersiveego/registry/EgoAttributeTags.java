package com.oblixorprime.immersiveego.registry;

import com.oblixorprime.immersiveego.ImmersiveEgo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;

public final class EgoAttributeTags {
    public static final TagKey<Attribute> CAPABILITY = create("capability");
    public static final TagKey<Attribute> DISPLAY_ONLY = create("display_only");

    private EgoAttributeTags() {
    }

    private static TagKey<Attribute> create(String path) {
        return TagKey.create(Registries.ATTRIBUTE, ResourceLocation.fromNamespaceAndPath(ImmersiveEgo.MOD_ID, path));
    }
}
