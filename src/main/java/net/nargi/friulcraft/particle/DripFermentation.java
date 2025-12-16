package net.nargi.friulcraft.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class DripFermentation extends SpriteBillboardParticle {
    protected DripFermentation(ClientWorld world, double x, double y, double z,
                               SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        this.gravityStrength = 0.06F;
        this.velocityMultiplier = 0.98F;
        this.maxAge = 40;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void tick() {
        super.tick();

        this.velocityX = 0;
        this.velocityZ = 0;

        this.velocityY -= this.gravityStrength;

        if (this.onGround) {
            this.markDead();
        }
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z,
                                                 double velocityX, double velocityY, double velocityZ) {
            return new DripFermentation(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
        }
    }
}
