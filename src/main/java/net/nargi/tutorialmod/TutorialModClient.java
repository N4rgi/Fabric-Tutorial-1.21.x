package net.nargi.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.Window;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.nargi.tutorialmod.block.ModBlocks;
import net.nargi.tutorialmod.effect.ModEffects;
import net.nargi.tutorialmod.screen.ModScreenHandlers;
import net.nargi.tutorialmod.screen.custom.FermentationBarrelScreen;

import java.io.IOException;

public class TutorialModClient implements ClientModInitializer {

    private PostEffectProcessor shaderEffect = null;
    private boolean shaderActive = false;


    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VINE_PLANT_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VINE_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VINE_PLANT_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WINE_PRESS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FRICO, RenderLayer.getCutout());

        HandledScreens.register(ModScreenHandlers.FERMENTATION_BARREL_SCREEN_HANDLER, FermentationBarrelScreen::new);

        // 1️⃣  Create / remove the shader when potion effect toggles
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean hasEffect = client.player.hasStatusEffect(ModEffects.DRUNK);

            if (hasEffect && !shaderActive) {
                loadShader(client);
            } else if (!hasEffect && shaderActive) {
                unloadShader();
            }
        });

        // 2️⃣  Render the shader each frame while active
        WorldRenderEvents.END.register(context -> {
            if (shaderEffect != null) {
                MinecraftClient client = MinecraftClient.getInstance();
                Window window = client.getWindow();

                // ✅ Get the current frame’s tick delta safely
                float tickDelta = client.getRenderTickCounter().getTickDelta(true);

                shaderEffect.render(tickDelta);
                shaderEffect.setupDimensions(window.getFramebufferWidth(), window.getFramebufferHeight());
            }
        });
    }

    private void loadShader(MinecraftClient client) {
        try {
            ResourceManager manager = client.getResourceManager();
            Identifier shaderId = Identifier.of(TutorialMod.MOD_ID, "shaders/post/drunk.json");
            shaderEffect = new PostEffectProcessor(client.getTextureManager(), manager, client.getFramebuffer(), shaderId);
            shaderEffect.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            shaderActive = true;
            System.out.println("[TutorialMod] Trying to load shader from: " + shaderId);

        } catch (IOException e) {
            System.err.println("[TutorialMod] Failed to load Drunk shader: " + e.getMessage());
        }
    }

    private void unloadShader() {
        if (shaderEffect != null) {
            shaderEffect.close();
            shaderEffect = null;
        }
        shaderActive = false;
    }
}