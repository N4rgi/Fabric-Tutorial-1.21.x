package net.nargi.friulcraft.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;
import net.nargi.friulcraft.block.ModBlocks;

public class ModItemsGroups {

    public static final ItemGroup PINK_GARNET_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(FriulCraftMod.MOD_ID, "friulcraft_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.WINE_GLASS))
                    .displayName(Text.translatable("itemgroup.friulcraft.itemgroup"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.GRAPES);
                        entries.add(ModItems.GRAPES_MUST);
                        entries.add(ModBlocks.VINE_PLANT);
                        entries.add(ModBlocks.VINE_PLANT_LEAVES);
                        entries.add(ModBlocks.VINE_PLANT_SAPLING);
                        entries.add(ModBlocks.FERMENTATION_BARREL);
                        entries.add(ModBlocks.WINE_PRESS);
                        entries.add(ModBlocks.FRICO);
                        entries.add(ModItems.FRICO_SLICE);
                        entries.add(ModItems.EMPTY_WINE_GLASS);
                        entries.add(ModItems.EMPTY_WINE_BOTTLE);
                        entries.add(ModItems.WINE_GLASS);
                        entries.add(ModItems.WINE_BOTTLE);
                    }).build());

    public static void registeringItemGroups() {
        FriulCraftMod.LOGGER.info("Registering Item Groups for " + FriulCraftMod.MOD_ID);
    }
}
