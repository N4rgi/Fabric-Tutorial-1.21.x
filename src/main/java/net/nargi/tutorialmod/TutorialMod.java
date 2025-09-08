package net.nargi.tutorialmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.nargi.tutorialmod.block.ModBlocks;
import net.nargi.tutorialmod.item.ModItems;
import net.nargi.tutorialmod.util.ModComposter;
import net.nargi.tutorialmod.villager.ModVillagers;
import net.nargi.tutorialmod.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorialmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModWorldGeneration.generateModWorldGen();
        ModBlocks.registerModBlocks(RenderLayer.getCutout());

        ModVillagers.registerVillagers();

        CompostingChanceRegistry.INSTANCE.add(ModItems.GRAPES, 0.5f);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_SAPLING, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.VINE_PLANT_LEAVES, 30, 60);

        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (entity instanceof IronGolemEntity ironGolemEntity) {
                if (playerEntity.getMainHandStack().getItem() == ModItems.GRAPES) {
                    playerEntity.sendMessage(Text.literal("Yes"));
                }
            }
                return ActionResult.SUCCESS;
        });

        // Qui modifichi il composter
        ModComposter.registerComposterChanges();
	}
}