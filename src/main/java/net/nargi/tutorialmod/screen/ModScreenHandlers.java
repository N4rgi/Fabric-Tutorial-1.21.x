package net.nargi.tutorialmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nargi.tutorialmod.TutorialMod;
import net.nargi.tutorialmod.screen.custom.fermentation_barrel_screen_handler;

public class ModScreenHandlers {
    public static final ScreenHandlerType<fermentation_barrel_screen_handler> FERMENTATION_BARREL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(TutorialMod.MOD_ID, "fermentation_barrel_screen_handler"),
                    new ExtendedScreenHandlerType<>(fermentation_barrel_screen_handler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        TutorialMod.LOGGER.info("Registering Screen Handlers for " + TutorialMod.MOD_ID);
    }
}
