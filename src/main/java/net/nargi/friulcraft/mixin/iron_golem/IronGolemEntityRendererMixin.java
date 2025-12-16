package net.nargi.friulcraft.mixin.iron_golem;

import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;
import net.nargi.friulcraft.entity.custom.GolemVariant;
import net.nargi.friulcraft.entity.variant.IronGolemVariantAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntityRenderer.class)
public abstract class IronGolemEntityRendererMixin {

    private static final Identifier GRAPES_TEXTURE =
            Identifier.of("friulcraft", "textures/entity/golem/iron_golem_grapes.png");

    private static final Identifier DEFAULT_TEXTURE =
            Identifier.of("minecraft", "textures/entity/iron_golem/iron_golem.png");

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void friulcraft$swapTexture(IronGolemEntity entity,
                                        CallbackInfoReturnable<Identifier> cir) {

        GolemVariant variant =
                ((IronGolemVariantAccessor) entity).getVariant();

        if (variant == GolemVariant.GRAPES) {
            cir.setReturnValue(GRAPES_TEXTURE);
        } else {
            cir.setReturnValue(DEFAULT_TEXTURE);
        }
    }
}
