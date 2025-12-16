package net.nargi.friulcraft.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.nargi.friulcraft.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(
            method = "canConsume",
            at = @At("HEAD"),
            cancellable = true
    )
    private void friulcraft$allowEatAtFull(
            boolean ignoreHunger,
            CallbackInfoReturnable<Boolean> cir
    ) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        // Allow eating if player has Umami
        if (player.hasStatusEffect(ModEffects.UMAMI)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(
            method = "eatFood",
            at = @At("TAIL")
    )
    private void friulcraft$healFromFood(
            World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir
    ) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if (world.isClient()) return;
        if (!player.hasStatusEffect(ModEffects.UMAMI)) return;

        FoodComponent food = stack.get(DataComponentTypes.FOOD);
        if (food == null) return;

        // Heal based on food nutrition (balanced)
        float healAmount = food.nutrition() * 0.5F;

        player.heal(healAmount);
    }
}
