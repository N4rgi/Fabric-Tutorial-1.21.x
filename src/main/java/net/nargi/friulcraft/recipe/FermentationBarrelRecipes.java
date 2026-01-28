package net.nargi.friulcraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record FermentationBarrelRecipes(Ingredient inputItem, ItemStack output) implements Recipe<FermentationBarrelRecipesInput> {

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.inputItem);
        return list;
    }

    // read Recipe JSON file ---> new Fermentation Barrel

    @Override
    public boolean matches(FermentationBarrelRecipesInput input, World world) {
        if (world.isClient()) {
            return false;
        }

        return inputItem.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(FermentationBarrelRecipesInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FERMENTATION_BARREL_RECIPES_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FERMENTATION_BARREL_RECIPES_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<FermentationBarrelRecipes> {
        public static final MapCodec<FermentationBarrelRecipes> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(FermentationBarrelRecipes::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(FermentationBarrelRecipes::output)
        ).apply(inst, FermentationBarrelRecipes::new));

        public static final PacketCodec<RegistryByteBuf, FermentationBarrelRecipes> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, FermentationBarrelRecipes::inputItem,
                        ItemStack.PACKET_CODEC, FermentationBarrelRecipes::output,
                        FermentationBarrelRecipes::new);

        @Override
        public MapCodec<FermentationBarrelRecipes> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FermentationBarrelRecipes> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
