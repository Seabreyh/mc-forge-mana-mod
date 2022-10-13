package com.seabreyh.mana;

import com.mojang.logging.LogUtils;
import com.seabreyh.mana.event.ManaClientEvents;
import com.seabreyh.mana.gui.ManaMenuTypes;
import com.seabreyh.mana.networking.ManaMessages;
import com.seabreyh.mana.registry.ManaBlockEntities;
import com.seabreyh.mana.registry.ManaBlocks;
import com.seabreyh.mana.registry.ManaEntities;
import com.seabreyh.mana.registry.ManaItems;
import com.seabreyh.mana.registry.ManaParticles;
import com.seabreyh.mana.registry.ManaRecipes;
import com.seabreyh.mana.registry.ManaSounds;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mana")
public class ManaMod {
    public static final String MOD_ID = "mana";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ManaMod() {

        // Register items
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ManaItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ManaEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ManaParticles.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ManaSounds.SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());

        ManaBlocks.register(eventBus);
        ManaBlockEntities.register(eventBus);
        ManaMenuTypes.register(eventBus);
        ManaRecipes.register(eventBus);

        // Add listeners
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        event.enqueueWork(() -> {
            ManaMessages.register();

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ManaBlocks.FLOWER_BUTTERCUP.getId(),
                    ManaBlocks.POTTED_FLOWER_BUTTERCUP);

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ManaBlocks.PLANT_LEMONBALM.getId(),
                    ManaBlocks.POTTED_PLANT_LEMONBALM);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ManaClientEvents.registerEntityRenderers(event);
        ManaClientEvents.registerBlockRenderers(event);
        ManaClientEvents.registerBlockEntityRenderers(event);
        ManaClientEvents.registerOverlays(event);

        ManaClientEvents.registerMenuScreens(/* no event pls */);

        // ClientSetup.registerMenuScreens(event);
        // MenuScreens.register(ManaMenuTypes.STAR_CATCHER_MENU.get(),
        // StarCatcherScreen::new);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        // LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            // LOGGER.info("HELLO from Register Block");
        }
    }

}
