package net.nargi.friulcraft.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.nargi.friulcraft.component.ModDataComponentTypes;

import java.util.List;

public class Etiquette extends Item {

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        stack.set(ModDataComponentTypes.WINE_NAME, "");
        stack.set(ModDataComponentTypes.WINE_DESCRIPTION, "");
        return stack;
    }


    public Etiquette(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        String wineName = stack.get(ModDataComponentTypes.WINE_NAME);
        String wineDescription = stack.get(ModDataComponentTypes.WINE_DESCRIPTION);

        if (wineName == null) wineName = "";
        if (wineDescription == null) wineDescription = "";

        // "Wine" in purple
        Text wineLabel = Text.literal("Wine: ")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x843C7D)));

        // Value in default white
        Text wineValue = Text.literal(wineName);

        // Combine them
        tooltip.add(Text.empty().append(wineLabel).append(wineValue));

        // "Description" in dark green
        Text descLabel = Text.literal("Description: ")
                .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x2E8B57)));

        // Value in default white
        Text descValue = Text.literal(wineDescription);

        tooltip.add(Text.empty().append(descLabel).append(descValue));
    }

}
