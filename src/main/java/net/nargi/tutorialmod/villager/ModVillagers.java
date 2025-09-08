package net.nargi.tutorialmod.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.nargi.tutorialmod.TutorialMod;
import net.nargi.tutorialmod.block.ModBlocks;

public class ModVillagers {

    public static final RegistryKey<PointOfInterestType> WINEMAKER_POI_KEY = registerPoiKey("winemaker_poi");
    public static final PointOfInterestType WINEMAKER_POI = registerPOI("winemaker_poi", ModBlocks.VINE_PLANT_SAPLING);

    public static final VillagerProfession WINEMAKER = registerProfession("winemaker", WINEMAKER_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(TutorialMod.MOD_ID, name),
                new VillagerProfession(name, pointOfInterestTypeRegistryEntry ->
                        pointOfInterestTypeRegistryEntry.matchesKey(type), pointOfInterestTypeRegistryEntry ->
                        pointOfInterestTypeRegistryEntry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_FARMER));
    }

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(TutorialMod.MOD_ID, name),
                1,1,block);
    }

    private static RegistryKey<PointOfInterestType> registerPoiKey(String name){
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(TutorialMod.MOD_ID, name));
    }

    public static void registerVillagers() {
        TutorialMod.LOGGER.info("Register Villagers for " + TutorialMod.MOD_ID);
    }
}
