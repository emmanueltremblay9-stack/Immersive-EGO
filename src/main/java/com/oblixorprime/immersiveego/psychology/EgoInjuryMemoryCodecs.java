package com.oblixorprime.immersiveego.psychology;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class EgoInjuryMemoryCodecs {
    public static final Codec<EgoInjuryMemory> EGO_INJURY_MEMORY =
            Stored.CODEC.comapFlatMap(EgoInjuryMemoryCodecs::decode, EgoInjuryMemoryCodecs::store);

    private EgoInjuryMemoryCodecs() {
    }

    private static DataResult<EgoInjuryMemory> decode(Stored stored) {
        if (!EgoInjuryMemory.canReadSchemaVersion(stored.schemaVersion())) {
            return DataResult.error(() -> "Unsupported EgoInjuryMemory schema version: " + stored.schemaVersion());
        }
        return DataResult.success(new EgoInjuryMemory(
                stored.schemaVersion(),
                stored.recentInjury(),
                stored.lingeringPain(),
                stored.recoveryDebt()));
    }

    private static Stored store(EgoInjuryMemory memory) {
        return new Stored(
                memory.schemaVersion(),
                memory.recentInjury(),
                memory.lingeringPain(),
                memory.recoveryDebt());
    }

    private record Stored(
            int schemaVersion,
            double recentInjury,
            double lingeringPain,
            double recoveryDebt) {
        private static final Codec<Stored> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Codec.INT.optionalFieldOf("schema_version", EgoInjuryMemory.CURRENT_SCHEMA_VERSION).forGetter(Stored::schemaVersion),
                        Codec.DOUBLE.optionalFieldOf("recent_injury", 0.0D).forGetter(Stored::recentInjury),
                        Codec.DOUBLE.optionalFieldOf("lingering_pain", 0.0D).forGetter(Stored::lingeringPain),
                        Codec.DOUBLE.optionalFieldOf("recovery_debt", 0.0D).forGetter(Stored::recoveryDebt))
                .apply(instance, Stored::new));
    }
}
