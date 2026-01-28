package net.nargi.friulcraft.block.custom;

import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.nargi.friulcraft.block.ModBlocks;
import net.nargi.friulcraft.item.ModItems;

public class vine_plant_leaves extends Block implements Waterloggable {
    public static final IntProperty AGE = IntProperty.of("age", 0, 2);
    public static final IntProperty DISTANCE = IntProperty.of("distance", 0, 1);
    public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;
    public static BooleanProperty WATERLOGGED;

    public vine_plant_leaves(Settings settings) {

        super(settings);

        this.setDefaultState(
                this.stateManager.getDefaultState()
                        .with(AGE, 0)
                        .with(WATERLOGGED, false)
                        .with(PERSISTENT, false)
        );
    }



    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        int age = state.get(AGE);
        boolean found_log = false;
        boolean persistent = state.get(PERSISTENT);

        int radius = 3;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {

                    BlockPos checkPos = pos.add(dx, dy, dz);

                    if (world.getBlockState(checkPos).isIn(BlockTags.LOGS)) {
                        found_log = true;
                        dx = dy = dz = 999;
                        break;
                    }
                }
            }
        }

        int newDist = found_log ? 1 : 0;

        if (!found_log && persistent == false) {
            //REMOVE BLOCK
            world.removeBlock(pos, false);

            if (!world.isClient && age == 2) {
                int i = 1 + world.getRandom().nextInt(2);

                ItemStack drop = new ItemStack(ModItems.GRAPES, i);
                ItemEntity entity = new ItemEntity(world,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        drop);
                world.spawnEntity(entity);
            }

        if (world.random.nextFloat() < 0.0625f) {
            ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    new ItemStack(ModBlocks.VINE_PLANT_SAPLING));
            world.spawnEntity(entity);
        }

        return;
        }

        if (age < 2 && random.nextInt(10) == 0) {
            age++;
        }

        BlockState newState = state
                .with(AGE, age)
                .with(DISTANCE, newDist);

        if (newState != state) {
            world.setBlockState(pos, newState, Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, DISTANCE, WATERLOGGED, PERSISTENT);
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
        return ActionResult.PASS;
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);

        int age = state.get(AGE);

        if (!(tool.getItem() instanceof ShearsItem)) {
            if (world.random.nextFloat() < 0.0625f) {
                ItemEntity entity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(ModBlocks.VINE_PLANT_SAPLING));
                world.spawnEntity(entity);
            }
        }

        if (!world.isClient && age == 2) {
            int i = 1 + world.getRandom().nextInt(2);

            ItemStack drop = new ItemStack(ModItems.GRAPES, i);
            ItemEntity entity = new ItemEntity(world,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    drop);
            world.spawnEntity(entity);
        }
    }


    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED)
                ? Fluids.WATER.getStill(false)
                : super.getFluidState(state);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                   WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());

        return this.getDefaultState()
                .with(PERSISTENT, true)
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER); // Waterlog if underwater
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }



}