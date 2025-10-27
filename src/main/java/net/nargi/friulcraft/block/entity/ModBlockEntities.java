package net.nargi.friulcraft.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;
import net.nargi.friulcraft.block.ModBlocks;
import net.nargi.friulcraft.block.entity.custom.fermentation_barrel_entity;

public class ModBlockEntities {
    public static final BlockEntityType<fermentation_barrel_entity> FERMENTATION_BARREL_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(FriulCraftMod.MOD_ID, "fermentation_barrel_be"),
                    BlockEntityType.Builder.create(fermentation_barrel_entity::new, ModBlocks.FERMENTATION_BARREL).build(null));

    public static void registerBlockEntities() {
        FriulCraftMod.LOGGER.info("Registering Block Entities for " + FriulCraftMod.MOD_ID);
    }
}
