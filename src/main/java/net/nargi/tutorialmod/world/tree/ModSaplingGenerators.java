package net.nargi.tutorialmod.world.tree;

import net.minecraft.block.SaplingGenerator;
import net.nargi.tutorialmod.TutorialMod;
import net.nargi.tutorialmod.world.ModConfiguredFeatures;

import java.util.Optional;

public class ModSaplingGenerators {
    public static final SaplingGenerator VINE_PLANT = new SaplingGenerator(TutorialMod.MOD_ID + ":vine_plant",
            Optional.empty(), Optional.of(ModConfiguredFeatures.VINE_PLANT_KEY),Optional.empty());
}
