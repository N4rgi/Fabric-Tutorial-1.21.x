package net.nargi.tutorialmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.nargi.tutorialmod.TutorialMod;
import net.minecraft.registry.Registry;
import net.nargi.tutorialmod.item.custom.GlassCup;
import net.nargi.tutorialmod.item.custom.WineBottle;

public class ModItems {

    public static final Item GRAPES = registerItem("grapes",
            new Item(new Item.Settings().food(ModFoodComponents.GRAPES)));

    public static final Item GRAPES_MUST = registerItem("grapes_must",
            new Item(new Item.Settings().maxCount(16).food(ModFoodComponents.GRAPES_MUST)));

    public static final Item GLASS_CUP = registerItem("glass_cup",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));


    public static final Item FRICO_SLICE = registerItem("frico_slice",
            new Item(new Item.Settings().food(ModFoodComponents.FRICO_SLICE)));

    public static final Item ETIQUETTE = registerItem("etiquette",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_RED = registerItem("etiquette_red",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_ORANGE = registerItem("etiquette_orange",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_YELLOW = registerItem("etiquette_yellow",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_LIME = registerItem("etiquette_lime",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_GREEN = registerItem("etiquette_green",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_CYAN = registerItem("etiquette_cyan",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_LIGHT_BLUE = registerItem("etiquette_light_blue",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_BLUE = registerItem("etiquette_blue",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_PURPLE = registerItem("etiquette_purple",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_MAGENTA = registerItem("etiquette_magenta",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_PINK = registerItem("etiquette_pink",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_WHITE = registerItem("etiquette_white",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_LIGHT_GRAY = registerItem("etiquette_light_gray",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_GRAY = registerItem("etiquette_gray",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_BLACK = registerItem("etiquette_black",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));

    public static final Item ETIQUETTE_BROWN = registerItem("etiquette_brown",
            new GlassCup(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TutorialMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TutorialMod.LOGGER.info("Registering Mod Items for " + TutorialMod.MOD_ID);

     ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(fabricItemGroupEntries -> {
         fabricItemGroupEntries.add(GRAPES);
         fabricItemGroupEntries.add(GLASS_CUP);
     });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.addAfter(Items.BEETROOT, GRAPES);
        });
    }
}
