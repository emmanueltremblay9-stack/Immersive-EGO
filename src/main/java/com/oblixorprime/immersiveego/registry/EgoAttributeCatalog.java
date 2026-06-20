package com.oblixorprime.immersiveego.registry;

import java.util.List;

public final class EgoAttributeCatalog {
    public static final EgoAttributeSpec STAMINA_CAPACITY = capability("stamina_capacity");
    public static final EgoAttributeSpec HYDRATION_EFFICIENCY = capability("hydration_efficiency");
    public static final EgoAttributeSpec RESPIRATION_EFFICIENCY = capability("respiration_efficiency");
    public static final EgoAttributeSpec THERMAL_TOLERANCE = capability("thermal_tolerance");
    public static final EgoAttributeSpec LOAD_TOLERANCE = capability("load_tolerance");
    public static final EgoAttributeSpec SLEEP_RECOVERY = capability("sleep_recovery");
    public static final EgoAttributeSpec STRESS_RESILIENCE = capability("stress_resilience");
    public static final EgoAttributeSpec ACCLIMATION_GAIN = capability("acclimation_gain");

    public static final EgoAttributeSpec EGO_LOAD = display("ego_load", 0.0D, EgoAttributeSentiment.NEGATIVE);
    public static final EgoAttributeSpec READINESS = display("readiness", 1.0D, EgoAttributeSentiment.POSITIVE);
    public static final EgoAttributeSpec HYDRATION = display("hydration", 1.0D, EgoAttributeSentiment.POSITIVE);
    public static final EgoAttributeSpec STAMINA = display("stamina", 1.0D, EgoAttributeSentiment.POSITIVE);
    public static final EgoAttributeSpec FATIGUE = display("fatigue", 0.0D, EgoAttributeSentiment.NEGATIVE);
    public static final EgoAttributeSpec THERMAL_STRAIN = display("thermal_strain", 0.0D, EgoAttributeSentiment.NEGATIVE);
    public static final EgoAttributeSpec RESPIRATORY_STRAIN = display("respiratory_strain", 0.0D, EgoAttributeSentiment.NEGATIVE);
    public static final EgoAttributeSpec NUTRITION_BALANCE = display("nutrition_balance", 1.0D, EgoAttributeSentiment.POSITIVE);
    public static final EgoAttributeSpec STRESS = display("stress", 0.0D, EgoAttributeSentiment.NEGATIVE);
    public static final EgoAttributeSpec FOCUS = display("focus", 1.0D, EgoAttributeSentiment.POSITIVE);
    public static final EgoAttributeSpec COMFORT = display("comfort", 1.0D, EgoAttributeSentiment.POSITIVE);
    public static final EgoAttributeSpec RECOVERY_CAPACITY = display("recovery_capacity", 1.0D, EgoAttributeSentiment.POSITIVE);

    private static final List<EgoAttributeSpec> SPECS = List.of(
            STAMINA_CAPACITY,
            HYDRATION_EFFICIENCY,
            RESPIRATION_EFFICIENCY,
            THERMAL_TOLERANCE,
            LOAD_TOLERANCE,
            SLEEP_RECOVERY,
            STRESS_RESILIENCE,
            ACCLIMATION_GAIN,
            EGO_LOAD,
            READINESS,
            HYDRATION,
            STAMINA,
            FATIGUE,
            THERMAL_STRAIN,
            RESPIRATORY_STRAIN,
            NUTRITION_BALANCE,
            STRESS,
            FOCUS,
            COMFORT,
            RECOVERY_CAPACITY);

    private EgoAttributeCatalog() {
    }

    public static List<EgoAttributeSpec> specs() {
        return SPECS;
    }

    public static List<EgoAttributeSpec> displayOnlySpecs() {
        return SPECS.stream()
                .filter(spec -> spec.role() == EgoAttributeRole.DISPLAY_ONLY)
                .toList();
    }

    public static List<EgoAttributeSpec> capabilitySpecs() {
        return SPECS.stream()
                .filter(spec -> spec.role() == EgoAttributeRole.CAPABILITY)
                .toList();
    }

    private static EgoAttributeSpec capability(String path) {
        return new EgoAttributeSpec(path, EgoAttributeRole.CAPABILITY, 1.0D, 0.0D, 4.0D, EgoAttributeSentiment.POSITIVE);
    }

    private static EgoAttributeSpec display(String path, double defaultValue, EgoAttributeSentiment sentiment) {
        return new EgoAttributeSpec(path, EgoAttributeRole.DISPLAY_ONLY, defaultValue, 0.0D, 1.0D, sentiment);
    }
}
