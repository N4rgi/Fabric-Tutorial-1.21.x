package net.nargi.friulcraft.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nargi.friulcraft.block.custom.fermentation_barrel;
import net.nargi.friulcraft.block.entity.ImplementedInventory;
import net.nargi.friulcraft.block.entity.ModBlockEntities;
import net.nargi.friulcraft.item.ModItems;
import net.nargi.friulcraft.recipe.FermentationBarrelRecipes;
import net.nargi.friulcraft.recipe.FermentationBarrelRecipesInput;
import net.nargi.friulcraft.recipe.ModRecipes;
import net.nargi.friulcraft.screen.custom.fermentation_barrel_screen_handler;
import net.nargi.friulcraft.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class fermentation_barrel_entity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private ItemStack lastInput = ItemStack.EMPTY;
    private boolean wasFull = false;


    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 200;

    public fermentation_barrel_entity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FERMENTATION_BARREL_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> fermentation_barrel_entity.this.progress;
                    case 1 -> fermentation_barrel_entity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: fermentation_barrel_entity.this.progress = value;
                    case 1: fermentation_barrel_entity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("fermentation_barrel.progress", progress);
        nbt.putInt("fermentation_barrel.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        progress = nbt.getInt("fermentation_barrel.progress");
        maxProgress = nbt.getInt("fermentation_barrel.max_progress");
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        ItemStack currentInput = getStack(INPUT_SLOT);

        if (!ItemStack.areItemsEqual(currentInput, lastInput)) {
            resetProgress();
            lastInput = currentInput.copy();
            markDirty(world, pos, state);
            return;
        }

        int grapesProgress = state.get(fermentation_barrel.GRAPES_PROGRESS);

        if(hasRecipe() && grapesProgress < 8 && getStack(INPUT_SLOT).isOf(ModItems.GRAPES_MUST)) {
            increaseCraftingProgress();
            markDirty(world, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else if (hasRecipe() && getStack(INPUT_SLOT).isOf(ModItems.EMPTY_WINE_BOTTLE) && grapesProgress <= 8 && grapesProgress >=4) {
            increaseCraftingProgress();
            markDirty(world, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 200;
    }

    private void craftItem() {
        Optional<RecipeEntry<FermentationBarrelRecipes>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return;

        ItemStack inputStack = getStack(INPUT_SLOT).copy();
        ItemStack output = recipe.get().value().output();

        this.removeStack(INPUT_SLOT, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(
                output.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + output.getCount()

        ));


        BlockState state = this.getCachedState();
        float pitch = 0.9F + world.random.nextFloat() * 0.2F;


        if (state.contains(fermentation_barrel.GRAPES_PROGRESS)) {
            int currentProgress = state.get(fermentation_barrel.GRAPES_PROGRESS);

            if (inputStack.isOf(ModItems.GRAPES_MUST)) {

                int newProgress = Math.min(currentProgress + 1, 8);
                this.world.setBlockState(this.pos,
                        state.with(fermentation_barrel.GRAPES_PROGRESS, newProgress),
                        Block.NOTIFY_ALL);
                world.playSound( null, pos, ModSounds.FULL_BARREL, SoundCategory.BLOCKS, 1.0f, pitch);
            }

            if (inputStack.isOf(ModItems.EMPTY_WINE_BOTTLE)) {
                int newProgress = Math.max(currentProgress - 4, 0);
                this.world.setBlockState(this.pos,
                        state.with(fermentation_barrel.GRAPES_PROGRESS, newProgress),
                        Block.NOTIFY_ALL);
                world.playSound( null, pos, ModSounds.BOTTLE_BARREL, SoundCategory.BLOCKS, 1.0f, pitch);
            }
        }
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }


    private boolean hasRecipe() {
        Optional<RecipeEntry<FermentationBarrelRecipes>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeEntry<FermentationBarrelRecipes>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager()
                .getFirstMatch(ModRecipes.FERMENTATION_BARREL_RECIPES_RECIPE_TYPE, new FermentationBarrelRecipesInput(inventory.get(INPUT_SLOT)), this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Fermentation Barrel");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new fermentation_barrel_screen_handler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
