package com.oblixorprime.immersiveego;

import com.mojang.logging.LogUtils;
import com.oblixorprime.immersiveego.config.EgoClientConfig;
import com.oblixorprime.immersiveego.config.EgoCoreConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(ImmersiveEgo.MOD_ID)
public final class ImmersiveEgo {
    public static final String MOD_ID = "immersive_ego";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ImmersiveEgo(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.SERVER, EgoCoreConfig.SPEC, "immersive_ego-core.toml");
        modContainer.registerConfig(ModConfig.Type.CLIENT, EgoClientConfig.SPEC, "immersive_ego-client.toml");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Immersive EGO bootstrap loaded. Gameplay systems are gated by server config.");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.debug("Immersive EGO server hook active for {}", event.getServer().getServerModName());
    }
}
