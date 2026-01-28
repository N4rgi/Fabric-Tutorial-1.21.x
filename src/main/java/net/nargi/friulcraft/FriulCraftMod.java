package net.nargi.friulcraft;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.nargi.friulcraft.block.ModBlocks;
import net.nargi.friulcraft.block.entity.ModBlockEntities;
import net.nargi.friulcraft.component.ModDataComponentTypes;
import net.nargi.friulcraft.effect.ModEffects;
import net.nargi.friulcraft.item.ModItems;
import net.nargi.friulcraft.item.ModItemsGroups;
import net.nargi.friulcraft.particle.ModParticles;
import net.nargi.friulcraft.recipe.ModRecipes;
import net.nargi.friulcraft.screen.ModScreenHandlers;
import net.nargi.friulcraft.villager.ModVillagers;
import net.nargi.friulcraft.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FriulCraftMod implements ModInitializer {
	public static final String MOD_ID = "friulcraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModWorldGeneration.generateModWorldGen();
        ModBlocks.registerModBlocks();

        ModVillagers.registerVillagers();

        ModBlockEntities.registerBlockEntities();

        ModScreenHandlers.registerScreenHandlers();

        ModParticles.registerParticles();

        ModDataComponentTypes.registerDataComponentTypes();

        ModRecipes.registerRecipes();

        ModItemsGroups.registeringItemGroups();

        CompostingChanceRegistry.INSTANCE.add(ModItems.GRAPES, 0.5f);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_SAPLING, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_LEAVES, 30, 60);

        ModEffects.registerEffects();

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WINEMAKER, 1, factories -> {

            //grapes-emerald
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.GRAPES, 10),
                    new ItemStack(Items.EMERALD,1), 20, 2,0.05f));

            //emerald-leaves
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD,1),
                    new ItemStack(ModBlocks.VINE_PLANT_LEAVES,1), 20, 2,0.04f));

            //emerald-leaves vine
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD,1),
                    new ItemStack(ModBlocks.VINE_PLANT,1), 20, 2,0.04f));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WINEMAKER, 2, factories -> {

            //emerald-slice
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 1),
                    new ItemStack(ModItems.FRICO_SLICE,2), 16, 5,0.04f));

            //emerald-frico
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModBlocks.FRICO,1), 4, 5,0.04f));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WINEMAKER, 3, factories -> {

            //emerald-must
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModItems.GRAPES_MUST,1), 8, 5,0.04f));

            //emerald-glass
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 1),
                    new ItemStack(ModItems.EMPTY_WINE_GLASS,2), 4, 6,0.04f));

            //emerald-empty bottle
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModItems.EMPTY_WINE_BOTTLE,1), 4, 8,0.04f));

        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WINEMAKER, 4, factories -> {

            // emerald-custom bottle
            factories.add((entity, random) -> {
                ItemStack bottle = new ItemStack(ModItems.WINE_BOTTLE, 1);

                bottle.set(ModDataComponentTypes.WINE_NAME, "Golden Golem Wine");
                bottle.set(ModDataComponentTypes.WINE_DESCRIPTION,
                        "A strong wine aged in the body of old golems");

                return new TradeOffer(
                        new TradedItem(Items.EMERALD, 5),
                        bottle,
                        3,
                        10,
                        0.05f
                );
            });
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WINEMAKER, 5, factories -> {

            // emerald-custom bottle
            factories.add((entity, random) -> {
                ItemStack bottle = new ItemStack(ModItems.WINE_BOTTLE, 1);

                bottle.set(ModDataComponentTypes.WINE_NAME, "Cabernet Pillager");
                bottle.set(ModDataComponentTypes.WINE_DESCRIPTION,
                        "Rare find once belonging of the pillagers");

                return new TradeOffer(
                        new TradedItem(Items.EMERALD, 10),
                        bottle,
                        1,
                        10,
                        0.05f
                );
            });

        });

        TradeOfferHelper.registerWanderingTraderOffers( 2, factories -> {

            //emerald-slice
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    new ItemStack(ModBlocks.VINE_PLANT_SAPLING,1), 8, 2,0.04f));

        });
    }
}