package net.nargi.friulcraft.world.tree;

import net.minecraft.block.SaplingGenerator;
import net.nargi.friulcraft.FriulCraftMod;
import net.nargi.friulcraft.world.ModConfiguredFeatures;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator VINE_PLANT = new SaplingGenerator(FriulCraftMod.MOD_ID + ":vine_plant",
            Optional.empty(), Optional.of(ModConfiguredFeatures.VINE_PLANT_KEY),Optional.empty());
}
