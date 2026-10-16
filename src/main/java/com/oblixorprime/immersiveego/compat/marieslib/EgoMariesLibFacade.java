package com.oblixorprime.immersiveego.compat.marieslib;

import com.mojang.logging.LogUtils;
import com.oblixorprime.immersiveego.nutrition.EgoDietaryMemory;
import com.oblixorprime.immersiveego.nutrition.EgoFoodNutritionProfile;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionGroup;
import com.oblixorprime.immersiveego.nutrition.EgoVanillaFoodNutritionCatalog;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import dev.marie.MariesLib.api.ApplicationHistoryView;
import dev.marie.MariesLib.api.MarieAPI;
import dev.marie.MariesLib.api.MariePlayerData;
import dev.marie.MariesLib.api.ThresholdEffect;
import dev.marie.MariesLib.api.ValueDefinition;
import dev.marie.MariesLib.api.ValueSourceTrigger;
import dev.marie.MariesLib.core.MarieLibContext;
import dev.marie.MariesLib.core.MarieLibRegistrationDelegate;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;

public final class EgoMariesLibFacade {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String SCANNER_SPEC_JSON = """
            {
              "multipliers": {
                "community_tag": 5.0,
                "namespace": 4.0,
                "suffix": 3.0,
                "keyword": 2.0,
                "archetype": 2.0,
                "recipe_inheritance": 1.0,
                "namespace_peer": 0.5,
                "secondary_suffix": 0.5,
                "namespace_peer_average_weight": 0.5
              }
            }
            """;
    private static final EgoRegistrationDelegate REGISTRATION_DELEGATE = new EgoRegistrationDelegate();

    private static boolean attached;
    private static boolean nutritionRegistered;

    private EgoMariesLibFacade() {
    }

    public static synchronized void attach(String modId, IEventBus modEventBus) {
        Objects.requireNonNull(modId, "modId");
        Objects.requireNonNull(modEventBus, "modEventBus");
        if (attached) {
            return;
        }
        ensureScannerSpecConfig(modId);
        MarieLibContext.register(MarieLibContext.builder(modId)
                .registrationDelegate(REGISTRATION_DELEGATE)
                .sourceValueResolver((stack, level) -> REGISTRATION_DELEGATE.valuesFor(stack))
                .build());
        attached = true;
    }

    public static synchronized void registerNutritionValuesAndSources() {
        if (!attached) {
            throw new IllegalStateException("MariesLib facade must be attached before nutrition registration");
        }
        if (nutritionRegistered) {
            return;
        }
        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            MarieAPI.registerValue(ValueDefinition.builder(group.valueKey())
                    .displayName(group.displayName())
                    .color(group.color())
                    .defaultDecayRate(0.00025F)
                    .criticalThreshold(0.20F)
                    .lowThreshold(0.40F)
                    .excessThreshold(0.95F)
                    .beneficial(true)
                    .amountScale(1.0D)
                    .build());
        }
        for (EgoFoodNutritionProfile profile : EgoVanillaFoodNutritionCatalog.defaults()) {
            ResourceLocation itemId = ResourceLocation.fromNamespaceAndPath("minecraft", profile.itemPath());
            for (Map.Entry<EgoNutritionGroup, Double> entry : profile.groupAmounts().entrySet()) {
                MarieAPI.registerSourceClassification(
                        itemId,
                        entry.getKey().valueKey(),
                        entry.getValue().floatValue());
            }
        }
        nutritionRegistered = true;
    }

    public static List<String> nutritionValueKeys() {
        return EgoNutritionGroup.all().stream()
                .map(EgoNutritionGroup::valueKey)
                .toList();
    }

    private static void ensureScannerSpecConfig(String modId) {
        Path configPath = FMLPaths.CONFIGDIR.get()
                .resolve(modId)
                .resolve("scanner_spec.json");
        try {
            Files.createDirectories(configPath.getParent());
            Files.writeString(
                    configPath,
                    SCANNER_SPEC_JSON,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW);
            LOGGER.info("Created MariesLib scanner spec config at {}", configPath);
        } catch (FileAlreadyExistsException ignored) {
            // Existing user config wins.
        } catch (IOException exception) {
            LOGGER.warn("Unable to prepare MariesLib scanner spec config at {}", configPath, exception);
        }
    }

    public static void fireFoodConsumed(ServerPlayer player, ItemStack stack) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(stack, "stack");
        if (stack.isEmpty()) {
            return;
        }
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        MarieAPI.fireSourceTrigger(player, ValueSourceTrigger.itemConsumed(itemId, 1.0D), stack);
    }

    public static EgoDietaryMemory readDietaryMemory(ServerPlayer player) {
        Objects.requireNonNull(player, "player");
        MariePlayerData data = MarieAPI.getTrackingData(player);
        if (data == null) {
            return EgoDietaryMemory.empty();
        }

        EnumMap<EgoNutritionGroup, Double> levels = new EnumMap<>(EgoNutritionGroup.class);
        Map<String, Float> values = data.values();
        for (EgoNutritionGroup group : EgoNutritionGroup.all()) {
            Float value = values == null ? null : values.get(group.valueKey());
            levels.put(group, EgoDietaryMemory.sanitizeNormalized(value));
        }

        ApplicationHistoryView history = data.sourceMemory();
        List<ResourceLocation> recentSources = history == null ? List.of() : history.getRecentSources();
        if (recentSources == null) {
            recentSources = List.of();
        }
        Set<ResourceLocation> uniqueSources = recentSources.stream().collect(Collectors.toSet());
        double varietyScore = EgoMath.clamp01(uniqueSources.size() / 8.0D);
        return new EgoDietaryMemory(levels, recentSources.size(), varietyScore);
    }

    private static final class EgoRegistrationDelegate implements MarieLibRegistrationDelegate {
        private final Map<String, ValueDefinition> valueDefinitions = new LinkedHashMap<>();
        private final Map<ResourceLocation, Map<String, Float>> sourceClassifications = new LinkedHashMap<>();

        @Override
        public List<String> getValueKeys() {
            return List.copyOf(valueDefinitions.keySet());
        }

        @Override
        public void registerValue(ValueDefinition definition) {
            Objects.requireNonNull(definition, "definition");
            valueDefinitions.put(definition.getId(), definition);
        }

        @Override
        public void registerEffect(ThresholdEffect effect) {
            Objects.requireNonNull(effect, "effect");
        }

        @Override
        public void registerSourceClassification(ResourceLocation itemId, String valueKey, float amount) {
            Objects.requireNonNull(itemId, "itemId");
            Objects.requireNonNull(valueKey, "valueKey");
            if (!Float.isFinite(amount)) {
                throw new IllegalArgumentException("amount must be finite");
            }
            sourceClassifications
                    .computeIfAbsent(itemId, ignored -> new LinkedHashMap<>())
                    .put(valueKey, amount);
        }

        @Override
        public ValueDefinition valueDefinitionFor(String valueKey) {
            return valueDefinitions.get(valueKey);
        }

        private Map<String, Float> valuesFor(ItemStack stack) {
            if (stack == null || stack.isEmpty()) {
                return Map.of();
            }
            Map<String, Float> values = sourceClassifications.get(BuiltInRegistries.ITEM.getKey(stack.getItem()));
            return values == null || values.isEmpty()
                    ? Map.of()
                    : Map.copyOf(values);
        }
    }
}
