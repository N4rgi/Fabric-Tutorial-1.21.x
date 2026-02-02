package net.nargi.friulcraft.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.nargi.friulcraft.item.ModItems;
import net.nargi.friulcraft.sound.ModSounds;

public class vine_plant extends Block implements Waterloggable {
    public static final IntProperty AGE = IntProperty.of("age", 0, 2);
    private static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0)
    );
    public static BooleanProperty WATERLOGGED;

    public vine_plant(Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager.getDefaultState()
                    .with(AGE, 0)
                    .with(WATERLOGGED, false)
        );
    }

    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED)
                ? Fluids.WATER.getStill(false)
                : super.getFluidState(state);
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
        builder.add(WATERLOGGED);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int age = state.get(AGE);
        float pitch = 0.9F + world.random.nextFloat() * 0.2F;
        if (!world.isClient && age == 2) {
            world.playSound( null, player.getBlockPos(), SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, pitch);
            int i = 1 + world.getRandom().nextInt(2);
            ItemStack drop = new ItemStack(ModItems.GRAPES, i);
            ItemEntity entity = new ItemEntity(world,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    drop);
            world.spawnEntity(entity);
            world.setBlockState(pos, state.with(AGE, 0), Block.NOTIFY_LISTENERS);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.getBlockState(pos.up()).isSolidBlock(world, pos.up())) {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                   WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state;
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);

        int age = state.get(AGE);

        if (!world.isClient && age == 2) {
            int i = 1 + world.getRandom().nextInt(2);

            ItemStack drop = new ItemStack(ModItems.GRAPES, i);
            ItemEntity entity = new ItemEntity(world,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    drop);
            world.spawnEntity(entity);
        }
    }    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());

        return this.getDefaultState()
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER); // Waterlog if underwater
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }

}