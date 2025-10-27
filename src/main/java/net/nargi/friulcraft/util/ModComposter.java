package net.nargi.friulcraft.util;

import net.minecraft.block.ComposterBlock;
import net.nargi.friulcraft.item.ModItems;

public class ModComposter {
    public static void registerComposterChanges() {
        // Aggiungi l'item grapes come compostabile
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.GRAPES, 1.0f);
    }
}
