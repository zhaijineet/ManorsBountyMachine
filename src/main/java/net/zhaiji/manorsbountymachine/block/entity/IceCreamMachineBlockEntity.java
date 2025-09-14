package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.menu.IceCreamMachineMenu;
import net.zhaiji.manorsbountymachine.recipe.IceCreamRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import net.zhaiji.manorsbountymachine.util.MachineUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IceCreamMachineBlockEntity extends BaseMachineBlockEntity {
    public static final int ITEMS_SIZE = 4;
    public static final int FLUID_TANK_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int LEFT_INPUT_SLOT = 2;
    public static final int RIGHT_INPUT_SLOT = 3;
    public static final int[] INPUT_SLOTS = {OUTPUT_SLOT, LEFT_INPUT_SLOT, RIGHT_INPUT_SLOT};
    public static final int[] MATERIAL_SLOTS = {LEFT_INPUT_SLOT, RIGHT_INPUT_SLOT};
    public final RecipeManager.CachedCheck<IceCreamMachineBlockEntity, IceCreamRecipe> recipeCheck;
    public final FluidTank fluidTank = new FluidTank(3000, ManorsBountyCompat::isIceCreamFluid) {
        @Override
        protected void onContentsChanged() {
            IceCreamMachineBlockEntity.this.setChanged();
        }
    };
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
    public boolean isTwoFlavor = false;
    public LazyOptional<IFluidHandler> fluidHandler = LazyOptional.empty();

    public IceCreamMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.ICE_CREAM_MACHINE.get(), pPos, pBlockState);
        this.recipeCheck = RecipeManager.createCheck(InitRecipe.ICE_CREAM_RECIPE_TYPE.get());
    }

    public void craftItem() {
        Optional<IceCreamRecipe> recipe = this.getRecipe();
        recipe.ifPresent(iceCreamRecipe -> {
            this.fluidTank.drain(iceCreamRecipe.fluidStack.getAmount(), IFluidHandler.FluidAction.EXECUTE);
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            List<ItemStack> craftRemaining = new ArrayList<>();
            for (int i = 0; i < (this.isTwoFlavor ? 3 : 2); i++) {
                if (OUTPUT_SLOT == INPUT_SLOTS[i]) continue;
                ItemStack input = this.getItem(INPUT_SLOTS[i]);
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
                    this.setItem(INPUT_SLOTS[i], remaining);
                } else if (!remaining.isEmpty()) {
                    craftRemaining.add(remaining);
                }
            }
            this.setItem(OUTPUT_SLOT, iceCreamRecipe.assemble(this, this.level.registryAccess()));
            this.insertCraftRemaining(craftRemaining);
            this.popCraftRemaining(craftRemaining);
        });
    }

    public void insertCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.insertCraftRemaining(this, INPUT_SLOTS, craftRemaining);
    }

    public void popCraftRemaining(List<ItemStack> craftRemaining) {
        MachineUtil.popCraftRemaining(this.level, this.getBlockPos(), craftRemaining);
    }

    public void setTwoFlavor(boolean isTwoFlavor) {
        this.isTwoFlavor = isTwoFlavor;
        this.setChanged();
    }

    public Optional<IceCreamRecipe> getRecipe() {
        return this.recipeCheck.getRecipeFor(this, this.level);
    }

    public List<IceCreamRecipe> getAllRecipe() {
        return this.level.getRecipeManager().getAllRecipesFor(InitRecipe.ICE_CREAM_RECIPE_TYPE.get());
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
        return new IceCreamMachineMenu(pContainerId, pPlayerInventory, this);
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
        this.isTwoFlavor = pTag.getBoolean("isTwoFlavor");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("fluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
        pTag.putBoolean("isTwoFlavor", this.isTwoFlavor);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        super.setItem(pSlot, pStack);
        MachineUtil.handlerFluidSlot(FLUID_TANK_SLOT, this, this.fluidTank, this.level, this.getBlockPos());
    }
}
