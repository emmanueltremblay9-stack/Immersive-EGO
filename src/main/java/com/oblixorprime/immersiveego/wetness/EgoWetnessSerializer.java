package com.oblixorprime.immersiveego.wetness;

import com.oblixorprime.immersiveego.ImmersiveEgo;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

/** Localized migration/quarantine never decodes or resets another EGO attachment. */
public final class EgoWetnessSerializer implements IAttachmentSerializer<CompoundTag, EgoWetnessState> {
    @Override
    public EgoWetnessState read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
        boolean malformed = (tag.contains("schema_version") && !tag.contains("schema_version", Tag.TAG_INT))
                || (tag.contains("wetness") && !tag.contains("wetness", Tag.TAG_ANY_NUMERIC))
                || (tag.contains("quarantined") && !tag.contains("quarantined", Tag.TAG_BYTE));
        int version = tag.contains("schema_version") ? tag.getInt("schema_version") : 0;
        malformed |= version != 0 && !tag.contains("wetness");
        double value = tag.getDouble("wetness");
        malformed |= !Double.isFinite(value) || value < 0.0D || value > 1.0D;
        boolean quarantined = malformed || tag.getBoolean("quarantined");
        double bounded = Double.isFinite(value) ? Math.clamp(value, 0.0D, 1.0D) : 0.0D;
        // Schema 0 / absent data is the explicit dry legacy migration; schema 1 is unchanged.
        int migrated = version == 0 && !quarantined ? EgoWetnessState.CURRENT_SCHEMA : version;
        EgoWetnessState result = new EgoWetnessState(migrated, bounded, quarantined);
        if (!result.supported()) {
            ImmersiveEgo.LOGGER.warn("Wetness attachment suspended: schema={}, quarantined={}; other EGO data untouched",
                    version, quarantined);
        }
        return result;
    }

    @Override
    public CompoundTag write(EgoWetnessState state, HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("schema_version", state.schemaVersion());
        tag.putDouble("wetness", state.wetness());
        tag.putBoolean("quarantined", state.quarantined());
        return tag;
    }
}
