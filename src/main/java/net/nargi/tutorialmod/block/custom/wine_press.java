package net.nargi.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.nargi.tutorialmod.item.ModItems;

public class wine_press extends Block {
    public static final IntProperty LEVEL = IntProperty.of("level", 0, 5);
    private static final VoxelShape SHAPE = VoxelShapes.union(

            Block.createCuboidShape(0, 0, 0, 16, 8, 2),
            Block.createCuboidShape(0, 0, 14, 16, 8, 16),
            Block.createCuboidShape(0, 0, 2, 2, 8, 14),
            Block.createCuboidShape(14, 0, 2, 16, 8, 14),
            Block.createCuboidShape(0, 0, 0, 16, 2, 16)
    );
    private static final VoxelShape MUST_SHAPE = VoxelShapes.union(

            Block.createCuboidShape(2, 0, 2, 14, 16, 14)
    );

    public wine_press(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (world.isClient) return;
        if (!(entity instanceof PlayerEntity)) return;

        var box = entity.getBoundingBox();

        var trigger = MUST_SHAPE.offset(pos.getX(), pos.getY(), pos.getZ());

        if (!trigger.getBoundingBox().intersects(entity.getBoundingBox())) {
            return;
        }

        int i = state.get(LEVEL);

        if (i < 5) {
        int next = (i + 1);
        world.setBlockState(pos, state.with(LEVEL, i + 1), Block.NOTIFY_ALL);
        }

        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int i = state.get(LEVEL);
        ItemStack heldItem = player.getMainHandStack();
        if (heldItem.isOf(ModItems.GRAPES) && i < 5) {
            if (!player.isCreative()) {
                heldItem.decrement(1);
            }

            world.setBlockState(pos, state.with(LEVEL, i + 1), Block.NOTIFY_ALL);
            return ActionResult.SUCCESS;
        }

        if (i == 5) {
            ItemStack drop = new ItemStack(ModItems.GRAPES_MUST, 1);
            ItemEntity entity = new ItemEntity(world,
                    pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                    drop);
            world.spawnEntity(entity);
            world.setBlockState(pos, state.with(LEVEL, 0), Block.NOTIFY_ALL);

            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
