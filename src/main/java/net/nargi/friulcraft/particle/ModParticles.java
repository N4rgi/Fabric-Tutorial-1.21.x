package net.nargi.friulcraft.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.FriulCraftMod;

public class ModParticles {

    public static final SimpleParticleType DRIP_WINE =
            registerParticles("drip_wine", FabricParticleTypes.simple());

    private static SimpleParticleType registerParticles(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(FriulCraftMod.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
        FriulCraftMod.LOGGER.info("Register Particle for " + FriulCraftMod.MOD_ID);
    }
}
