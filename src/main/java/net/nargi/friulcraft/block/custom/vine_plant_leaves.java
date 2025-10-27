package net.nargi.friulcraft.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.nargi.friulcraft.block.ModBlocks;
import net.nargi.friulcraft.item.ModItems;

public class vine_plant_leaves extends Block {
    public static final IntProperty AGE = IntProperty.of("age", 0, 2);
    public static Integer IS_AGEING = 0;

    public vine_plant_leaves(Settings settings) {
        super(settings);
    }



    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 2 && IS_AGEING == 0) {
            if (random.nextInt(10) == 0) {
                world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int age = state.get(AGE);
        ItemStack heldItem = player.getMainHandStack();

        //Drop Grapes
        if (!world.isClient && age == 2) {
            int i = 1 + world.getRandom().nextInt(2);

            ItemStack drop = new ItemStack(ModItems.GRAPES, i);
            ItemEntity entity = new ItemEntity(world,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    drop);
            world.spawnEntity(entity);
            world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
            return ActionResult.SUCCESS;
        }

        //Grow under Vines
        if (heldItem.isOf(Items.BONE_MEAL)) {
            BlockPos below = pos.down();
            if (world.getBlockState(below).isAir()) {
                if (!world.isClient) {
                    // Place another vine_plant under this one
                    world.setBlockState(below, ModBlocks.VINE_PLANT.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BONE_MEAL_USE,
                            SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.syncWorldEvent(2005, pos, 0);

                    if (!player.isCreative()) {
                        heldItem.decrement(1);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }

        if (heldItem.isOf(Items.SHEARS) && IS_AGEING == 0) {
            world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(2005, pos, 0);
            IS_AGEING = 1;
            player.sendMessage(Text.of("not Grow"));

            return ActionResult.SUCCESS;

        }
        else if (heldItem.isOf(Items.SHEARS) && IS_AGEING == 1) {
            world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(2005, pos, 0);
            IS_AGEING = 0;
            player.sendMessage(Text.of("Grow"));

            return ActionResult.SUCCESS;

        }
        return ActionResult.PASS;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.getBlockState(pos.up()).isSolidBlock(world, pos.up())) {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }
}

