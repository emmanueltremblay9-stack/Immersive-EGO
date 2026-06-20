package com.oblixorprime.immersiveego.registry;

import com.oblixorprime.immersiveego.ImmersiveEgo;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EgoAttributes {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ImmersiveEgo.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> STAMINA_CAPACITY = register(EgoAttributeCatalog.STAMINA_CAPACITY);
    public static final DeferredHolder<Attribute, Attribute> HYDRATION_EFFICIENCY = register(EgoAttributeCatalog.HYDRATION_EFFICIENCY);
    public static final DeferredHolder<Attribute, Attribute> RESPIRATION_EFFICIENCY = register(EgoAttributeCatalog.RESPIRATION_EFFICIENCY);
    public static final DeferredHolder<Attribute, Attribute> THERMAL_TOLERANCE = register(EgoAttributeCatalog.THERMAL_TOLERANCE);
    public static final DeferredHolder<Attribute, Attribute> LOAD_TOLERANCE = register(EgoAttributeCatalog.LOAD_TOLERANCE);
    public static final DeferredHolder<Attribute, Attribute> SLEEP_RECOVERY = register(EgoAttributeCatalog.SLEEP_RECOVERY);
    public static final DeferredHolder<Attribute, Attribute> STRESS_RESILIENCE = register(EgoAttributeCatalog.STRESS_RESILIENCE);
    public static final DeferredHolder<Attribute, Attribute> ACCLIMATION_GAIN = register(EgoAttributeCatalog.ACCLIMATION_GAIN);

    public static final DeferredHolder<Attribute, Attribute> EGO_LOAD = register(EgoAttributeCatalog.EGO_LOAD);
    public static final DeferredHolder<Attribute, Attribute> READINESS = register(EgoAttributeCatalog.READINESS);
    public static final DeferredHolder<Attribute, Attribute> HYDRATION = register(EgoAttributeCatalog.HYDRATION);
    public static final DeferredHolder<Attribute, Attribute> STAMINA = register(EgoAttributeCatalog.STAMINA);
    public static final DeferredHolder<Attribute, Attribute> FATIGUE = register(EgoAttributeCatalog.FATIGUE);
    public static final DeferredHolder<Attribute, Attribute> THERMAL_STRAIN = register(EgoAttributeCatalog.THERMAL_STRAIN);
    public static final DeferredHolder<Attribute, Attribute> RESPIRATORY_STRAIN = register(EgoAttributeCatalog.RESPIRATORY_STRAIN);
    public static final DeferredHolder<Attribute, Attribute> NUTRITION_BALANCE = register(EgoAttributeCatalog.NUTRITION_BALANCE);
    public static final DeferredHolder<Attribute, Attribute> STRESS = register(EgoAttributeCatalog.STRESS);
    public static final DeferredHolder<Attribute, Attribute> FOCUS = register(EgoAttributeCatalog.FOCUS);
    public static final DeferredHolder<Attribute, Attribute> COMFORT = register(EgoAttributeCatalog.COMFORT);
    public static final DeferredHolder<Attribute, Attribute> RECOVERY_CAPACITY = register(EgoAttributeCatalog.RECOVERY_CAPACITY);

    private static final java.util.List<DeferredHolder<Attribute, Attribute>> DISPLAY_ATTRIBUTES = java.util.List.of(
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

    private static final java.util.List<DeferredHolder<Attribute, Attribute>> PLAYER_ATTRIBUTES = java.util.List.of(
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

    private EgoAttributes() {
    }

    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(EgoAttributes::addPlayerAttributes);
    }

    public static java.util.List<DeferredHolder<Attribute, Attribute>> playerAttributes() {
        return PLAYER_ATTRIBUTES;
    }

    public static java.util.List<DeferredHolder<Attribute, Attribute>> displayAttributes() {
        return DISPLAY_ATTRIBUTES;
    }

    private static void addPlayerAttributes(EntityAttributeModificationEvent event) {
        PLAYER_ATTRIBUTES.forEach(attribute -> event.add(EntityType.PLAYER, attribute));
    }

    private static DeferredHolder<Attribute, Attribute> register(EgoAttributeSpec spec) {
        return ATTRIBUTES.register(spec.path(), () -> new PercentageAttribute(
                spec.descriptionId(),
                spec.defaultValue(),
                spec.minValue(),
                spec.maxValue())
                .setSyncable(true)
                .setSentiment(toMinecraftSentiment(spec.sentiment())));
    }

    private static Attribute.Sentiment toMinecraftSentiment(EgoAttributeSentiment sentiment) {
        return switch (sentiment) {
            case POSITIVE -> Attribute.Sentiment.POSITIVE;
            case NEGATIVE -> Attribute.Sentiment.NEGATIVE;
        };
    }
}
