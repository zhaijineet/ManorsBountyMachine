package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.block.FermenterBlock;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.menu.FermenterMenu;
import net.zhaiji.manorsbountymachine.recipe.BaseFermentationRecipe;
import net.zhaiji.manorsbountymachine.recipe.BrightFermentationRecipe;
import net.zhaiji.manorsbountymachine.recipe.DimFermentationRecipe;
import net.zhaiji.manorsbountymachine.recipe.NormalFermentationRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import net.zhaiji.manorsbountymachine.util.MachineUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FermenterBlockEntity extends AbstractMachineBlockEntity {
    public static final int ITEMS_SIZE = 8;
    public static final int CATALYSTS_TOP = 0;
    public static final int CATALYSTS_BOTTOM = 1;
    public static final int BOTTLE = 2;
    public static final int TOP_LEFT = 3;
    public static final int TOP_RIGHT = 4;
    public static final int BOTTOM_LEFT = 5;
    public static final int BOTTOM_RIGHT = 6;
    public static final int OUTPUT = 7;
    public static final int[] INPUT_SLOTS = {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};
    public static final double CATALYST_A_EFFECT = 0.85;
    public static final double CATALYST_B_EFFECT = 0.7;
    public static final double CATALYST_C_EFFECT = 0.5;
    public static final double CATALYST_X_EFFECT = 0;
    public final RecipeManager.CachedCheck<FermenterBlockEntity, DimFermentationRecipe> dimRecipeCheck;
    public final RecipeManager.CachedCheck<FermenterBlockEntity, NormalFermentationRecipe> normalRecipeCheck;
    public final RecipeManager.CachedCheck<FermenterBlockEntity, BrightFermentationRecipe> brightRecipeCheck;
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
    public boolean isRunning = false;
    public int cookingTime = 0;
    public int maxCookingTime = 0;
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> FermenterBlockEntity.this.cookingTime;
                case 1 -> FermenterBlockEntity.this.maxCookingTime;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> FermenterBlockEntity.this.setCookingTime(pValue);
                case 1 -> FermenterBlockEntity.this.setMaxCookingTime(pValue);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    public final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level pLevel, BlockPos pPos, BlockState pState) {
            FermenterBlockEntity.this.playBarrelOpenSound();
            FermenterBlockEntity.this.setOpen(true);
        }

        @Override
        protected void onClose(Level pLevel, BlockPos pPos, BlockState pState) {
            FermenterBlockEntity.this.playBarrelCloseSound();
            FermenterBlockEntity.this.setOpen(false);
        }

        @Override
        protected void openerCountChanged(Level pLevel, BlockPos pPos, BlockState pState, int pCount, int pOpenCount) {
        }

        @Override
        protected boolean isOwnContainer(Player pPlayer) {
            if (pPlayer.containerMenu instanceof FermenterMenu menu) {
                return menu.blockEntity == FermenterBlockEntity.this;
            }
            return false;
        }
    };
    public int outputMultiple = 0;

    public FermenterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.FERMENTER.get(), pPos, pBlockState);
        this.dimRecipeCheck = RecipeManager.createCheck(InitRecipe.DIM_FERMENTATION_RECIPE_TYPE.get());
        this.normalRecipeCheck = RecipeManager.createCheck(InitRecipe.NORMAL_FERMENTATION_RECIPE_TYPE.get());
        this.brightRecipeCheck = RecipeManager.createCheck(InitRecipe.BRIGHT_FERMENTATION_RECIPE_TYPE.get());
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, FermenterBlockEntity pBlockEntity) {
        pBlockEntity.recheckOpen();
        if (pBlockEntity.isRunning) {
            pBlockEntity.cookingTime++;
            if (pBlockEntity.cookingTime >= pBlockEntity.maxCookingTime) {
                pBlockEntity.craftItem();
            }
            pBlockEntity.setChanged();
        }
    }

    public void startRunning() {
        if (this.isRunning) return;
        if (!this.getItem(OUTPUT).isEmpty()) return;
        int maxCookingTime;
        Optional<DimFermentationRecipe> dimRecipe = this.getDimRecipe();
        Optional<NormalFermentationRecipe> normalRecipe = this.getNormalRecipe();
        Optional<BrightFermentationRecipe> brightRecipe = this.getBrightRecipe();
        if (dimRecipe.isPresent()) {
            maxCookingTime = dimRecipe.get().cookingTime;
        } else if (normalRecipe.isPresent()) {
            maxCookingTime = normalRecipe.get().cookingTime;
        } else if (brightRecipe.isPresent()) {
            maxCookingTime = brightRecipe.get().cookingTime;
        } else {
            return;
        }
        this.playBarrelCloseSound();
        this.isRunning = true;
        this.setCookingTime(0);
        this.setMaxCookingTime(this.handleMaxCookingTime(maxCookingTime));
    }

    public void stopRunning() {
        this.playBarrelOpenSound();
        this.isRunning = false;
        this.setCookingTime(0);
        this.setMaxCookingTime(0);
    }

    public void craftItem() {
        BaseFermentationRecipe recipe;
        Optional<DimFermentationRecipe> dimRecipe = this.getDimRecipe();
        Optional<NormalFermentationRecipe> normalRecipe = this.getNormalRecipe();
        Optional<BrightFermentationRecipe> brightRecipe = this.getBrightRecipe();
        if (dimRecipe.isPresent()) {
            recipe = dimRecipe.get();
        } else if (normalRecipe.isPresent()) {
            recipe = normalRecipe.get();
        } else if (brightRecipe.isPresent()) {
            recipe = brightRecipe.get();
        } else {
            this.stopRunning();
            return;
        }
        ItemStack output = recipe.assemble(this, this.level.registryAccess());
        if (recipe.hasBottle()) {
            this.getItem(BOTTLE).shrink(this.outputMultiple);
        }
        List<ItemStack> craftRemaining = new ArrayList<>();
        for (int slot : INPUT_SLOTS) {
            ItemStack input = this.getItem(slot);
            if (input.isEmpty()) continue;
            ItemStack remaining = MachineUtil.getCraftRemaining(input, this.outputMultiple);
            if (ManorsBountyCompat.isDamageableMaterial(input)) {
                ManorsBountyCompat.damageItem(input, this.level);
                if (!input.isEmpty()) {
                    remaining = ItemStack.EMPTY;
                }
            } else {
                input.shrink(this.outputMultiple);
            }
            if (input.isEmpty() && !remaining.isEmpty()) {
                this.setItem(slot, remaining);
            } else if (!remaining.isEmpty()) {
                craftRemaining.add(remaining);
            }
            this.setItem(OUTPUT, output);
            this.insertCraftRemaining(craftRemaining);
            this.popCraftRemaining(craftRemaining);
        }
        this.stopRunning();
    }

    public void insertCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.insertCraftRemaining(this, INPUT_SLOTS, craftRemaining);
    }

    public void popCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.popCraftRemaining(this.level, this.getBlockPos(), craftRemaining);
    }

    public NonNullList<ItemStack> getInput() {
        NonNullList<ItemStack> input = NonNullList.withSize(INPUT_SLOTS.length, ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            input.set(i, this.getItem(INPUT_SLOTS[i]));
        }
        return input;
    }

    public void setCookingTime(int value) {
        this.cookingTime = value;
        this.setChanged();
    }

    public void setMaxCookingTime(int value) {
        this.maxCookingTime = value;
        this.setChanged();
    }

    public int handleMaxCookingTime(int value) {
        ItemStack catalystsTop = this.getItem(CATALYSTS_TOP);
        ItemStack catalystsBottom = this.getItem(CATALYSTS_BOTTOM);
        value = applyCatalystEffect(value, catalystsTop);
        value = applyCatalystEffect(value, catalystsBottom);
        return Math.max(value, 10);
    }

    private int applyCatalystEffect(int value, ItemStack catalyst) {
        if (catalyst.isEmpty()) return value;
        if (ManorsBountyCompat.isCatalystsA(catalyst)) {
            catalyst.shrink(1);
            return (int) (value * CATALYST_A_EFFECT);
        } else if (ManorsBountyCompat.isCatalystsB(catalyst)) {
            catalyst.shrink(1);
            return (int) (value * CATALYST_B_EFFECT);
        } else if (ManorsBountyCompat.isCatalystsC(catalyst)) {
            catalyst.shrink(1);
            return (int) (value * CATALYST_C_EFFECT);
        } else if (ManorsBountyCompat.isCatalystsX(catalyst)) {
            catalyst.shrink(1);
            return (int) (value * CATALYST_X_EFFECT);
        }
        return value;
    }

    public int getLightLevel() {
        return this.level.getRawBrightness(this.getBlockPos(), 0);
    }

    public LightState getLightState() {
        int lightLevel = this.getLightLevel();
        if (lightLevel <= 5) return LightState.DIM;
        if (lightLevel <= 10) return LightState.NORMAL;
        return LightState.BRIGHT;
    }

    public Optional<DimFermentationRecipe> getDimRecipe() {
        return this.dimRecipeCheck.getRecipeFor(this, this.level);
    }

    public Optional<NormalFermentationRecipe> getNormalRecipe() {
        return this.normalRecipeCheck.getRecipeFor(this, this.level);
    }

    public Optional<BrightFermentationRecipe> getBrightRecipe() {
        return this.brightRecipeCheck.getRecipeFor(this, this.level);
    }

    public List<DimFermentationRecipe> getAllDimRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.DIM_FERMENTATION_RECIPE_TYPE.get());
    }

    public List<NormalFermentationRecipe> getAllNormalRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.NORMAL_FERMENTATION_RECIPE_TYPE.get());
    }

    public List<BrightFermentationRecipe> getAllBrightRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.BRIGHT_FERMENTATION_RECIPE_TYPE.get());
    }

    public void playBarrelOpenSound() {
        this.level.playSound(null, this.getBlockPos(), SoundEvents.BARREL_OPEN, SoundSource.BLOCKS);
    }

    public void playBarrelCloseSound() {
        this.level.playSound(null, this.getBlockPos(), SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS);
    }

    public void setOpen(boolean open) {
        this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(FermenterBlock.OPEN, open));
    }

    @Override
    public void startOpen(Player pPlayer) {
        super.startOpen(pPlayer);
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player pPlayer) {
        super.stopOpen(pPlayer);
        if (!this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public IItemHandler getItemHandler() {
        return new InvWrapper(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FermenterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.isRunning = pTag.getBoolean("isRunning");
        this.cookingTime = pTag.getInt("cookingTime");
        this.maxCookingTime = pTag.getInt("maxCookingTime");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("isRunning", this.isRunning);
        pTag.putInt("cookingTime", this.cookingTime);
        pTag.putInt("maxCookingTime", this.maxCookingTime);
    }

    public enum LightState {
        DIM(0, 5),
        NORMAL(6, 10),
        BRIGHT(11, 15);
        public final int minLight;
        public final int maxLight;

        LightState(int minLight, int maxLight) {
            this.minLight = minLight;
            this.maxLight = maxLight;
        }
    }
}
