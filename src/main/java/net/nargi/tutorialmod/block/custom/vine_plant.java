package net.nargi.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.nargi.tutorialmod.item.ModItems;

public class vine_plant extends Block {
    public static final IntProperty AGE = IntProperty.of("age", 0, 2);
    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0)
    );

    public vine_plant(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }



    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);
        if (i < 2) {
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
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int age = state.get(AGE);
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