package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseHasItemBlockEntity extends BlockEntity implements Container {
    public LazyOptional<IItemHandler> itemHandler = LazyOptional.empty();

    public BaseHasItemBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.loadAndRevive();
    }

    public abstract NonNullList<ItemStack> getItems();

    public abstract IItemHandler getItemHandler();

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

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.getItems().clear();
        ContainerHelper.loadAllItems(pTag, this.getItems());
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, this.getItems());
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return false;
    }

    @Override
    public boolean canTakeItem(Container pTarget, int pIndex, ItemStack pStack) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return this.getItems().size();
    }

    public boolean isFull() {
        int count = 0;
        for (ItemStack itemstack : this.getItems()) {
            if (!itemstack.isEmpty()) {
                count++;
            }
        }
        return count == this.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.getItems()) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return pSlot >= 0 && pSlot < this.getItems().size() ? this.getItems().get(pSlot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), pSlot, pAmount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }
        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        ItemStack itemstack = this.getItems().get(pSlot);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.getItems().set(pSlot, ItemStack.EMPTY);
            return itemstack;
        }
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        this.getItems().set(pSlot, pStack);
        if (!pStack.isEmpty() && pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        this.getItems().clear();
        this.setChanged();
    }
}
