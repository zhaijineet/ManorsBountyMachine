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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.block.OvenBlock;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;
import net.zhaiji.manorsbountymachine.menu.OvenMenu;
import net.zhaiji.manorsbountymachine.recipe.OvenRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;
import net.zhaiji.manorsbountymachine.util.MachineUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OvenBlockEntity extends BaseMachineBlockEntity {
    public static final int ITEMS_SIZE = 7;
    public static final int TOP_LEFT = 0;
    public static final int TOP_CENTER = 1;
    public static final int TOP_RIGHT = 2;
    public static final int BOTTOM_LEFT = 3;
    public static final int BOTTOM_CENTER = 4;
    public static final int BOTTOM_RIGHT = 5;
    public static final int OUTPUT = 6;
    public static final int[] INPUT_SLOTS = {TOP_LEFT, TOP_CENTER, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT};
    public static final int SOUND_TIME = 100;
    public final RecipeManager.CachedCheck<OvenBlockEntity, OvenRecipe> recipeCheck;
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
    public boolean isRunning = false;
    public Temperature temperature = Temperature.ZERO;
    public int cookingTime = 0;
    public MaxCookingTime maxCookingTime = MaxCookingTime.ZERO;
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> OvenBlockEntity.this.temperature.temperature;
                case 1 -> OvenBlockEntity.this.cookingTime;
                case 2 -> OvenBlockEntity.this.maxCookingTime.cookingTime;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> OvenBlockEntity.this.setTemperature(pValue);
                case 1 -> OvenBlockEntity.this.setCookingTime(pValue);
                case 2 -> OvenBlockEntity.this.setMaxCookingTime(pValue);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    public int playSoundCooldown = 0;

    public OvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.OVEN.get(), pPos, pBlockState);
        this.recipeCheck = RecipeManager.createCheck(InitRecipe.OVEN_RECIPE_TYPE.get());
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, OvenBlockEntity pBlockEntity) {
        if (pBlockEntity.isRunning) {
            pBlockEntity.cookingTime++;
            if (pBlockEntity.cookingTime >= pBlockEntity.maxCookingTime.cookingTime) {
                pBlockEntity.craftItem();
                pLevel.playSound(null, pBlockEntity.getBlockPos(), InitSoundEvent.OVEN_DING.get(), SoundSource.BLOCKS);
            }
            if (pBlockEntity.playSoundCooldown <= 0) {
                pBlockEntity.playSoundCooldown = SOUND_TIME;
                pLevel.playSound(null, pBlockEntity.getBlockPos(), InitSoundEvent.OVEN_RUNNING.get(), SoundSource.BLOCKS);
            }
            pBlockEntity.playSoundCooldown--;
            pBlockEntity.setChanged();
        }
    }

    public void startRunning() {
        if (this.isRunning) return;
        if (this.temperature == Temperature.ZERO) return;
        if (this.maxCookingTime == MaxCookingTime.ZERO) return;
        if (!this.getItem(OUTPUT).isEmpty()) return;
        if (this.getRecipe().isEmpty() && this.getSmokingRecipe().isEmpty()) return;
        this.isRunning = true;
        this.playSoundCooldown = 0;
        this.setRunningState(true);
        this.setCookingTime(0);
    }

    public void stopRunning() {
        this.isRunning = false;
        this.setRunningState(false);
        this.setCookingTime(0);
        this.setMaxCookingTime(0);
    }

    public void craftItem() {
        Optional<OvenRecipe> recipe = this.getRecipe();
        if (recipe.isEmpty()) {
            recipe = this.getSmokingRecipe();
        }
        List<ItemStack> craftRemaining = new ArrayList<>();
        ItemStack output;
        if (recipe.isPresent() && recipe.get().isStateMatch(this)) {
            output = recipe.get().assemble(this, this.level.registryAccess());
            this.handlerCraft(craftRemaining);
        } else {
            output = this.getFailItemStack();
            this.handlerCraft(craftRemaining);
        }
        this.setItem(OUTPUT, output);
        this.insertCraftRemaining(craftRemaining);
        this.popCraftRemaining(craftRemaining);
        this.stopRunning();
    }

    public void handlerCraft(List<ItemStack> craftRemaining) {
        for (int slot : INPUT_SLOTS) {
            ItemStack input = this.getItem(slot);
            if (input.isEmpty()) continue;
            ItemStack remaining = MachineUtil.getCraftRemaining(input, 1);
            if (ManorsBountyCompat.isDamageableMaterial(input)) {
                ManorsBountyCompat.damageItem(input, this.level);
                if (!input.isEmpty()) {
                    remaining = ItemStack.EMPTY;
                }
            } else {
                input.shrink(1);
            }
            if (input.isEmpty() && !remaining.isEmpty()) {
                this.setItem(slot, remaining);
            } else if (!remaining.isEmpty()) {
                craftRemaining.add(remaining);
            }
        }
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

    public ItemStack getFailItemStack() {
        return Items.CHARCOAL.getDefaultInstance().copyWithCount(1);
    }

    public void setRunningState(boolean isRunning) {
        this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(OvenBlock.RUNNING, isRunning));
    }

    public void setTemperature(int value) {
        switch (value) {
            case 0 -> this.temperature = Temperature.ZERO;
            case 1, 150 -> this.temperature = Temperature.ONE_HUNDRED_FIFTY;
            case 2, 200 -> this.temperature = Temperature.TWO_HUNDRED;
            case 3, 250 -> this.temperature = Temperature.TWO_HUNDRED_FIFTY;
        }
        this.setChanged();
    }

    public void setCookingTime(int value) {
        this.cookingTime = value;
        this.setChanged();
    }

    public void setMaxCookingTime(int value) {
        switch (value) {
            case 0 -> this.maxCookingTime = MaxCookingTime.ZERO;
            case 1, 5, 100 -> this.maxCookingTime = MaxCookingTime.FIVE;
            case 2, 10, 200 -> this.maxCookingTime = MaxCookingTime.TEN;
            case 3, 15, 300 -> this.maxCookingTime = MaxCookingTime.FIFTEEN;
        }
        this.setChanged();
    }

    public Optional<OvenRecipe> getRecipe() {
        return this.recipeCheck.getRecipeFor(this, this.level);
    }

    public Optional<OvenRecipe> getSmokingRecipe() {
        for (OvenRecipe ovenRecipe : SmokingRecipeManager.ovenRecipes) {
            if (ovenRecipe.matches(this, this.level)) {
                return Optional.of(ovenRecipe);
            }
        }
        return Optional.empty();
    }

    public List<OvenRecipe> getAllRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.OVEN_RECIPE_TYPE.get());
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
        return new OvenMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.isRunning = pTag.getBoolean("isRunning");
        this.setCookingTime(pTag.getInt("cookingTime"));
        this.setTemperature(pTag.getInt("temperature"));
        this.setMaxCookingTime(pTag.getInt("maxCookingTime"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("isRunning", this.isRunning);
        pTag.putInt("cookingTime", this.cookingTime);
        pTag.putInt("temperature", this.temperature.temperature);
        pTag.putInt("maxCookingTime", this.maxCookingTime.cookingTime);
    }

    public enum Temperature {
        ZERO(0),
        ONE_HUNDRED_FIFTY(150),
        TWO_HUNDRED(200),
        TWO_HUNDRED_FIFTY(250);

        public final int state;
        public final int temperature;
        public final String temperatureName;

        Temperature(int temperature) {
            this.state = Math.max(temperature / 50 - 2, 0);
            this.temperature = temperature;
            this.temperatureName = temperature + "°";
        }
    }

    public enum MaxCookingTime {
        ZERO(0),
        FIVE(5),
        TEN(10),
        FIFTEEN(15);

        public static final MaxCookingTime LAST = FIFTEEN;
        public final int state;
        public final int second;
        public final String secondName;
        public final int cookingTime;

        MaxCookingTime(int second) {
            this.state = second / 5;
            this.second = second;
            this.secondName = second + "s";
            this.cookingTime = second * 20;
        }
    }
}
