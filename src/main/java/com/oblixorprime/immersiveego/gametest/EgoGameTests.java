package com.oblixorprime.immersiveego.gametest;

import com.mojang.brigadier.tree.CommandNode;
import com.oblixorprime.immersiveego.ImmersiveEgo;
import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import com.oblixorprime.immersiveego.registry.EgoAttributeSpec;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorService;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
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

        CommandNode<CommandSourceStack> synergy = ego.getChild("synergy");
        if (synergy == null || synergy.getChild("trace") == null) {
            helper.fail("Missing /ego synergy trace command");
            return;
        }

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
}
