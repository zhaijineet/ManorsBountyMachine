package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.menu.SaucepanAndWhiskMenu;
import net.zhaiji.manorsbountymachine.recipe.SaucepanAndWhiskRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import net.zhaiji.manorsbountymachine.util.MachineUtil;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaucepanAndWhiskBlockEntity extends BaseMachineBlockEntity implements GeoBlockEntity {
    public static final int ITEMS_SIZE = 11;
    public static final int OUTPUT = 0;
    public static final int MAIN_TOP_LEFT = 1;
    public static final int MAIN_TOP_CENTER = 2;
    public static final int MAIN_TOP_RIGHT = 3;
    public static final int MAIN_BOTTOM_LEFT = 4;
    public static final int MAIN_BOTTOM_CENTER = 5;
    public static final int MAIN_BOTTOM_RIGHT = 6;
    public static final int SECONDARY_TOP_LEFT = 7;
    public static final int SECONDARY_TOP_RIGHT = 8;
    public static final int SECONDARY_BOTTOM_LEFT = 9;
    public static final int SECONDARY_BOTTOM_RIGHT = 10;
    public static final int[] MAIN_INPUT_SLOTS = {MAIN_TOP_LEFT, MAIN_TOP_CENTER, MAIN_TOP_RIGHT, MAIN_BOTTOM_LEFT, MAIN_BOTTOM_CENTER, MAIN_BOTTOM_RIGHT};
    public static final int[] SECONDARY_INPUT_SLOTS = {SECONDARY_TOP_LEFT, SECONDARY_TOP_RIGHT, SECONDARY_BOTTOM_LEFT, SECONDARY_BOTTOM_RIGHT};
    public static final int[] INPUT_SLOTS = {OUTPUT, MAIN_TOP_LEFT, MAIN_TOP_CENTER, MAIN_TOP_RIGHT, MAIN_BOTTOM_LEFT, MAIN_BOTTOM_CENTER, MAIN_BOTTOM_RIGHT, SECONDARY_TOP_LEFT, SECONDARY_TOP_RIGHT, SECONDARY_BOTTOM_LEFT, SECONDARY_BOTTOM_RIGHT};
    public static final int MAX_STIRS_COUNT = 6;
    public static final RawAnimation DEPLOY_ANIM = RawAnimation.begin().then("animation.saucepan_and_whisk.working", Animation.LoopType.PLAY_ONCE);
    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public final RecipeManager.CachedCheck<SaucepanAndWhiskBlockEntity, SaucepanAndWhiskRecipe> recipeCheck;
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
    public int stirsCount = 0;
    public ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> SaucepanAndWhiskBlockEntity.this.stirsCount;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> SaucepanAndWhiskBlockEntity.this.setStirsCount(pValue);
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public SaucepanAndWhiskBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.SAUCEPAN_AND_WHISK.get(), pPos, pBlockState);
        this.recipeCheck = RecipeManager.createCheck(InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_TYPE.get());
    }

    public void craftItem() {
        this.getRecipe().ifPresent(saucepanAndWhiskRecipe -> {
            List<ItemStack> craftRemaining = new ArrayList<>();
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
            this.setItem(OUTPUT, saucepanAndWhiskRecipe.assemble(this, this.level.registryAccess()));
            this.insertCraftRemaining(craftRemaining);
            this.popCraftRemaining(craftRemaining);
        });
        this.resetStirsCount();
    }

    public void insertCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.insertCraftRemaining(this, INPUT_SLOTS, craftRemaining);
    }

    public void popCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.popCraftRemaining(this.level, this.getBlockPos(), craftRemaining);
    }

    public boolean onHeatBlock() {
        return ManorsBountyCompat.isSaucepanAndWhiskHeatBlock(this.level.getBlockState(this.getBlockPos().below()));
    }

    public void setStirsCount(int value) {
        this.stirsCount = value;
        if (this.stirsCount > MAX_STIRS_COUNT) {
            this.stirsCount = 0;
        }
        this.setChanged();
    }

    public void addStirsCount() {
        this.setStirsCount(this.stirsCount + 1);
    }

    public void resetStirsCount() {
        this.setStirsCount(0);
    }

    public Optional<SaucepanAndWhiskRecipe> getRecipe() {
        return this.recipeCheck.getRecipeFor(this, this.level);
    }

    public List<SaucepanAndWhiskRecipe> getAllRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_TYPE.get());
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
    public void setItem(int pSlot, ItemStack pStack) {
        this.resetStirsCount();
        super.setItem(pSlot, pStack);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        this.resetStirsCount();
        return super.removeItem(pSlot, pAmount);
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
        return new SaucepanAndWhiskMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.setStirsCount(pTag.getInt("stirsCount"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("stirsCount", this.stirsCount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(
                        this,
                        "saucepan_and_whisk",
                        this::deployAnimController
                )
                        .triggerableAnim("animation.saucepan_and_whisk.working", DEPLOY_ANIM)
        );
    }

    protected <E extends SaucepanAndWhiskBlockEntity> PlayState deployAnimController(final AnimationState<E> state) {
        state.getController().setAnimation(DEPLOY_ANIM);
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
