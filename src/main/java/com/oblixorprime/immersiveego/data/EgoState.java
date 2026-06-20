package com.oblixorprime.immersiveego.data;

import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeRole;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Objects;

public record EgoState(
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
    public static final int CURRENT_SCHEMA_VERSION = 1;

    private static final EgoState DEFAULT = new EgoState(
            CURRENT_SCHEMA_VERSION,
            EgoAttributeCatalog.EGO_LOAD.defaultValue(),
            EgoAttributeCatalog.READINESS.defaultValue(),
            EgoAttributeCatalog.HYDRATION.defaultValue(),
            EgoAttributeCatalog.STAMINA.defaultValue(),
            EgoAttributeCatalog.FATIGUE.defaultValue(),
            EgoAttributeCatalog.THERMAL_STRAIN.defaultValue(),
            EgoAttributeCatalog.RESPIRATORY_STRAIN.defaultValue(),
            EgoAttributeCatalog.NUTRITION_BALANCE.defaultValue(),
            EgoAttributeCatalog.STRESS.defaultValue(),
            EgoAttributeCatalog.FOCUS.defaultValue(),
            EgoAttributeCatalog.COMFORT.defaultValue(),
            EgoAttributeCatalog.RECOVERY_CAPACITY.defaultValue());

    public EgoState {
        schemaVersion = CURRENT_SCHEMA_VERSION;
        egoLoad = EgoMath.clamp01(egoLoad);
        readiness = EgoMath.clamp01(readiness);
        hydration = EgoMath.clamp01(hydration);
        stamina = EgoMath.clamp01(stamina);
        fatigue = EgoMath.clamp01(fatigue);
        thermalStrain = EgoMath.clamp01(thermalStrain);
        respiratoryStrain = EgoMath.clamp01(respiratoryStrain);
        nutritionBalance = EgoMath.clamp01(nutritionBalance);
        stress = EgoMath.clamp01(stress);
        focus = EgoMath.clamp01(focus);
        comfort = EgoMath.clamp01(comfort);
        recoveryCapacity = EgoMath.clamp01(recoveryCapacity);
    }

    public static EgoState defaultState() {
        return DEFAULT;
    }

    public static boolean canReadSchemaVersion(int schemaVersion) {
        return schemaVersion >= 0 && schemaVersion <= CURRENT_SCHEMA_VERSION;
    }

    public double displayValue(EgoAttributeSpec spec) {
        requireDisplaySpec(spec);
        return switch (spec.path()) {
            case "ego_load" -> egoLoad;
            case "readiness" -> readiness;
            case "hydration" -> hydration;
            case "stamina" -> stamina;
            case "fatigue" -> fatigue;
            case "thermal_strain" -> thermalStrain;
            case "respiratory_strain" -> respiratoryStrain;
            case "nutrition_balance" -> nutritionBalance;
            case "stress" -> stress;
            case "focus" -> focus;
            case "comfort" -> comfort;
            case "recovery_capacity" -> recoveryCapacity;
            default -> throw new IllegalArgumentException("Unknown EGO display attribute: " + spec.path());
        };
    }

    public EgoState withDisplayValue(EgoAttributeSpec spec, double value) {
        requireDisplaySpec(spec);
        return switch (spec.path()) {
            case "ego_load" -> new EgoState(schemaVersion, value, readiness, hydration, stamina, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, stress, focus, comfort, recoveryCapacity);
            case "readiness" -> new EgoState(schemaVersion, egoLoad, value, hydration, stamina, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, stress, focus, comfort, recoveryCapacity);
            case "hydration" -> new EgoState(schemaVersion, egoLoad, readiness, value, stamina, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, stress, focus, comfort, recoveryCapacity);
            case "stamina" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, value, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, stress, focus, comfort, recoveryCapacity);
            case "fatigue" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, value, thermalStrain, respiratoryStrain, nutritionBalance, stress, focus, comfort, recoveryCapacity);
            case "thermal_strain" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, fatigue, value, respiratoryStrain, nutritionBalance, stress, focus, comfort, recoveryCapacity);
            case "respiratory_strain" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, fatigue, thermalStrain, value, nutritionBalance, stress, focus, comfort, recoveryCapacity);
            case "nutrition_balance" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, fatigue, thermalStrain, respiratoryStrain, value, stress, focus, comfort, recoveryCapacity);
            case "stress" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, value, focus, comfort, recoveryCapacity);
            case "focus" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, stress, value, comfort, recoveryCapacity);
            case "comfort" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, stress, focus, value, recoveryCapacity);
            case "recovery_capacity" -> new EgoState(schemaVersion, egoLoad, readiness, hydration, stamina, fatigue, thermalStrain, respiratoryStrain, nutritionBalance, stress, focus, comfort, value);
            default -> throw new IllegalArgumentException("Unknown EGO display attribute: " + spec.path());
        };
    }

    private static void requireDisplaySpec(EgoAttributeSpec spec) {
        Objects.requireNonNull(spec, "spec");
        if (spec.role() != EgoAttributeRole.DISPLAY_ONLY) {
            throw new IllegalArgumentException("Attribute is not a display-only EGO mirror: " + spec.path());
        }
    }
}
