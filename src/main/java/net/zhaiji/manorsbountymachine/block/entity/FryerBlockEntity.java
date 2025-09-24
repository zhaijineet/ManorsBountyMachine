package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.block.FryerBlock;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;
import net.zhaiji.manorsbountymachine.menu.FryerMenu;
import net.zhaiji.manorsbountymachine.recipe.FastFryRecipe;
import net.zhaiji.manorsbountymachine.recipe.SlowFryRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitParticleType;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;
import net.zhaiji.manorsbountymachine.util.MachineUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FryerBlockEntity extends BaseMachineBlockEntity {
    public static final int ITEMS_SIZE = 5;
    public static final int FLUID_TANK_SLOT = 0;
    public static final int TOP_LEFT = 1;
    public static final int TOP_RIGHT = 2;
    public static final int BOTTOM_LEFT = 3;
    public static final int BOTTOM_RIGHT = 4;
    public static final int[] INPUT_SLOTS = {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT};
    public static final int FAST_COOKING_TIME = 80;
    public static final int SLOW_COOKING_TIME = 160;
    public static final int FAIL_COOKING_TIME = 280;
    public static final int SOUND_TIME = 20;
    public final RecipeManager.CachedCheck<FryerCraftContainer, FastFryRecipe> fastRecipeCheck;
    public final RecipeManager.CachedCheck<FryerCraftContainer, SlowFryRecipe> slowRecipeCheck;
    public final FluidTank fluidTank = new FluidTank(4000, ManorsBountyCompat::isOilFluid) {
        @Override
        protected void onContentsChanged() {
            BlockPos blockPos = FryerBlockEntity.this.getBlockPos();
            BlockState blockState = FryerBlockEntity.this.getBlockState();
            FryerBlockEntity.this.level.setBlockAndUpdate(blockPos, blockState.setValue(FryerBlock.HAS_OIL, !this.isEmpty()));
            FryerBlockEntity.this.setChanged();
        }
    };
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
    public final FryerCraftContainer[] fryerCraftContainers;
    public boolean isRunning = false;
    public int craftState = 0;
    public int cookingTime = 0;
    public final ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return FryerBlockEntity.this.cookingTime;
        }

        @Override
        public void set(int pIndex, int pValue) {
            FryerBlockEntity.this.setCookingTime(pValue);
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    public int playSoundCooldown = 0;
    public LazyOptional<IFluidHandler> fluidHandler = LazyOptional.empty();

    public FryerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.FRYER.get(), pPos, pBlockState);
        this.fastRecipeCheck = RecipeManager.createCheck(InitRecipe.FAST_FRY_RECIPE_TYPE.get());
        this.slowRecipeCheck = RecipeManager.createCheck(InitRecipe.SLOW_FRY_RECIPE_TYPE.get());
        FryerCraftContainer[] fryerCraftContainers = new FryerCraftContainer[INPUT_SLOTS.length];
        for (int i = 0; i < INPUT_SLOTS.length; i++) {
            fryerCraftContainers[i] = new FryerCraftContainer(this, INPUT_SLOTS[i]);
        }
        this.fryerCraftContainers = fryerCraftContainers;
    }

    public void addOilSplashParticle(Level level, BlockPos pos) {
        level.addParticle(
                InitParticleType.OIL_SPLASH.get(),
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                0.0D,
                0.0D,
                0.0D
        );
    }

    public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, FryerBlockEntity pBlockEntity) {
        if (pBlockEntity.isRunning) {
            pBlockEntity.addOilSplashParticle(pLevel, pPos);
            pBlockEntity.addOilSplashParticle(pLevel, pPos);
            pBlockEntity.addOilSplashParticle(pLevel, pPos);
            pBlockEntity.addOilSplashParticle(pLevel, pPos);
            pBlockEntity.addOilSplashParticle(pLevel, pPos);
        }
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, FryerBlockEntity pBlockEntity) {
        if (pBlockEntity.isRunning) {
            pBlockEntity.cookingTime++;
            if (pBlockEntity.playSoundCooldown <= 0) {
                pBlockEntity.playSoundCooldown = SOUND_TIME;
                pLevel.playSound(null, pBlockEntity.getBlockPos(), InitSoundEvent.FRYER_FRYING.get(), SoundSource.BLOCKS);
            }
            pBlockEntity.playSoundCooldown--;
            pBlockEntity.craftItem();
            pBlockEntity.setChanged();
        }
    }

    public void startRunning() {
        if (this.isRunning) return;
        if (this.fluidTank.getFluidAmount() < 250) return;
        int count = 0;
        boolean flag = false;
        for (FryerCraftContainer craftContainer : this.fryerCraftContainers) {
            ItemStack input = craftContainer.getItem();
            if (input.isEmpty()) continue;
            count += input.getCount();
            if (flag) continue;
            flag = this.findFastRecipe(input).isPresent() || this.findSmokingRecipe(input).isPresent() || this.findSlowRecipe(input).isPresent();
        }
        if (count == 0) return;
        if (!flag) return;
        this.isRunning = true;
        this.playSoundCooldown = 0;
        this.setCookingTime(0);
        if (this.level instanceof ServerLevel serverLevel && serverLevel.random.nextInt(10000) < count * 625) {
            this.fluidTank.drain(250, IFluidHandler.FluidAction.EXECUTE);
            this.syncData();
            this.handlerFluidSlot();
        }
        this.setChanged();
    }

    public void stopRunning() {
        this.isRunning = false;
        this.setCookingTime(0);
        this.craftState = 0;
        for (FryerCraftContainer craftContainer : this.fryerCraftContainers) {
            craftContainer.reset();
        }
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void craftItem() {
        if (this.craftState == 0 && this.cookingTime >= FryerBlockEntity.FAST_COOKING_TIME && this.cookingTime < FryerBlockEntity.SLOW_COOKING_TIME) {
            this.craftState++;
            this.fastCraftItem();
        } else if (this.craftState == 1 && this.cookingTime >= FryerBlockEntity.SLOW_COOKING_TIME && this.cookingTime < FryerBlockEntity.FAIL_COOKING_TIME) {
            this.craftState++;
            this.slowCraftItem();
        } else if (this.craftState == 2 && this.cookingTime >= FryerBlockEntity.FAIL_COOKING_TIME) {
            this.failCraftItem();
            this.stopRunning();
        }
    }

    public void setCookingTime(int value) {
        this.cookingTime = value;
        this.setChanged();
    }

    public void fastCraftItem() {
        List<ItemStack> craftRemaining = new ArrayList<>();
        for (FryerCraftContainer craftContainer : this.fryerCraftContainers) {
            craftContainer.tryFastCraftItem();
            this.handlerCraftRemaining(craftContainer, craftRemaining);
        }
        this.popCraftRemaining(craftRemaining);
        this.syncData();
    }

    public void slowCraftItem() {
        List<ItemStack> craftRemaining = new ArrayList<>();
        for (FryerCraftContainer craftContainer : this.fryerCraftContainers) {
            craftContainer.trySlowCraftItem();
            this.handlerCraftRemaining(craftContainer, craftRemaining);
        }
        this.popCraftRemaining(craftRemaining);
        this.syncData();
    }

    public void failCraftItem() {
        List<ItemStack> craftRemaining = new ArrayList<>();
        for (FryerCraftContainer craftContainer : this.fryerCraftContainers) {
            craftContainer.failCraftItem();
            this.handlerCraftRemaining(craftContainer, craftRemaining);
        }
        this.popCraftRemaining(craftRemaining);
        this.syncData();
    }

    public void syncData() {
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void handlerCraftRemaining(FryerCraftContainer container, List<ItemStack> craftRemaining) {
        ItemStack remaining = container.craftRemaining;
        if (!remaining.isEmpty()) {
            craftRemaining.add(remaining);
            container.resetCraftRemaining();
        }
    }

    public void popCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.popCraftRemaining(this.level, this.getBlockPos(), craftRemaining);
    }

    public Optional<FastFryRecipe> findFastRecipe(ItemStack input) {
        for (FastFryRecipe fastFryRecipe : this.getAllFastRecipe()) {
            if (fastFryRecipe.input.test(input)) {
                return Optional.of(fastFryRecipe);
            }
        }
        return Optional.empty();
    }

    public Optional<FastFryRecipe> findSmokingRecipe(ItemStack input) {
        for (FastFryRecipe fastFryRecipe : SmokingRecipeManager.fastFryRecipes) {
            if (fastFryRecipe.input.test(input)) {
                return Optional.of(fastFryRecipe);
            }
        }
        return Optional.empty();
    }

    public Optional<SlowFryRecipe> findSlowRecipe(ItemStack input) {
        for (SlowFryRecipe slowFryRecipe : this.getAllSlowRecipe()) {
            if (slowFryRecipe.input.test(input)) {
                return Optional.of(slowFryRecipe);
            }
        }
        return Optional.empty();
    }

    public Optional<FastFryRecipe> getFastRecipe(FryerCraftContainer craftContainer) {
        return this.fastRecipeCheck.getRecipeFor(craftContainer, this.level);
    }

    public List<FastFryRecipe> getAllFastRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.FAST_FRY_RECIPE_TYPE.get());
    }

    public Optional<SlowFryRecipe> getSlowRecipe(FryerCraftContainer craftContainer) {
        return this.slowRecipeCheck.getRecipeFor(craftContainer, this.level);
    }

    public List<SlowFryRecipe> getAllSlowRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.SLOW_FRY_RECIPE_TYPE.get());
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
        return new FryerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return this.fluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void loadAndRevive() {
        super.loadAndRevive();
        this.fluidHandler = LazyOptional.of(() -> this.fluidTank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.fluidHandler.invalidate();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.fluidTank.readFromNBT(pTag.getCompound("fluidTank"));
        this.isRunning = pTag.getBoolean("isRunning");
        this.craftState = pTag.getInt("craftState");
        this.cookingTime = pTag.getInt("cookingTime");
        ListTag listtag = pTag.getList("fryerCraftContainer", 10);
        for (int i = 0; i < this.fryerCraftContainers.length; i++) {
            this.fryerCraftContainers[i].originalItem = ItemStack.of(listtag.getCompound(i));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("fluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
        pTag.putBoolean("isRunning", this.isRunning);
        pTag.putInt("craftState", this.craftState);
        pTag.putInt("cookingTime", this.cookingTime);
        ListTag listtag = new ListTag();
        for (FryerCraftContainer craftContainer : this.fryerCraftContainers) {
            CompoundTag compoundtag = new CompoundTag();
            craftContainer.originalItem.save(compoundtag);
            listtag.add(compoundtag);
        }
        pTag.put("fryerCraftContainer", listtag);
    }

    public void handlerFluidSlot() {
        MachineUtil.handlerFluidSlot(FLUID_TANK_SLOT, this, this.fluidTank, this.level, this.getBlockPos());
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        super.setItem(pSlot, pStack);
        this.handlerFluidSlot();
    }

    public static class FryerCraftContainer implements Container {
        public FryerBlockEntity blockEntity;
        public int index;
        public ItemStack originalItem = ItemStack.EMPTY;
        public ItemStack craftRemaining = ItemStack.EMPTY;
        public SlowFryRecipe slowFryRecipe;

        public FryerCraftContainer(FryerBlockEntity blockEntity, int index) {
            this.blockEntity = blockEntity;
            this.index = index;
        }

        public void tryFastCraftItem() {
            ItemStack input = this.getItem();
            if (input.isEmpty()) return;
            this.originalItem = input.copy();
            Optional<FastFryRecipe> recipe = this.blockEntity.findFastRecipe(this.originalItem);
            if (recipe.isEmpty()) {
                recipe = this.blockEntity.findSmokingRecipe(this.originalItem);
            }
            if (recipe.isPresent()) {
                recipe.ifPresent(this::fastCraftItem);
            } else {
                this.blockEntity.findSlowRecipe(this.originalItem).ifPresent(slowFryRecipe -> this.slowFryRecipe = slowFryRecipe);
                if (this.slowFryRecipe == null) {
                    this.failCraftItem();
                }
            }
        }

        public void fastCraftItem(FastFryRecipe recipe) {
            this.setCraftRemaining(this.getItem());
            ItemStack output = recipe.assemble(this, this.blockEntity.level.registryAccess());
            this.setItem(output);
        }

        public void trySlowCraftItem() {
            ItemStack input = this.getItem();
            if (input.isEmpty()) return;
            if (this.slowFryRecipe != null) {
                this.slowCraftItem(this.slowFryRecipe);
                return;
            } else {
                Optional<SlowFryRecipe> recipe = this.blockEntity.findSlowRecipe(this.originalItem);
                if (recipe.isPresent()) {
                    recipe.ifPresent(this::slowCraftItem);
                    return;
                }
            }
            this.failCraftItem();
        }

        public void slowCraftItem(SlowFryRecipe recipe) {
            this.setCraftRemaining(this.getItem());
            ItemStack output = recipe.assemble(this, this.blockEntity.level.registryAccess());
            this.setItem(output);
        }

        public void failCraftItem() {
            ItemStack input = this.getItem();
            if (input.isEmpty()) return;
            this.setCraftRemaining(input);
            this.setItem(this.getFailItemStack(input));
        }

        public void setCraftRemaining(ItemStack input) {
            this.craftRemaining = MachineUtil.getCraftRemaining(input);
        }

        public ItemStack getFailItemStack(ItemStack input) {
//            return Items.CHARCOAL.getDefaultInstance().copyWithCount(input.getCount());
            return ManorsBountyCompat.getManorsBountyItemStack("cinder", input.getCount());
        }

        public void reset() {
            this.slowFryRecipe = null;
            this.resetOriginalItem();
            this.resetCraftRemaining();
        }

        public void resetOriginalItem() {
            this.originalItem = this.getItem().copy();
        }

        public void resetCraftRemaining() {
            this.craftRemaining = ItemStack.EMPTY;
        }

        @Override
        public int getContainerSize() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return blockEntity.getItem(this.index).isEmpty();
        }

        @Override
        public ItemStack getItem(int pSlot) {
            return blockEntity.getItem(this.index);
        }

        public ItemStack getItem() {
            return this.getItem(this.index);
        }

        public void setItem(ItemStack pStack) {
            this.setItem(this.index, pStack);
        }

        @Override
        public ItemStack removeItem(int pSlot, int pAmount) {
            return this.blockEntity.removeItem(pSlot, pAmount);
        }

        public ItemStack removeItem(int pAmount) {
            return this.removeItem(this.index, pAmount);
        }

        public ItemStack removeItemNoUpdate() {
            return this.removeItemNoUpdate(this.index);
        }

        @Override
        public ItemStack removeItemNoUpdate(int pSlot) {
            return this.blockEntity.removeItemNoUpdate(pSlot);
        }

        @Override
        public void setItem(int pSlot, ItemStack pStack) {
            this.blockEntity.setItem(pSlot, pStack);
        }

        @Override
        public void setChanged() {
            this.blockEntity.setChanged();
        }

        @Override
        public boolean stillValid(Player pPlayer) {
            return Container.stillValidBlockEntity(this.blockEntity, pPlayer);
        }

        @Override
        public void clearContent() {
            this.blockEntity.setItem(index, ItemStack.EMPTY);
        }
    }
}
