package net.nargi.friulcraft.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;

public class ModRecipes {
    public static final RecipeSerializer<FermentationBarrelRecipes> FERMENTATION_BARREL_RECIPES_RECIPE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(FriulCraftMod.MOD_ID, "fermentation_barrel"),
                    new FermentationBarrelRecipes.Serializer());
    public static final RecipeType<FermentationBarrelRecipes> FERMENTATION_BARREL_RECIPES_RECIPE_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(FriulCraftMod.MOD_ID, "fermentation_barrel"), new RecipeType<FermentationBarrelRecipes>() {
                @Override
                public String toString() {
                    return "fermentation_barrel";
                }});

    public static final RecipeSerializer<WineGlassRecipe> DRINK_FILLING_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            Identifier.of(FriulCraftMod.MOD_ID, "drink_filling"),
            new SpecialRecipeSerializer<>(WineGlassRecipe::new)
    );

    public static final RecipeType<WineGlassRecipe> DRINK_FILLING_TYPE = Registry.register(
            Registries.RECIPE_TYPE,
            Identifier.of(FriulCraftMod.MOD_ID, "drink_filling"),
            new RecipeType<WineGlassRecipe>() {
                @Override
                public String toString() {
                    return "drink_filling";
                }
            }
    );

    public static void registerRecipes() {
        FriulCraftMod.LOGGER.info("Registering Custom Recipes for " + FriulCraftMod.MOD_ID);
    }

}
