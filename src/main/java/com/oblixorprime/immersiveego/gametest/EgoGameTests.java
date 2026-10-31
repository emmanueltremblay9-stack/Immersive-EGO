package com.oblixorprime.immersiveego.gametest;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.tree.CommandNode;
import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.compat.marieslib.EgoMariesLibFacade;
import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoader;
import com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoader;
import com.oblixorprime.immersiveego.config.EgoServerModuleConfigs;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.nutrition.EgoDietaryMemory;
import com.oblixorprime.immersiveego.nutrition.EgoFoodNutritionProfile;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionContext;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionGroup;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionModule;
import com.oblixorprime.immersiveego.nutrition.EgoNutritionService;
import com.oblixorprime.immersiveego.nutrition.EgoVanillaFoodNutritionCatalog;
import com.oblixorprime.immersiveego.origins.EgoOriginState;
import com.oblixorprime.immersiveego.physiology.EgoHydrationDemandContext;
import com.oblixorprime.immersiveego.physiology.EgoHydrationService;
import com.oblixorprime.immersiveego.physiology.EgoDrinkItemResolver;
import com.oblixorprime.immersiveego.physiology.EgoRespirationService;
import com.oblixorprime.immersiveego.physiology.EgoRespiratoryDimension;
import com.oblixorprime.immersiveego.physiology.EgoRespiratorySample;
import com.oblixorprime.immersiveego.physiology.EgoRespiratorySampler;
import com.oblixorprime.immersiveego.physiology.EgoStaminaActivityContext;
import com.oblixorprime.immersiveego.physiology.EgoStaminaService;
import com.oblixorprime.immersiveego.physiology.EgoWaterPurificationItemResolver;
import com.oblixorprime.immersiveego.psychology.EgoInjuryMemory;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyContext;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyModule;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyRuntime;
import com.oblixorprime.immersiveego.psychology.EgoPsychologyService;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeRole;
import com.oblixorprime.immersiveego.registry.EgoAttributeSentiment;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorService;
import com.oblixorprime.immersiveego.sleep.EgoSleepContext;
import com.oblixorprime.immersiveego.sleep.EgoSleepService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.neoforged.neoforge.registries.DeferredHolder;

@GameTestHolder(ImmersiveEgo.MOD_ID)
@PrefixGameTestTemplate(false)
public final class EgoGameTests {
    private static final String EMPTY = "empty";

    private EgoGameTests() {
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void player_attribute_supplier_contains_every_ego_attribute(GameTestHelper helper) {
        AttributeSupplier playerAttributes = DefaultAttributes.getSupplier(EntityType.PLAYER);
        List<String> missing = new ArrayList<>();

        for (DeferredHolder<Attribute, Attribute> attribute : EgoAttributes.playerAttributes()) {
            if (!playerAttributes.hasAttribute(attribute)) {
                missing.add(attribute.getId().toString());
            }
        }

        if (!missing.isEmpty()) {
            helper.fail("Missing EGO player attributes: " + String.join(", ", missing));
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void attribute_catalog_paths_are_unique_bounded_and_sentimented(GameTestHelper helper) {
        Set<String> paths = new HashSet<>();

        for (EgoAttributeSpec spec : EgoAttributeCatalog.specs()) {
            if (!paths.add(spec.path())) {
                helper.fail("Duplicate EGO attribute path: " + spec.path());
                return;
            }
            if (spec.minValue() > spec.maxValue()
                    || spec.defaultValue() < spec.minValue()
                    || spec.defaultValue() > spec.maxValue()) {
                helper.fail("Invalid bounds/default for EGO attribute: " + spec.path());
                return;
            }
            if (spec.role() == EgoAttributeRole.DISPLAY_ONLY
                    && spec.sentiment() != EgoAttributeSentiment.POSITIVE
                    && spec.sentiment() != EgoAttributeSentiment.NEGATIVE) {
                helper.fail("Display EGO attribute must declare positive or negative sentiment: " + spec.path());
                return;
            }
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void mirror_service_updates_display_attributes_without_duplicate_modifiers(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.42D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.35D);

        player.setData(EgoAttachments.EGO_STATE.get(), state);
        EgoAttributeMirrorService.updatePlayerMirrors(player);
        EgoAttributeMirrorService.updatePlayerMirrors(player);

        assertMirror(helper, player, EgoAttributeCatalog.HYDRATION, 0.42D);
        assertMirror(helper, player, EgoAttributeCatalog.FATIGUE, 0.35D);

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void server_registers_ego_status_and_debug_state_commands(GameTestHelper helper) {
        CommandNode<CommandSourceStack> ego = helper.getLevel()
                .getServer()
                .getCommands()
                .getDispatcher()
                .getRoot()
                .getChild("ego");

        if (ego == null) {
            helper.fail("Missing /ego command root");
            return;
        }

        if (ego.getChild("status") == null) {
            helper.fail("Missing /ego status command");
            return;
        }

        CommandNode<CommandSourceStack> debug = ego.getChild("debug");
        if (debug == null || debug.getChild("state") == null) {
            helper.fail("Missing /ego debug state command");
            return;
        }

        if (debug.getChild("environment") == null) {
            helper.fail("Missing /ego debug environment command");
            return;
        }

        if (debug.getChild("origin") == null) {
            helper.fail("Missing /ego debug origin command");
            return;
        }

        CommandNode<CommandSourceStack> synergy = ego.getChild("synergy");
        if (synergy == null || synergy.getChild("trace") == null) {
            helper.fail("Missing /ego synergy trace command");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void debug_and_synergy_commands_require_permission_level_two(GameTestHelper helper) {
        CommandNode<CommandSourceStack> ego = helper.getLevel()
                .getServer()
                .getCommands()
                .getDispatcher()
                .getRoot()
                .getChild("ego");
        if (ego == null) {
            helper.fail("Missing /ego command root");
            return;
        }

        CommandNode<CommandSourceStack> debug = ego.getChild("debug");
        if (!requiresPermissionLevelTwo(helper, debug, "ego debug")) {
            return;
        }

        CommandNode<CommandSourceStack> synergy = ego.getChild("synergy");
        if (!requiresPermissionLevelTwo(helper, synergy, "ego synergy")) {
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void drink_item_resolver_recognizes_only_vanilla_drinks(GameTestHelper helper) {
        if (!assertDrinkRecognized(helper, PotionContents.createItemStack(Items.POTION, Potions.WATER), "water potion")) {
            return;
        }
        if (!assertDrinkRecognized(helper, new ItemStack(Items.MILK_BUCKET), "milk bucket")) {
            return;
        }
        if (!assertDrinkRecognized(helper, new ItemStack(Items.HONEY_BOTTLE), "honey bottle")) {
            return;
        }

        if (EgoDrinkItemResolver.resolve(PotionContents.createItemStack(Items.POTION, Potions.AWKWARD)).isPresent()) {
            helper.fail("Awkward potion must not count as hydration");
            return;
        }
        if (EgoDrinkItemResolver.resolve(PotionContents.createItemStack(Items.SPLASH_POTION, Potions.WATER)).isPresent()) {
            helper.fail("Splash water potion must not count as direct drinking");
            return;
        }
        if (EgoWaterPurificationItemResolver.resolve(new ItemStack(Items.CHARCOAL)).isEmpty()) {
            helper.fail("Charcoal must count as a water purification item");
            return;
        }
        if (EgoWaterPurificationItemResolver.resolve(new ItemStack(Items.CAMPFIRE)).isEmpty()) {
            helper.fail("Campfire must count as a water purification item");
            return;
        }
        if (EgoWaterPurificationItemResolver.resolve(new ItemStack(Items.DIRT)).isPresent()) {
            helper.fail("Dirt must not count as a water purification item");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void minecraft_sleep_api_matches_phantom_rest_model(GameTestHelper helper) {
        if (Player.SLEEP_DURATION != EgoSleepContext.PHANTOM_REST_QUALIFYING_TICKS) {
            helper.fail("Expected vanilla player sleep duration to remain "
                    + EgoSleepContext.PHANTOM_REST_QUALIFYING_TICKS
                    + " ticks but was "
                    + Player.SLEEP_DURATION);
            return;
        }
        if (!"time_since_rest".equals(Stats.TIME_SINCE_REST.getPath())) {
            helper.fail("Unexpected TIME_SINCE_REST stat path: " + Stats.TIME_SINCE_REST);
            return;
        }
        if (Stats.CUSTOM.get(Stats.TIME_SINCE_REST) == null) {
            helper.fail("Missing vanilla custom TIME_SINCE_REST stat");
            return;
        }
        if (EgoSleepContext.PHANTOM_SPAWN_RESTLESS_TICKS != 72000) {
            helper.fail("Expected phantom spawn restless threshold to remain 72000 ticks");
            return;
        }
        EgoSleepContext rested = new EgoSleepContext(
                18000L,
                true,
                Player.SLEEP_DURATION,
                0,
                20,
                1.0D);
        EgoSleepContext restless = new EgoSleepContext(
                18000L,
                false,
                0,
                EgoSleepContext.PHANTOM_SPAWN_RESTLESS_TICKS,
                20,
                1.0D);
        if (!rested.phantomRestQualified()) {
            helper.fail("Expected sleep context to qualify phantom rest after vanilla sleep duration");
            return;
        }
        if (!restless.phantomSpawnEligible()) {
            helper.fail("Expected restless context to be phantom-spawn eligible at vanilla threshold");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void vanilla_food_catalog_exposes_six_marieslib_nutrition_groups(GameTestHelper helper) {
        if (EgoNutritionGroup.all().size() != 6) {
            helper.fail("Expected six EGO nutrition groups");
            return;
        }
        if (EgoVanillaFoodNutritionCatalog.defaultByItemPath("bread")
                .filter(profile -> profile.amount(EgoNutritionGroup.GRAINS) > 0.0D)
                .isEmpty()) {
            helper.fail("Bread must classify as grains");
            return;
        }
        if (EgoVanillaFoodNutritionCatalog.defaultByItemPath("cooked_beef")
                .filter(profile -> profile.amount(EgoNutritionGroup.PROTEIN) > 0.0D)
                .isEmpty()) {
            helper.fail("Cooked beef must classify as protein");
            return;
        }
        if (EgoVanillaFoodNutritionCatalog.defaultByItemPath("egg").isPresent()) {
            helper.fail("Egg is not a vanilla edible item and must not be classified as consumed food");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void origin_state_attachment_defaults_to_unassigned(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        if (!"unassigned".equals(player.getData(EgoAttachments.EGO_ORIGIN_STATE.get()).originId())) {
            helper.fail("Expected new players to start with an unassigned EGO origin");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void injury_memory_attachment_defaults_to_clear(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        if (!player.getData(EgoAttachments.EGO_INJURY_MEMORY.get()).clear()) {
            helper.fail("Expected new players to start with clear EGO injury memory");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void state_origin_and_injury_attachments_round_trip_on_mock_player(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState expectedState = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.33D)
                .withDisplayValue(EgoAttributeCatalog.STRESS, 0.44D)
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.55D);
        EgoOriginState expectedOrigin = new EgoOriginState(
                EgoOriginState.CURRENT_SCHEMA_VERSION,
                "highland",
                0.20D,
                0.30D,
                0.40D,
                0.10D);
        EgoInjuryMemory expectedInjury = new EgoInjuryMemory(
                EgoInjuryMemory.CURRENT_SCHEMA_VERSION,
                0.15D,
                0.25D,
                0.35D);

        player.setData(EgoAttachments.EGO_STATE.get(), expectedState);
        player.setData(EgoAttachments.EGO_ORIGIN_STATE.get(), expectedOrigin);
        player.setData(EgoAttachments.EGO_INJURY_MEMORY.get(), expectedInjury);

        if (!expectedState.equals(player.getData(EgoAttachments.EGO_STATE.get()))) {
            helper.fail("Expected EGO state attachment to round-trip mutated server values");
            return;
        }
        if (!expectedOrigin.equals(player.getData(EgoAttachments.EGO_ORIGIN_STATE.get()))) {
            helper.fail("Expected EGO origin attachment to round-trip mutated server values");
            return;
        }
        if (!expectedInjury.equals(player.getData(EgoAttachments.EGO_INJURY_MEMORY.get()))) {
            helper.fail("Expected EGO injury attachment to round-trip mutated server values");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void state_origin_and_injury_attachments_survive_player_nbt_round_trip(GameTestHelper helper) {
        Player saved = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState expectedState = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.27D)
                .withDisplayValue(EgoAttributeCatalog.STRESS, 0.38D)
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.49D);
        EgoOriginState expectedOrigin = new EgoOriginState(
                EgoOriginState.CURRENT_SCHEMA_VERSION,
                "humid",
                0.21D,
                0.12D,
                0.33D,
                0.44D);
        EgoInjuryMemory expectedInjury = new EgoInjuryMemory(
                EgoInjuryMemory.CURRENT_SCHEMA_VERSION,
                0.16D,
                0.26D,
                0.36D);

        saved.setData(EgoAttachments.EGO_STATE.get(), expectedState);
        saved.setData(EgoAttachments.EGO_ORIGIN_STATE.get(), expectedOrigin);
        saved.setData(EgoAttachments.EGO_INJURY_MEMORY.get(), expectedInjury);

        CompoundTag savedTag = new CompoundTag();
        saved.saveWithoutId(savedTag);
        if (!savedTag.contains("neoforge:attachments")) {
            helper.fail("Expected saved player NBT to contain NeoForge attachment data");
            return;
        }

        CompoundTag attachments = savedTag.getCompound("neoforge:attachments");
        if (!attachments.contains("immersive_ego:ego_state")) {
            helper.fail("Expected saved player NBT to contain EGO state attachment data");
            return;
        }
        if (!attachments.contains("immersive_ego:ego_origin_state")) {
            helper.fail("Expected saved player NBT to contain EGO origin attachment data");
            return;
        }
        if (!attachments.contains("immersive_ego:ego_injury_memory")) {
            helper.fail("Expected saved player NBT to contain EGO injury attachment data");
            return;
        }

        Player loaded = helper.makeMockPlayer(GameType.SURVIVAL);
        loaded.load(savedTag);
        if (!expectedState.equals(loaded.getData(EgoAttachments.EGO_STATE.get()))) {
            helper.fail("Expected EGO state attachment to survive player NBT save/load");
            return;
        }
        if (!expectedOrigin.equals(loaded.getData(EgoAttachments.EGO_ORIGIN_STATE.get()))) {
            helper.fail("Expected EGO origin attachment to survive player NBT save/load");
            return;
        }
        if (!expectedInjury.equals(loaded.getData(EgoAttachments.EGO_INJURY_MEMORY.get()))) {
            helper.fail("Expected EGO injury attachment to survive player NBT save/load");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void state_origin_and_injury_attachments_copy_on_death_clone_event(GameTestHelper helper) {
        Player original = helper.makeMockPlayer(GameType.SURVIVAL);
        Player respawned = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState expectedState = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.18D)
                .withDisplayValue(EgoAttributeCatalog.STRESS, 0.61D)
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.72D);
        EgoOriginState expectedOrigin = new EgoOriginState(
                EgoOriginState.CURRENT_SCHEMA_VERSION,
                "arid",
                0.64D,
                0.11D,
                0.22D,
                0.33D);
        EgoInjuryMemory expectedInjury = new EgoInjuryMemory(
                EgoInjuryMemory.CURRENT_SCHEMA_VERSION,
                0.41D,
                0.52D,
                0.63D);

        original.setData(EgoAttachments.EGO_STATE.get(), expectedState);
        original.setData(EgoAttachments.EGO_ORIGIN_STATE.get(), expectedOrigin);
        original.setData(EgoAttachments.EGO_INJURY_MEMORY.get(), expectedInjury);

        NeoForge.EVENT_BUS.post(new PlayerEvent.Clone(respawned, original, true));
        if (!expectedState.equals(respawned.getData(EgoAttachments.EGO_STATE.get()))) {
            helper.fail("Expected EGO state attachment to copy across a death respawn clone event");
            return;
        }
        if (!expectedOrigin.equals(respawned.getData(EgoAttachments.EGO_ORIGIN_STATE.get()))) {
            helper.fail("Expected EGO origin attachment to copy across a death respawn clone event");
            return;
        }
        if (!expectedInjury.equals(respawned.getData(EgoAttachments.EGO_INJURY_MEMORY.get()))) {
            helper.fail("Expected EGO injury attachment to copy across a death respawn clone event");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void hydration_progression_reduces_server_authoritative_state(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState before = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.HYDRATION, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, 0.70D)
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, 0.60D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.40D);

        player.setData(EgoAttachments.EGO_STATE.get(), before);
        EgoState after = EgoHydrationService.applyDemand(
                player.getData(EgoAttachments.EGO_STATE.get()),
                new EgoHydrationDemandContext(200, 1.0D));
        player.setData(EgoAttachments.EGO_STATE.get(), after);

        if (!(player.getData(EgoAttachments.EGO_STATE.get()).hydration() < before.hydration())) {
            helper.fail("Expected hydration demand to reduce persisted server-authoritative hydration");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void sleep_progression_recovers_server_authoritative_state(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState before = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.FOCUS, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.COMFORT, 0.50D);

        player.setData(EgoAttachments.EGO_STATE.get(), before);
        EgoState after = EgoSleepService.applySleep(
                player.getData(EgoAttachments.EGO_STATE.get()),
                new EgoSleepContext(18000L, true, 200, 20, 1.0D));
        player.setData(EgoAttachments.EGO_STATE.get(), after);
        EgoState persisted = player.getData(EgoAttachments.EGO_STATE.get());

        if (!(persisted.fatigue() < before.fatigue())) {
            helper.fail("Expected night sleep to reduce persisted server-authoritative fatigue");
            return;
        }
        if (!(persisted.recoveryCapacity() > before.recoveryCapacity()
                && persisted.focus() > before.focus()
                && persisted.comfort() > before.comfort())) {
            helper.fail("Expected night sleep to improve persisted recovery, focus, and comfort");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void nutrition_progression_from_vanilla_food_updates_server_state(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState before = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.NUTRITION_BALANCE, 0.10D);
        EgoFoodNutritionProfile bread = EgoVanillaFoodNutritionCatalog.defaultByItemPath("bread")
                .orElseThrow();
        EgoDietaryMemory memory = new EgoDietaryMemory(
                Map.copyOf(bread.groupAmounts()),
                1,
                0.20D);

        player.setData(EgoAttachments.EGO_STATE.get(), before);
        EgoState after = EgoNutritionService.applyNutrition(
                player.getData(EgoAttachments.EGO_STATE.get()),
                EgoNutritionContext.fromMemory(20, memory));
        player.setData(EgoAttachments.EGO_STATE.get(), after);

        if (!(player.getData(EgoAttachments.EGO_STATE.get()).nutritionBalance() > before.nutritionBalance())) {
            helper.fail("Expected vanilla food nutrition memory to raise persisted nutrition balance");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void food_consumption_event_updates_marieslib_memory_and_server_state(GameTestHelper helper) {
        ServerPlayer player = new ServerPlayer(
                helper.getLevel().getServer(),
                helper.getLevel(),
                new GameProfile(UUID.randomUUID(), "ego-nutrition-test"),
                ClientInformation.createDefault());

        EgoServerModuleConfigs.ModuleConfig nutritionConfig = EgoServerModuleConfigs
                .byKey(EgoNutritionModule.NUTRITION_MODULE_KEY)
                .orElseThrow();
        boolean previousCoreEnabled = EgoCoreConfig.ENABLE_SIMULATION.get();
        boolean previousNutritionEnabled = nutritionConfig.enabled().get();
        try {
            EgoCoreConfig.ENABLE_SIMULATION.set(true);
            nutritionConfig.enabled().set(true);

            EgoState before = EgoState.defaultState()
                    .withDisplayValue(EgoAttributeCatalog.NUTRITION_BALANCE, 0.10D);
            player.setData(EgoAttachments.EGO_STATE.get(), before);

            ItemStack bread = new ItemStack(Items.BREAD);
            NeoForge.EVENT_BUS.post(new LivingEntityUseItemEvent.Finish(player, bread, 0, ItemStack.EMPTY));

            EgoDietaryMemory memory = EgoMariesLibFacade.readDietaryMemory(player);
            if (memory.recentSourceCount() < 1) {
                helper.fail("Expected MariesLib dietary memory to record consumed bread");
                return;
            }
            if (!(memory.level(EgoNutritionGroup.GRAINS) > 0.0D)) {
                helper.fail("Expected consumed bread to raise MariesLib grains memory");
                return;
            }
            if (!(player.getData(EgoAttachments.EGO_STATE.get()).nutritionBalance() > before.nutritionBalance())) {
                helper.fail("Expected food-consumption event to raise persisted nutrition balance");
                return;
            }

            helper.succeed();
        } finally {
            nutritionConfig.enabled().set(previousNutritionEnabled);
            EgoCoreConfig.ENABLE_SIMULATION.set(previousCoreEnabled);
        }
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void psychology_runtime_records_injury_and_updates_server_state(GameTestHelper helper) {
        ServerPlayer player = new ServerPlayer(
                helper.getLevel().getServer(),
                helper.getLevel(),
                new GameProfile(UUID.randomUUID(), "ego-psychology-test"),
                ClientInformation.createDefault());

        EgoServerModuleConfigs.ModuleConfig psychologyConfig = EgoServerModuleConfigs
                .byKey(EgoPsychologyModule.PSYCHOLOGY_MODULE_KEY)
                .orElseThrow();
        boolean previousCoreEnabled = EgoCoreConfig.ENABLE_SIMULATION.get();
        boolean previousPsychologyEnabled = psychologyConfig.enabled().get();
        try {
            EgoCoreConfig.ENABLE_SIMULATION.set(true);
            psychologyConfig.enabled().set(true);

            EgoState before = EgoState.defaultState()
                    .withDisplayValue(EgoAttributeCatalog.STRESS, 0.05D)
                    .withDisplayValue(EgoAttributeCatalog.FOCUS, 0.80D)
                    .withDisplayValue(EgoAttributeCatalog.COMFORT, 0.80D)
                    .withDisplayValue(EgoAttributeCatalog.RECOVERY_CAPACITY, 0.80D);
            player.setData(EgoAttachments.EGO_STATE.get(), before);
            player.setHealth(8.0F);

            EgoPsychologyContext context = EgoPsychologyRuntime.context(player, before, 20)
                    .orElse(null);
            if (context == null) {
                helper.fail("Expected enabled psychology runtime to produce a context");
                return;
            }

            EgoInjuryMemory injuryMemory = player.getData(EgoAttachments.EGO_INJURY_MEMORY.get());
            if (!(injuryMemory.recentInjury() > 0.0D
                    && injuryMemory.lingeringPain() > 0.0D
                    && injuryMemory.recoveryDebt() > 0.0D)) {
                helper.fail("Expected psychology runtime to persist injury memory from live health deficit");
                return;
            }
            if (!(injuryMemory.painPressure(0.0D) > 0.0D)) {
                helper.fail("Expected persisted injury memory to carry lingering pain pressure after health recovery");
                return;
            }

            EgoState after = EgoPsychologyService.applyPsychology(before, context);
            player.setData(EgoAttachments.EGO_STATE.get(), after);
            EgoState persisted = player.getData(EgoAttachments.EGO_STATE.get());

            if (!(persisted.stress() > before.stress())) {
                helper.fail("Expected pain/fear pressure to raise persisted server-authoritative stress");
                return;
            }
            if (!(persisted.focus() < before.focus()
                    && persisted.comfort() < before.comfort()
                    && persisted.recoveryCapacity() < before.recoveryCapacity())) {
                helper.fail("Expected pain/fear pressure to reduce focus, comfort, and recovery capacity");
                return;
            }

            helper.succeed();
        } finally {
            psychologyConfig.enabled().set(previousPsychologyEnabled);
            EgoCoreConfig.ENABLE_SIMULATION.set(previousCoreEnabled);
        }
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void respiratory_dimension_mapping_updates_server_state(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ResourceKey<Level> otherDimension = ResourceKey.create(
                Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath(ImmersiveEgo.MOD_ID, "thin_air"));

        if (EgoRespiratorySampler.dimensionFrom(Level.OVERWORLD) != EgoRespiratoryDimension.OVERWORLD) {
            helper.fail("Expected Overworld dimension key to classify as OVERWORLD");
            return;
        }
        if (EgoRespiratorySampler.dimensionFrom(Level.NETHER) != EgoRespiratoryDimension.NETHER) {
            helper.fail("Expected Nether dimension key to classify as NETHER");
            return;
        }
        if (EgoRespiratorySampler.dimensionFrom(Level.END) != EgoRespiratoryDimension.END) {
            helper.fail("Expected End dimension key to classify as END");
            return;
        }
        if (EgoRespiratorySampler.dimensionFrom(otherDimension) != EgoRespiratoryDimension.OTHER) {
            helper.fail("Expected non-vanilla dimension key to classify as OTHER");
            return;
        }

        EgoState baseline = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.RESPIRATORY_STRAIN, 0.0D);
        player.setData(EgoAttachments.EGO_STATE.get(), baseline);

        EgoState afterNether = EgoRespirationService.applySample(
                player.getData(EgoAttachments.EGO_STATE.get()),
                new EgoRespiratorySample(0.0D, 0.0D, 0.0D, 0.35D, 1.0D));
        player.setData(EgoAttachments.EGO_STATE.get(), afterNether);
        double netherStrain = player.getData(EgoAttachments.EGO_STATE.get()).respiratoryStrain();

        EgoState afterEnd = EgoRespirationService.applySample(
                baseline,
                new EgoRespiratorySample(0.0D, 0.0D, 0.0D, 0.25D, 1.0D));
        EgoState afterOther = EgoRespirationService.applySample(
                baseline,
                new EgoRespiratorySample(0.0D, 0.0D, 0.0D, 0.15D, 1.0D));

        if (!(netherStrain > baseline.respiratoryStrain())) {
            helper.fail("Expected Nether dimension pressure to increase persisted respiratory strain");
            return;
        }
        if (!(afterEnd.respiratoryStrain() > afterOther.respiratoryStrain()
                && afterEnd.respiratoryStrain() < netherStrain
                && afterOther.respiratoryStrain() > baseline.respiratoryStrain())) {
            helper.fail("Expected dimension respiratory strain ordering Nether > End > Other > Overworld");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void malformed_dynamic_configs_repair_to_safe_defaults(GameTestHelper helper) {
        Path temporaryRoot = null;
        try {
            temporaryRoot = Files.createTempDirectory("immersive-ego-gametest-config");
            Path configDirectory = temporaryRoot.resolve(ImmersiveEgo.MOD_ID);
            Files.createDirectories(configDirectory);
            Files.writeString(configDirectory.resolve(EgoDynamicServerConfigLoader.FILE_NAME), """
                    [scheduler
                    intervalTicks = "fast"
                    """);
            Files.writeString(configDirectory.resolve(EgoDynamicSynergyConfigLoader.FILE_NAME), """
                    [synergies
                    edges = ["fatigue -> stress : 0.25"
                    """);

            EgoDynamicServerConfig serverConfig = EgoDynamicServerConfigLoader.load(temporaryRoot);
            if (serverConfig.schedulerEnabled()) {
                helper.fail("Expected malformed simulation config to repair scheduler.enabled to false");
                return;
            }
            if (serverConfig.schedulerIntervalTicks() != EgoDynamicServerConfig.DEFAULT_SCHEDULER_INTERVAL_TICKS) {
                helper.fail("Expected malformed simulation config to repair scheduler interval to default");
                return;
            }
            if (Math.abs(serverConfig.mirrorUpdateEpsilon() - EgoDynamicServerConfig.DEFAULT_MIRROR_UPDATE_EPSILON) > 1.0E-12D) {
                helper.fail("Expected malformed simulation config to repair mirror epsilon to default");
                return;
            }
            if (!EgoDynamicSynergyConfigLoader.load(temporaryRoot).edges().isEmpty()) {
                helper.fail("Expected malformed synergy config to repair to an empty edge list");
                return;
            }

            deleteRecursively(temporaryRoot);
            helper.succeed();
        } catch (IOException | RuntimeException exception) {
            helper.fail("Expected malformed dynamic config repair to succeed: " + exception.getMessage());
        }
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void stamina_progression_consumes_and_recovers_server_state(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState beforeSprint = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.60D);

        player.setData(EgoAttachments.EGO_STATE.get(), beforeSprint);
        EgoState afterSprint = EgoStaminaService.applyActivity(
                player.getData(EgoAttachments.EGO_STATE.get()),
                new EgoStaminaActivityContext(1.0D, true, false, false, false, 20, 1.0D));
        player.setData(EgoAttachments.EGO_STATE.get(), afterSprint);
        if (!(player.getData(EgoAttachments.EGO_STATE.get()).stamina() < beforeSprint.stamina())) {
            helper.fail("Expected sprinting to consume persisted server-authoritative stamina");
            return;
        }

        EgoState beforeRest = player.getData(EgoAttachments.EGO_STATE.get());
        EgoState afterRest = EgoStaminaService.applyActivity(
                beforeRest,
                new EgoStaminaActivityContext(0.0D, false, false, false, false, 20, 1.0D));
        player.setData(EgoAttachments.EGO_STATE.get(), afterRest);
        if (!(player.getData(EgoAttachments.EGO_STATE.get()).stamina() > beforeRest.stamina())) {
            helper.fail("Expected resting to recover persisted server-authoritative stamina");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void sleep_debt_progression_increases_server_fatigue(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState before = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.20D)
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, 0.40D)
                .withDisplayValue(EgoAttributeCatalog.STRESS, 0.30D)
                .withDisplayValue(EgoAttributeCatalog.COMFORT, 0.50D);

        player.setData(EgoAttachments.EGO_STATE.get(), before);
        EgoState after = EgoSleepService.applySleep(
                player.getData(EgoAttachments.EGO_STATE.get()),
                new EgoSleepContext(18000L, false, 0, 72000, 20, 1.0D));
        player.setData(EgoAttachments.EGO_STATE.get(), after);

        if (!(player.getData(EgoAttachments.EGO_STATE.get()).fatigue() > before.fatigue())) {
            helper.fail("Expected awake-night sleep pressure to increase persisted server-authoritative fatigue");
            return;
        }

        helper.succeed();
    }

    @GameTest(template = EMPTY, timeoutTicks = 20)
    public static void nap_progression_recovers_less_fatigue_than_night_sleep(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        EgoState tired = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.STRESS, 0.0D)
                .withDisplayValue(EgoAttributeCatalog.THERMAL_STRAIN, 0.0D)
                .withDisplayValue(EgoAttributeCatalog.COMFORT, 1.0D);

        EgoState afterNightSleep = EgoSleepService.applySleep(
                tired,
                new EgoSleepContext(18000L, true, 200, 20, 1.0D));
        EgoState afterDayNap = EgoSleepService.applySleep(
                tired,
                new EgoSleepContext(6000L, true, 20, 20, 1.0D));

        if (!(afterDayNap.fatigue() < tired.fatigue())) {
            helper.fail("Expected day nap to recover some persisted fatigue");
            return;
        }
        if (!(afterNightSleep.fatigue() < afterDayNap.fatigue())) {
            helper.fail("Expected night sleep to recover more fatigue than a short day nap");
            return;
        }

        player.setData(EgoAttachments.EGO_STATE.get(), afterDayNap);
        helper.succeed();
    }

    private static void assertMirror(GameTestHelper helper, Player player, EgoAttributeSpec spec, double expectedValue) {
        AttributeInstance instance = player.getAttribute(EgoAttributes.displayAttributes().get(EgoAttributeCatalog.displayOnlySpecs().indexOf(spec)));
        if (instance == null) {
            helper.fail("Missing display attribute instance for " + spec.path());
            return;
        }

        double actualValue = instance.getValue();
        if (Math.abs(actualValue - expectedValue) > 1.0E-7D) {
            helper.fail("Expected " + spec.path() + " to mirror " + expectedValue + " but was " + actualValue);
            return;
        }

        ResourceLocation modifierId = EgoAttributeMirrorService.mirrorModifierId(spec);
        long modifierCount = instance.getModifiers().stream()
                .filter(modifier -> modifier.is(modifierId))
                .count();
        if (modifierCount != 1L) {
            helper.fail("Expected exactly one mirror modifier for " + spec.path() + " but found " + modifierCount);
        }
    }

    private static boolean assertDrinkRecognized(GameTestHelper helper, ItemStack stack, String label) {
        if (EgoDrinkItemResolver.resolve(stack).isEmpty()) {
            helper.fail("Expected drink resolver to recognize " + label);
            return false;
        }
        return true;
    }

    private static void deleteRecursively(Path root) throws IOException {
        if (root == null || Files.notExists(root)) {
            return;
        }
        try (var paths = Files.walk(root)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(path);
            }
        }
    }

    private static boolean requiresPermissionLevelTwo(
            GameTestHelper helper,
            CommandNode<CommandSourceStack> node,
            String label) {
        if (node == null) {
            helper.fail("Missing /" + label + " command node");
            return false;
        }

        Predicate<CommandSourceStack> requirement = node.getRequirement();
        if (requirement.test(commandSource(helper, 0))) {
            helper.fail("/" + label + " must reject permission level 0");
            return false;
        }
        if (!requirement.test(commandSource(helper, 2))) {
            helper.fail("/" + label + " must accept permission level 2");
            return false;
        }
        return true;
    }

    private static CommandSourceStack commandSource(GameTestHelper helper, int permissionLevel) {
        return new CommandSourceStack(
                CommandSource.NULL,
                Vec3.ZERO,
                Vec2.ZERO,
                helper.getLevel(),
                permissionLevel,
                "Immersive EGO GameTest",
                Component.literal("Immersive EGO GameTest"),
                helper.getLevel().getServer(),
                null);
    }
}
