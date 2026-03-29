package net.nargi.friulcraft.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.nargi.friulcraft.item.ModItems;

public class WineGlassRecipe extends SpecialCraftingRecipe {

    public WineGlassRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput inventory, World world) {
        boolean hasBottle = false;
        boolean hasGlass = false;

        for (ItemStack stack : inventory.getStacks()) {
            if (stack.isOf(ModItems.WINE_BOTTLE)) hasBottle = true;
            if (stack.isOf(ModItems.EMPTY_WINE_GLASS)) hasGlass = true;
        }

        return hasBottle && hasGlass;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput inventory, RegistryWrapper.WrapperLookup lookup) {
        return new ItemStack(ModItems.WINE_GLASS);
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput inventory) {
        var stacks = inventory.getStacks();
        DefaultedList<ItemStack> remainders = DefaultedList.ofSize(stacks.size(), ItemStack.EMPTY);

        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);

            if (stack.isOf(ModItems.WINE_BOTTLE)) {
                ItemStack damaged = stack.copy();
                damaged.setDamage(damaged.getDamage() + 1);

                remainders.set(i,
                        damaged.getDamage() >= damaged.getMaxDamage()
                                ? new ItemStack(ModItems.EMPTY_WINE_BOTTLE)
                                : damaged
                );
            }
        }

        return remainders;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public net.minecraft.recipe.RecipeSerializer<?> getSerializer() {
        return ModRecipes.DRINK_FILLING_SERIALIZER;
    }
}