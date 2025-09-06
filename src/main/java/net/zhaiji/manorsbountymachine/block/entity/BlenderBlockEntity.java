package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.menu.BlenderMenu;
import net.zhaiji.manorsbountymachine.recipe.BlenderRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;
import net.zhaiji.manorsbountymachine.util.MachineUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlenderBlockEntity extends BaseMachineBlockEntity {
    public static final int ITEMS_SIZE = 13;
    public static final int CONTAINER = 0;
    public static final int MAIN_TOP_LEFT = 1;
    public static final int MAIN_TOP_RIGHT = 2;
    public static final int MAIN_CENTER_LEFT = 3;
    public static final int MAIN_CENTER_RIGHT = 4;
    public static final int MAIN_BOTTOM_LEFT = 5;
    public static final int MAIN_BOTTOM_RIGHT = 6;
    public static final int SECONDARY_TOP_LEFT = 7;
    public static final int SECONDARY_TOP_RIGHT = 8;
    public static final int SECONDARY_BOTTOM_LEFT = 9;
    public static final int SECONDARY_BOTTOM_RIGHT = 10;
    public static final int SECONDARY_OUTPUT = 11;
    public static final int MAIN_OUTPUT = 12;
    public static final int[] MAIN_INPUT_SLOTS = {MAIN_TOP_LEFT, MAIN_TOP_RIGHT, MAIN_CENTER_LEFT, MAIN_CENTER_RIGHT, MAIN_BOTTOM_LEFT, MAIN_BOTTOM_RIGHT};
    public static final int[] SECONDARY_INPUT_SLOTS = {SECONDARY_TOP_LEFT, SECONDARY_TOP_RIGHT, SECONDARY_BOTTOM_LEFT, SECONDARY_BOTTOM_RIGHT};
    public static final int[] INPUT_SLOTS = {CONTAINER, MAIN_TOP_LEFT, MAIN_TOP_RIGHT, MAIN_CENTER_LEFT, MAIN_CENTER_RIGHT, MAIN_BOTTOM_LEFT, MAIN_BOTTOM_RIGHT, SECONDARY_TOP_LEFT, SECONDARY_TOP_RIGHT, SECONDARY_BOTTOM_LEFT, SECONDARY_BOTTOM_RIGHT};
    public final RecipeManager.CachedCheck<BlenderBlockEntity, BlenderRecipe> recipeCheck;
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
    public boolean isRunning = false;
    public int cookingTime = 0;
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> BlenderBlockEntity.this.cookingTime;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> BlenderBlockEntity.this.setCookingTime(pValue);
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    public int maxCookingTime = 60;
    public int outputMultiple = 0;

    public BlenderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.BLENDER.get(), pPos, pBlockState);
        this.recipeCheck = RecipeManager.createCheck(InitRecipe.BLENDER_RECIPE_TYPE.get());
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, BlenderBlockEntity pBlockEntity) {
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
        if (!this.getItem(MAIN_OUTPUT).isEmpty() || !this.getItem(SECONDARY_OUTPUT).isEmpty()) return;
        if (this.getRecipe().isEmpty()) return;
        this.level.playSound(null, this.getBlockPos(), InitSoundEvent.BLENDER_RUNNING.get(), SoundSource.BLOCKS);
        this.isRunning = true;
        this.setCookingTime(0);
    }

    public void stopRunning() {
        this.isRunning = false;
        this.setCookingTime(0);
    }

    public void craftItem() {
        this.getRecipe().ifPresent(blenderRecipe -> {
            ItemStack output = blenderRecipe.assemble(this, this.level.registryAccess());
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
                    input.shrink(slot == CONTAINER ? output.getCount() : this.outputMultiple);
                }
                if (input.isEmpty() && !remaining.isEmpty()) {
                    this.setItem(slot, remaining);
                } else if (!remaining.isEmpty()) {
                    craftRemaining.add(remaining);
                }
            }
            //TODO 这里需要一个检测物品是粉状还是液体的标签，用于配方完成播放音效
//            if (output.is()) {
//                this.level.playSound(null, this.getBlockPos(), InitSoundEvent.BLENDER_POWDER_DONE.get(), SoundSource.BLOCKS);
//            } else if (output.is()) {
//                this.level.playSound(null, this.getBlockPos(), InitSoundEvent.BLENDER_WATER_DONE.get(), SoundSource.BLOCKS);
//            } else {
//                this.level.playSound(null, this.getBlockPos(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS);
//            }
            this.setItem(MAIN_OUTPUT, output);
            ItemStack outgrowth = blenderRecipe.getOutgrowth(this.level);
            if (!outgrowth.isEmpty()) {
                this.setItem(SECONDARY_OUTPUT, outgrowth);
            }
            this.insertCraftRemaining(craftRemaining);
            this.popCraftRemaining(craftRemaining);
        });
        this.stopRunning();
    }

    public void insertCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.insertCraftRemaining(this, INPUT_SLOTS, craftRemaining);
    }

    public void popCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.popCraftRemaining(this.level, this.getBlockPos(), craftRemaining);
    }

    public void setCookingTime(int value) {
        this.cookingTime = value;
        this.setChanged();
    }

    public Optional<BlenderRecipe> getRecipe() {
        return this.recipeCheck.getRecipeFor(this, this.level);
    }

    public List<BlenderRecipe> getAllRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.BLENDER_RECIPE_TYPE.get());
    }

    public NonNullList<ItemStack> getMainInput() {
        NonNullList<ItemStack> input = NonNullList.withSize(MAIN_INPUT_SLOTS.length, ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            input.set(i, this.getItem(MAIN_INPUT_SLOTS[i]));
        }
        return input;
    }

    public NonNullList<ItemStack> getSecondaryInput() {
        NonNullList<ItemStack> input = NonNullList.withSize(SECONDARY_INPUT_SLOTS.length, ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            input.set(i, this.getItem(SECONDARY_INPUT_SLOTS[i]));
        }
        return input;
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
        return new BlenderMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.isRunning = pTag.getBoolean("isRunning");
        this.cookingTime = pTag.getInt("cookingTime");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("isRunning", this.isRunning);
        pTag.putInt("cookingTime", this.cookingTime);
    }
}
