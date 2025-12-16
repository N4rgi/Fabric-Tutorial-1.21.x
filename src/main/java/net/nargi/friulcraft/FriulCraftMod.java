package net.nargi.friulcraft;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.nargi.friulcraft.block.ModBlocks;
import net.nargi.friulcraft.block.entity.ModBlockEntities;
import net.nargi.friulcraft.component.ModDataComponentTypes;
import net.nargi.friulcraft.effect.ModEffects;
import net.nargi.friulcraft.item.ModItems;
import net.nargi.friulcraft.particle.ModParticles;
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
        ModBlocks.registerModBlocks(RenderLayer.getCutout());

        ModVillagers.registerVillagers();

        ModBlockEntities.registerBlockEntities();

        ModScreenHandlers.registerScreenHandlers();

        ModParticles.registerParticles();

        ModDataComponentTypes.registerDataComponentTypes();

        CompostingChanceRegistry.INSTANCE.add(ModItems.GRAPES, 0.5f);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_SAPLING, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_LEAVES, 30, 60);

        ModEffects.registerEffects();

        TradeOfferHelper.registerVillagerOffers(ModVillagers.WINEMAKER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.GRAPES,4), 7, 2,0.04f));
        });
    }
}