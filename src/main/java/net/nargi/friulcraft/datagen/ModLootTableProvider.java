package net.nargi.friulcraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import net.nargi.friulcraft.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generate() {

        addDrop(ModBlocks.VINE_PLANT_LEAVES,
                leavesDrops(ModBlocks.VINE_PLANT_LEAVES, ModBlocks.VINE_PLANT_SAPLING, 0.0625f));

        addDrop(ModBlocks.FERMENTATION_BARREL);
        addDrop(ModBlocks.WINE_PRESS);
        addDrop(ModBlocks.VINE_PLANT_SAPLING);

    }
}
