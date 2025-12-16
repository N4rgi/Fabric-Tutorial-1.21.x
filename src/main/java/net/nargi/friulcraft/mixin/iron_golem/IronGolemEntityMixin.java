package net.nargi.friulcraft.mixin.iron_golem;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.nargi.friulcraft.entity.custom.GolemVariant;
import net.nargi.friulcraft.entity.variant.IronGolemVariantAccessor;
import net.nargi.friulcraft.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements IronGolemVariantAccessor {

    private static final TrackedData<Integer> CUSTOM_VARIANT =
            DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public IronGolemEntityMixin(EntityType<? extends GolemEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void friulcraft$initVariant(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(CUSTOM_VARIANT, 0);
    }

    @Override
    public GolemVariant getVariant() {
        return GolemVariant.byId(this.dataTracker.get(CUSTOM_VARIANT));
    }

    @Override
    public void setVariant(GolemVariant variant) {
        this.dataTracker.set(CUSTOM_VARIANT, variant.getId());
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void friulcraft$writeVariant(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("FriulcraftGolemVariant", this.getVariant().getId());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void friulcraft$readVariant(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("FriulcraftGolemVariant")) {
            this.setVariant(GolemVariant.byId(nbt.getInt("FriulcraftGolemVariant")));
        }
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void friulcraft$onUse(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {

        ItemStack stack = player.getStackInHand(hand);
        World world = this.getWorld();

        if (stack.isOf(ModItems.GRAPES)) {

            if (((IronGolemVariantAccessor) this).getVariant() != GolemVariant.DEFAULT) {
                return;
            }

            ((IronGolemVariantAccessor) this).setVariant(GolemVariant.GRAPES);

            if (!player.isCreative()) {
                stack.decrement(1);
            }



            world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1, this.getZ(), 0, 0, 0);
            world.playSound(null, this.getBlockPos(), SoundEvents.ITEM_DYE_USE, SoundCategory.NEUTRAL, 1f, 1f);


            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }

        if (stack.isOf(Items.SHEARS)) {

            if (((IronGolemVariantAccessor) this).getVariant() != GolemVariant.GRAPES) {
                return;
            }

            ((IronGolemVariantAccessor) this).setVariant(GolemVariant.DEFAULT);

            if (!world.isClient) {
                ItemEntity carrot = new ItemEntity(
                        world,
                        this.getX(),
                        this.getY() + 1,
                        this.getZ(),
                        new ItemStack(ModItems.GRAPES, 1)
                );
                world.spawnEntity(carrot);


            world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1, this.getZ(), 0, 0, 0);
            world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.NEUTRAL, 1f, 1f);

            cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
