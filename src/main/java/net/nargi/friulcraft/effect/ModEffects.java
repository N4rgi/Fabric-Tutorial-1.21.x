package net.nargi.friulcraft.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> DRUNK = registerStatusEffect("drunk",
            new DrunkEffect(StatusEffectCategory.HARMFUL, 0xEAD1DC));

    public static final RegistryEntry<StatusEffect> UMAMI = registerStatusEffect("umami",
            new UmamiEffect(StatusEffectCategory.HARMFUL, 0xF1C232));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(FriulCraftMod.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
            FriulCraftMod.LOGGER.info("Registering Mod Effects for " + FriulCraftMod.MOD_ID);
    }
}
