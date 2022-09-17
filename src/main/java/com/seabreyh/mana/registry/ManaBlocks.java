package com.seabreyh.mana.registry;

import com.seabreyh.mana.ManaMod;
import com.seabreyh.mana.blocks.CelestialTorch;
import com.seabreyh.mana.blocks.StarBottle;
import com.seabreyh.mana.blocks.StarCatcher;
import com.seabreyh.mana.blocks.botany.flowers.Flower;
import com.google.common.base.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ManaBlocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
                        ManaMod.MOD_ID);

        // Create blocks ----

        // INFO: Remember to register your block render type in ManaClientEvents.java if
        // it is a 3D/translucent block.

        // INFO: If you want to make a basic block, you dont need to create a class for
        // that block, just replace new StarCatcher with Block

        public static final RegistryObject<Block> STAR_CATCHER = registerBlock("star_catcher",
                        () -> new StarCatcher(BlockBehaviour.Properties
                                        .of(Material.STONE)
                                        .strength(0.2f)
                                        .destroyTime(0.3f)
                                        .noOcclusion()
                                        .lightLevel(BlockState -> 15)),
                        ManaCreativeTabs.MANA_TAB_BLOCKS);

        public static final RegistryObject<Block> STAR_BOTTLE = registerBlock("star_bottle",
                        () -> new StarBottle(BlockBehaviour.Properties
                                        .of(Material.GLASS)
                                        .sound(SoundType.GLASS)
                                        .strength(0.2f)
                                        .destroyTime(0.3f)
                                        .noOcclusion()
                                        .lightLevel(BlockState -> 15)),
                        ManaCreativeTabs.MANA_TAB_BLOCKS);

        public static final RegistryObject<Block> CELESTIAL_TORCH = registerBlock("celestial_torch",
                        () -> new CelestialTorch(BlockBehaviour.Properties
                                        .of(Material.DECORATION).noCollission().instabreak()
                                        .lightLevel(BlockState -> 15)
                                        .sound(SoundType.WOOD)),
                        ManaCreativeTabs.MANA_TAB_BLOCKS);

        public static final RegistryObject<Block> FLOWER_BUTTERCUP = registerBlock("flower_buttercup",
                        () -> new Flower(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak()
                                        .sound(SoundType.GRASS)),
                        ManaCreativeTabs.MANA_TAB_BLOCKS);

        // End create blocks ----

        private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                        CreativeModeTab tab) {
                RegistryObject<T> toReturn = BLOCKS.register(name, block);
                registerBlockItem(name, toReturn, tab);
                return toReturn;
        }

        private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                        CreativeModeTab tab) {
                return ManaItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
        }

        public static void register(IEventBus eventBus) {
                BLOCKS.register(eventBus);
        }
}
