package net.nargi.tutorialmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.nargi.tutorialmod.TutorialMod;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final Item GRAPES = registerItem("grapes", new Item(new Item.Settings().food(ModFoodComponents.GRAPES)));
    public static final Item GRAPE_SEEDS = registerItem("grape_seeds", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TutorialMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TutorialMod.LOGGER.info("Registering Mod Items for " + TutorialMod.MOD_ID);

     ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(fabricItemGroupEntries -> {
         fabricItemGroupEntries.add(GRAPES);
     });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.addAfter(Items.BEETROOT, GRAPES);
        });
    }
}
