package net.nargi.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.nargi.tutorialmod.block.ModBlocks;
import net.nargi.tutorialmod.screen.ModScreenHandlers;
import net.nargi.tutorialmod.screen.custom.fermentation_barrel_screen;

public class TutorialModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VINE_PLANT_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VINE_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VINE_PLANT_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WINE_PRESS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FRICO, RenderLayer.getCutout());

        HandledScreens.register(ModScreenHandlers.FERMENTATION_BARREL_SCREEN_HANDLER, fermentation_barrel_screen::new);

    }
}
