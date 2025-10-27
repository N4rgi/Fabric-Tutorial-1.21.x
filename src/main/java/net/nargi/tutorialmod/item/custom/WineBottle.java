package net.nargi.tutorialmod.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WineBottle extends Item {

    public WineBottle(Settings settings) {
        super(settings);
    }

    public int getColor(ItemStack stack) {
        var color = stack.get(DataComponentTypes.DYED_COLOR);
        return color != null ? color.rgb() : 0xA06540; // default brownish
    }

    // ✅ Set a new color (e.g. after crafting with dyes)
    public void setColor(ItemStack stack, int color) {
        stack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(color, true));
    }

    // ✅ Optional: remove color (like cleaning in a cauldron)
    public void clearColor(ItemStack stack) {
        stack.remove(DataComponentTypes.DYED_COLOR);
    }
}
