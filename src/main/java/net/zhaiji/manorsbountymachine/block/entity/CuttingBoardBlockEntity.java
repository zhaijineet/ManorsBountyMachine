package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.recipe.CuttingBoardMultipleRecipe;
import net.zhaiji.manorsbountymachine.recipe.CuttingBoardSingleRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

import java.util.Optional;

public class CuttingBoardBlockEntity extends AbstractContainerBlockEntity {
    public static final int ITEMS_SIZE = 6;
    public final RecipeManager.CachedCheck<CuttingBoardBlockEntity.CuttingBoardCraftContainer, CuttingBoardSingleRecipe> singleRecipeCheck;
    public final RecipeManager.CachedCheck<CuttingBoardBlockEntity.CuttingBoardCraftContainer, CuttingBoardMultipleRecipe> multipleRecipeCheck;
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
    public int craftIndex = 5;

    public CuttingBoardBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.CUTTING_BOARD.get(), pPos, pBlockState);
        this.singleRecipeCheck = RecipeManager.createCheck(InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_TYPE.get());
        this.multipleRecipeCheck = RecipeManager.createCheck(InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_TYPE.get());
    }

    public void craftSingleItem(ItemStack tool) {
        CuttingBoardCraftContainer container = new CuttingBoardCraftContainer(this, tool);
        while (this.craftIndex >= 0) {
            Optional<CuttingBoardSingleRecipe> recipe = this.getSingleRecipe(container);
            if (recipe.isPresent()) {
                CuttingBoardSingleRecipe cuttingBoardSingleRecipe = recipe.get();
                this.items.remove(this.craftIndex);
                this.dropItemStack(cuttingBoardSingleRecipe.assemble(container, this.level.registryAccess()));
                this.dropItemStack(cuttingBoardSingleRecipe.getOutgrowth(this.level));
                break;
            }
            this.craftIndex--;
        }
        if (this.craftIndex <= 0) {
            this.resetCraftIndex();
        }
    }

    public boolean craftMultipleItem() {
        CuttingBoardCraftContainer container = new CuttingBoardCraftContainer(this);
        Optional<CuttingBoardMultipleRecipe> recipe = this.getMultipleRecipe(container);
        if (recipe.isPresent()) {
            CuttingBoardMultipleRecipe cuttingBoardMultipleRecipe = recipe.get();
            this.clearContent();
            this.dropItemStack(cuttingBoardMultipleRecipe.assemble(container, this.level.registryAccess()));
            return true;
        }
        return false;
    }

    public void dropItemStack(ItemStack itemStack) {
        BlockPos blockPos = this.getBlockPos();
        Containers.dropItemStack(this.level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
    }

    public void resetCraftIndex() {
        this.craftIndex = 5;
        this.setChanged();
    }

    public Optional<CuttingBoardSingleRecipe> getSingleRecipe(CuttingBoardCraftContainer container) {
        return this.singleRecipeCheck.getRecipeFor(container, this.level);
    }

    public Optional<CuttingBoardMultipleRecipe> getMultipleRecipe(CuttingBoardCraftContainer container) {
        return this.multipleRecipeCheck.getRecipeFor(container, this.level);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public IItemHandler getItemHandler() {
        return new InvWrapper(this);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.craftIndex = pTag.getInt("craftIndex");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("craftIndex", this.craftIndex);
    }

    public static class CuttingBoardCraftContainer implements Container {
        public CuttingBoardBlockEntity blockEntity;
        public ItemStack tool = ItemStack.EMPTY;

        public CuttingBoardCraftContainer(CuttingBoardBlockEntity blockEntity) {
            this.blockEntity = blockEntity;
        }

        public CuttingBoardCraftContainer(CuttingBoardBlockEntity blockEntity, ItemStack tool) {
            this.blockEntity = blockEntity;
            this.tool = tool;
        }

        public ItemStack getMaterial() {
            return this.blockEntity.getItem(this.blockEntity.craftIndex);
        }

        public NonNullList<ItemStack> getAllItem() {
            return this.blockEntity.items;
        }

        @Override
        public int getContainerSize() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getItem(int pSlot) {
            return null;
        }

        @Override
        public ItemStack removeItem(int pSlot, int pAmount) {
            return null;
        }

        @Override
        public ItemStack removeItemNoUpdate(int pSlot) {
            return null;
        }

        @Override
        public void setItem(int pSlot, ItemStack pStack) {

        }

        @Override
        public void setChanged() {
        }

        @Override
        public boolean stillValid(Player pPlayer) {
            return false;
        }

        @Override
        public void clearContent() {
        }
    }
}
