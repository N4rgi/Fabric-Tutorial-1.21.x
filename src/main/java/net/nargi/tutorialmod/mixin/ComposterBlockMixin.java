package net.nargi.tutorialmod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nargi.tutorialmod.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin {

    @Inject(method = "emptyFullComposter", at = @At("HEAD"), cancellable = true)
    private static void changeOutput(Entity user, BlockState state, World world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient) {
            // Qui droppi il tuo item custom
            ItemStack stack = new ItemStack(ModItems.GRAPES);
            Block.dropStack(world, pos, stack);

            // Reset del composter
            BlockState newState = state.with(ComposterBlock.LEVEL, 0);
            world.setBlockState(pos, newState, 3);

            // Ritorno e stop del codice vanilla
            cir.setReturnValue(newState);
            return;
        }
    }
}
