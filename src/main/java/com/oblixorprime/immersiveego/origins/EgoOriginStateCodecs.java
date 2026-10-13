package com.oblixorprime.immersiveego.origins;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public final class EgoOriginStateCodecs {
    public static final Codec<EgoOriginState> EGO_ORIGIN_STATE =
            Stored.CODEC.comapFlatMap(EgoOriginStateCodecs::decode, EgoOriginStateCodecs::store);

    private EgoOriginStateCodecs() {
    }

    private static DataResult<EgoOriginState> decode(Stored stored) {
        if (!EgoOriginState.canReadSchemaVersion(stored.schemaVersion())) {
            return DataResult.error(() -> "Unsupported EgoOriginState schema version: " + stored.schemaVersion());
        }
        return DataResult.success(new EgoOriginState(
                stored.schemaVersion(),
                stored.originId(),
                stored.heatAcclimation(),
                stored.coldAcclimation(),
                stored.altitudeAcclimation(),
                stored.humidityAcclimation()));
    }

    private static Stored store(EgoOriginState state) {
        return new Stored(
                state.schemaVersion(),
                state.originId(),
                state.heatAcclimation(),
                state.coldAcclimation(),
                state.altitudeAcclimation(),
                state.humidityAcclimation());
    }

    private record Stored(
            int schemaVersion,
            String originId,
            double heatAcclimation,
            double coldAcclimation,
            double altitudeAcclimation,
            double humidityAcclimation) {
        private static final Codec<Stored> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Codec.INT.optionalFieldOf("schema_version", EgoOriginState.CURRENT_SCHEMA_VERSION).forGetter(Stored::schemaVersion),
                        Codec.STRING.optionalFieldOf("origin_id", EgoOriginState.UNASSIGNED).forGetter(Stored::originId),
                        Codec.DOUBLE.optionalFieldOf("heat_acclimation", 0.0D).forGetter(Stored::heatAcclimation),
                        Codec.DOUBLE.optionalFieldOf("cold_acclimation", 0.0D).forGetter(Stored::coldAcclimation),
                        Codec.DOUBLE.optionalFieldOf("altitude_acclimation", 0.0D).forGetter(Stored::altitudeAcclimation),
                        Codec.DOUBLE.optionalFieldOf("humidity_acclimation", 0.0D).forGetter(Stored::humidityAcclimation))
                .apply(instance, Stored::new));
    }
}
