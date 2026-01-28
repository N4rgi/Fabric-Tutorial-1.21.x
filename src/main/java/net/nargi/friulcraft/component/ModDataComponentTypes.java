package net.nargi.friulcraft.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<String> WINE_NAME =
            register("wine_name", builder -> builder.codec(Codec.STRING));

    public static final ComponentType<String> WINE_DESCRIPTION =
            register("wine_description", builder -> builder.codec(Codec.STRING));

    public static final ComponentType<Integer> DRUNK_LVL =
            register("drunk_lvl", builder -> builder.codec(Codec.INT));

    public static final ComponentType<Integer> WINE_AGE =
            register("wine_age", builder -> builder.codec(Codec.INT));


    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(FriulCraftMod.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }


    public static void registerDataComponentTypes() {
        FriulCraftMod.LOGGER.info("Registering Data Component Types for " + FriulCraftMod.MOD_ID);
    }
}
