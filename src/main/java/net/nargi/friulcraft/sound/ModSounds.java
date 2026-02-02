package net.nargi.friulcraft.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;

public class ModSounds {

    public static final SoundEvent GRAPES_PICK = registeringSoundEvent("grapes_pick");
    public static final SoundEvent FULL_BARREL = registeringSoundEvent("full_barrel");
    public static final SoundEvent BOTTLE_BARREL = registeringSoundEvent("bottle_barrel");


    private static SoundEvent registeringSoundEvent(String name) {
        Identifier id = Identifier.of(FriulCraftMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registeringSounds() {
        FriulCraftMod.LOGGER.info("Registering Mod Sounds for " + FriulCraftMod.MOD_ID);
    }
}
