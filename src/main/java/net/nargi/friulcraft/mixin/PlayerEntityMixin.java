package net.nargi.friulcraft.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.nargi.friulcraft.effect.ModEffects;
import net.nargi.friulcraft.util.ICustomIntDrunkLvl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements ICustomIntDrunkLvl {

    @Inject(
            method = "canConsume",
            at = @At("HEAD"),
            cancellable = true
    )
    private void friulcraft$allowEatAtFull(
            boolean ignoreHunger,
            CallbackInfoReturnable<Boolean> cir
    ) {
        PlayerEntity player = (PlayerEntity) (Object) this;

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
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (world.isClient()) return;
        if (!player.hasStatusEffect(ModEffects.UMAMI)) return;

        FoodComponent food = stack.get(DataComponentTypes.FOOD);
        if (food == null) return;

        // Heal based on food nutrition (balanced)
        float healAmount = food.nutrition() * 0.5F;

        player.heal(healAmount);
    }

    // --- Declare custom tracked data ---
    private static final TrackedData<Integer> CUSTOM_INT = DataTracker.registerData(
            PlayerEntity.class,
            TrackedDataHandlerRegistry.INTEGER
    );

    // --- Inject into initDataTracker to register it ---
    @Inject(method = "initDataTracker", at = @At("RETURN"))
    private void addCustomDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(CUSTOM_INT, 0); // Default value = 0
    }

    // --- Getter and setter using getDataTracker() ---
    @Override
    public int getCustomInt() {
        return ((PlayerEntity) (Object) this).getDataTracker().get(CUSTOM_INT);
    }

    @Override
    public void setCustomInt(int value) {
        ((PlayerEntity) (Object) this).getDataTracker().set(CUSTOM_INT, value);
    }

    @Inject(
            method = "wakeUp",
            at = @At("TAIL")
    )
    private void friulcraft$resetDrunkLevelOnWakeUp(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!player.getWorld().isClient()) {
            this.setCustomInt(0);
            player.removeStatusEffect(ModEffects.DRUNK);
            player.removeStatusEffect(ModEffects.UMAMI);
        }
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void friulcraft$drunkTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (player.getWorld().isClient()) return;

        // Half a Minecraft day
        final int INTERVAL = 3000;

        // Only run once per interval
        if (player.getWorld().getTime() % INTERVAL != 0) return;

        int drunk = this.getCustomInt();

        if (drunk > 0) {
            this.setCustomInt(drunk - 1);
        }

        // Optional: auto-remove effect when sober
        if (this.getCustomInt() == 0) {
            player.removeStatusEffect(ModEffects.DRUNK);
        }
    }
}
