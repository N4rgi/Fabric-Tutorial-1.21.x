package net.nargi.tutorialmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.nargi.tutorialmod.TutorialMod;
import net.minecraft.registry.Registry;
import net.nargi.tutorialmod.item.custom.CalibratedEnderPearl;

public class ModItems {

    public static final Item GRAPES = registerItem("grapes",
            new Item(new Item.Settings().food(ModFoodComponents.GRAPES)));

    public static final Item GRAPE_SEEDS = registerItem("grape_seeds",
            new Item(new Item.Settings()));

    public static final Item CALIBRATED_ENDER_PEARL = registerItem("calibrated_ender_pearl",
            new CalibratedEnderPearl(new Item.Settings().maxDamage(100)));

    public static final Item GRAPES_MUST = registerItem("grapes_must",
            new Item(new Item.Settings()));

    public static final Item GLASS_CUP = registerItem("glass_cup",
            new Item(new Item.Settings().food(ModFoodComponents.GLASS_CUP)));


    public static final Item FRICO_SLICE = registerItem("frico_slice",
            new Item(new Item.Settings().food(ModFoodComponents.FRICO_SLICE)));


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
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.addAfter(Items.DIAMOND_SWORD, CALIBRATED_ENDER_PEARL);
        });
    }
}
