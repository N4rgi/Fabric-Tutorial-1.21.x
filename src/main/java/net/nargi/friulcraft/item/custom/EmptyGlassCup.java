package net.nargi.friulcraft.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.nargi.friulcraft.item.ModItems;

public class EmptyGlassCup extends Item {

    public EmptyGlassCup(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        // Raycast to detect water block
        var hit = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);

        if (hit.getType() == HitResult.Type.BLOCK) {
            var pos = hit.getBlockPos();

            // Check if the block is water
            if (world.getFluidState(pos).isIn(FluidTags.WATER)) {

                // Play sound
                player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);

                // Replace item with your filled cup
                ItemStack filledCup = new ItemStack(ModItems.WATER_GLASS);

                // Creative players don't consume items
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }

                // Give the filled cup
                if (stack.isEmpty()) {
                    return TypedActionResult.success(filledCup, world.isClient());
                } else {
                    player.getInventory().insertStack(filledCup);
                }

                return TypedActionResult.success(stack, world.isClient());
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}