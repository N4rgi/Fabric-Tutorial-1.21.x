
package net.nargi.friulcraft.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.nargi.friulcraft.FriulCraftMod;
import net.nargi.friulcraft.block.ModBlocks;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> VINE_PLANT_KEY = registryKey("vine_plant");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, VINE_PLANT_KEY, Feature.TREE,
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.OAK_LOG), // Optional: short trunk
                        new StraightTrunkPlacer(1, 0, 0),          // Minimal trunk
                        BlockStateProvider.of(ModBlocks.VINE_PLANT_LEAVES), // Dense leaves
                        new BlobFoliagePlacer(
                                ConstantIntProvider.create(2),         // Radius
                                ConstantIntProvider.create(0),         // Offset
                                3                                      // Height
                        ),
                        new TwoLayersFeatureSize(0, 0, 0)          // No layering
                ).ignoreVines().build()
        );
    }



    public static RegistryKey<ConfiguredFeature<?,?>> registryKey(String name){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(FriulCraftMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register
            (Registerable<ConfiguredFeature<?,?>> context, RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }


}
