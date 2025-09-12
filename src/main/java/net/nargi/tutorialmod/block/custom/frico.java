package net.nargi.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class frico extends Block {
    public static final IntProperty BITES = IntProperty.of("bites", 0, 3);
    private static final VoxelShape SHAPE0 = VoxelShapes.union(

            Block.createCuboidShape(2, 0, 2, 14, 3, 14)
    );
    private static final VoxelShape SHAPE1 = VoxelShapes.union(

            Block.createCuboidShape(2, 0, 8, 14, 3, 14),
            Block.createCuboidShape(8, 0, 2, 14, 3, 14)


    );
    private static final VoxelShape SHAPE2 = VoxelShapes.union(

            Block.createCuboidShape(2, 0, 8, 14, 3, 14)
    );
    private static final VoxelShape SHAPE3 = VoxelShapes.union(

            Block.createCuboidShape(8, 0, 8, 14, 3, 14)

    );


    public frico(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(BITES, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int i = state.get(BITES);
        ItemStack stack = new ItemStack(ModItems.FRICO_SLICE, 1);

        // Try to insert into inventory
        if (!player.getInventory().insertStack(stack)) {
            // If inventory full, drop in world
            player.dropItem(stack, false);
        }

        if (!world.isClient && i < 3) {
            world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_LISTENERS);
        }

        if (!world.isClient && i == 3) {
            world.removeBlock(pos, false);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int i = state.get(BITES);
        if (i ==1){
            return SHAPE1;
        }
        if (i ==2){
            return SHAPE2;
        }
        if (i ==3){
            return SHAPE3;
        }
        else {
            return SHAPE0;
        }


    }
}
