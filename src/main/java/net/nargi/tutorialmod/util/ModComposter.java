package net.nargi.tutorialmod.util;

import net.minecraft.block.ComposterBlock;
import net.nargi.tutorialmod.item.ModItems;

public class ModComposter {
    public static void registerComposterChanges() {
        // Aggiungi l'item grapes come compostabile
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.GRAPES, 1.0f);
    }
}
