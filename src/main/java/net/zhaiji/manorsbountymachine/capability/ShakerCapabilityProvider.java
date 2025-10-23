package net.zhaiji.manorsbountymachine.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShakerCapabilityProvider extends CapabilityProvider<ShakerCapabilityProvider> implements INBTSerializable<CompoundTag> {
    public static final int ITEMS_SIZE = 7;
    public static final int OUTPUT = 0;
    public static final int TOP_LEFT = 1;
    public static final int TOP_CENTER = 2;
    public static final int TOP_RIGHT = 3;
    public static final int BOTTOM_LEFT = 4;
    public static final int BOTTOM_CENTER = 5;
    public static final int BOTTOM_RIGHT = 6;
    public static final int[] INPUT_SLOTS = {TOP_LEFT, TOP_CENTER, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT};

    public ItemStackHandler itemStackHandler = new ItemStackHandler(ITEMS_SIZE){
        @Override
        public int getSlotLimit(int slot) {
            return 4;
        }
    };
    public LazyOptional<IItemHandler> itemHandler = LazyOptional.empty();

    public ShakerCapabilityProvider() {
        super(ShakerCapabilityProvider.class);
        this.loadAndRevive();
    }

    public ItemStackHandler getItemHandler() {
        return this.itemStackHandler;
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.itemStackHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.itemStackHandler.deserializeNBT(nbt);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void loadAndRevive() {
        this.itemHandler = LazyOptional.of(this::getItemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.loadAndRevive();
    }
}
