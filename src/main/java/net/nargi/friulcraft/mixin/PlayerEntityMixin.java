package net.nargi.friulcraft.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.nargi.friulcraft.effect.ModEffects;
import net.nargi.friulcraft.item.ModItems;
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

    @Inject(
            method = "interact(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onInteractEntity(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity self = (PlayerEntity) (Object) this;

        // Server-side only
        if (self.getWorld().isClient()) return;

        // Only main hand interactions
        if (hand != Hand.MAIN_HAND) return;

        // Must click another player
        if (!(entity instanceof PlayerEntity target)) return;

        ItemStack selfStack = self.getMainHandStack();
        ItemStack targetStack = target.getMainHandStack();

        // Required items
        if (!selfStack.isOf(ModItems.WINE_BOTTLE)) return;
        if (!targetStack.isOf(ModItems.EMPTY_WINE_GLASS)) return;

        // Remove 1 empty glass
        targetStack.decrement(1);

        // Give 1 filled wine glass
        ItemStack filledGlass = new ItemStack(ModItems.WINE_GLASS);

        // If their hand is now empty, put it in hand
        if (targetStack.isEmpty()) {
            target.setStackInHand(Hand.MAIN_HAND, filledGlass);
        } else {
            // Otherwise try to insert into inventory
            if (!target.getInventory().insertStack(filledGlass)) {
                target.dropItem(filledGlass, false);
            }
        }

        self.getWorld().playSound(
                null,
                self.getBlockPos(),
                SoundEvents.ITEM_BOTTLE_EMPTY,
                SoundCategory.PLAYERS,
                1.0f,
                1.0f
        );

        if (self instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer) {
            var adv = serverPlayer.server.getAdvancementLoader()
                    .get(Identifier.of("friulcraft", "pour_wine"));

            if (adv != null) {
                serverPlayer.getAdvancementTracker().grantCriterion(adv, "pour_wine");
            }
        }

        if (!self.getAbilities().creativeMode) {
            if (selfStack.getDamage() + 1 >= selfStack.getMaxDamage()) {
                self.setStackInHand(Hand.MAIN_HAND, new ItemStack(ModItems.EMPTY_WINE_BOTTLE));
            } else {

                // Otherwise damage normally (server-safe)
                if (self.getWorld() instanceof net.minecraft.server.world.ServerWorld serverWorld
                        && self instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer) {
                    selfStack.damage(
                            1,
                            serverWorld,
                            serverPlayer,
                            item -> {}
                    );
                }
            }
        }

        // Sync inventories
        target.playerScreenHandler.sendContentUpdates();
        self.playerScreenHandler.sendContentUpdates();

        cir.setReturnValue(ActionResult.SUCCESS);

    }

}
