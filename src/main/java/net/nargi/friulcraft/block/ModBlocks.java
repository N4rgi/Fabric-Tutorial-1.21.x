package net.nargi.friulcraft.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;
import net.nargi.friulcraft.block.custom.*;
import net.nargi.friulcraft.world.tree.ModSaplingGenerators;

public class ModBlocks {

    public static final Block VINE_PLANT_LEAVES = registerBlock("vine_plant_leaves",
            new vine_plant_leaves(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));

    public static final Block VINE_PLANT = registerBlock("vine_plant",
            new vine_plant(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).noCollision()));

    public static final Block VINE_PLANT_SAPLING = registerBlock("vine_plant_sapling",
            new SaplingBlock(ModSaplingGenerators.VINE_PLANT, AbstractBlock.Settings.copy(Blocks.OAK_SAPLING).noCollision()));

    public static final Block WINE_PRESS = registerBlock("wine_press",
            new wine_press(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Block FERMENTATION_BARREL = registerBlock("fermentation_barrel",
            new fermentation_barrel(Block.Settings.copy(Blocks.BARREL)));



    public static final Block FRICO = registerBlock("frico",
            new frico(AbstractBlock.Settings.copy(Blocks.CAKE)));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(FriulCraftMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(FriulCraftMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(RenderLayer translucent) {
        FriulCraftMod.LOGGER.info("Registering Mod Blocks for " + FriulCraftMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.VINE_PLANT_LEAVES);
            fabricItemGroupEntries.add(ModBlocks.VINE_PLANT);
            fabricItemGroupEntries.add(ModBlocks.VINE_PLANT_SAPLING);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Items.FLOWERING_AZALEA_LEAVES, VINE_PLANT_LEAVES);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Items.FLOWERING_AZALEA_LEAVES, VINE_PLANT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Items.CHERRY_SAPLING, VINE_PLANT_SAPLING);
        });


    }
}
