package com.oblixorprime.immersiveego.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;

public final class EgoStateCodecs {
    public static final Codec<EgoState> EGO_STATE = Stored.CODEC.comapFlatMap(EgoStateCodecs::decode, EgoStateCodecs::store);

    private EgoStateCodecs() {
    }

    private static DataResult<EgoState> decode(Stored stored) {
        if (!EgoState.canReadSchemaVersion(stored.schemaVersion())) {
            return DataResult.error(() -> "Unsupported EgoState schema version: " + stored.schemaVersion());
        }
        return DataResult.success(new EgoState(
                stored.schemaVersion(),
                stored.egoLoad(),
                stored.readiness(),
                stored.hydration(),
                stored.stamina(),
                stored.fatigue(),
                stored.thermalStrain(),
                stored.respiratoryStrain(),
                stored.nutritionBalance(),
                stored.stress(),
                stored.focus(),
                stored.comfort(),
                stored.recoveryCapacity()));
    }

    private static Stored store(EgoState state) {
        return new Stored(
                state.schemaVersion(),
                state.egoLoad(),
                state.readiness(),
                state.hydration(),
                state.stamina(),
                state.fatigue(),
                state.thermalStrain(),
                state.respiratoryStrain(),
                state.nutritionBalance(),
                state.stress(),
                state.focus(),
                state.comfort(),
                state.recoveryCapacity());
    }

    private record Stored(
            int schemaVersion,
            double egoLoad,
            double readiness,
            double hydration,
            double stamina,
            double fatigue,
            double thermalStrain,
            double respiratoryStrain,
            double nutritionBalance,
            double stress,
            double focus,
            double comfort,
            double recoveryCapacity) {
        private static final Codec<Stored> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Codec.INT.optionalFieldOf("schema_version", EgoState.CURRENT_SCHEMA_VERSION).forGetter(Stored::schemaVersion),
                        Codec.DOUBLE.optionalFieldOf("ego_load", EgoAttributeCatalog.EGO_LOAD.defaultValue()).forGetter(Stored::egoLoad),
                        Codec.DOUBLE.optionalFieldOf("readiness", EgoAttributeCatalog.READINESS.defaultValue()).forGetter(Stored::readiness),
                        Codec.DOUBLE.optionalFieldOf("hydration", EgoAttributeCatalog.HYDRATION.defaultValue()).forGetter(Stored::hydration),
                        Codec.DOUBLE.optionalFieldOf("stamina", EgoAttributeCatalog.STAMINA.defaultValue()).forGetter(Stored::stamina),
                        Codec.DOUBLE.optionalFieldOf("fatigue", EgoAttributeCatalog.FATIGUE.defaultValue()).forGetter(Stored::fatigue),
                        Codec.DOUBLE.optionalFieldOf("thermal_strain", EgoAttributeCatalog.THERMAL_STRAIN.defaultValue()).forGetter(Stored::thermalStrain),
                        Codec.DOUBLE.optionalFieldOf("respiratory_strain", EgoAttributeCatalog.RESPIRATORY_STRAIN.defaultValue()).forGetter(Stored::respiratoryStrain),
                        Codec.DOUBLE.optionalFieldOf("nutrition_balance", EgoAttributeCatalog.NUTRITION_BALANCE.defaultValue()).forGetter(Stored::nutritionBalance),
                        Codec.DOUBLE.optionalFieldOf("stress", EgoAttributeCatalog.STRESS.defaultValue()).forGetter(Stored::stress),
                        Codec.DOUBLE.optionalFieldOf("focus", EgoAttributeCatalog.FOCUS.defaultValue()).forGetter(Stored::focus),
                        Codec.DOUBLE.optionalFieldOf("comfort", EgoAttributeCatalog.COMFORT.defaultValue()).forGetter(Stored::comfort),
                        Codec.DOUBLE.optionalFieldOf("recovery_capacity", EgoAttributeCatalog.RECOVERY_CAPACITY.defaultValue()).forGetter(Stored::recoveryCapacity))
                .apply(instance, Stored::new));
    }
}
