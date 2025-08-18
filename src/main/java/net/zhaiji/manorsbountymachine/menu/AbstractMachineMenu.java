package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.block.entity.AbstractMachineBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractMachineMenu extends AbstractContainerMenu {
    public final Inventory playerInventory;
    public final AbstractMachineBlockEntity machineBlockEntity;

    public AbstractMachineMenu(@Nullable MenuType<?> pMenuType, int pContainerId, Inventory pPlayerInventory, AbstractMachineBlockEntity machineBlockEntity) {
        super(pMenuType, pContainerId);
        this.playerInventory = pPlayerInventory;
        this.machineBlockEntity = machineBlockEntity;
    }

    public void initSlot() {
        this.initMachineInventorySlot();
        this.initPlayerInventorySlot();
    }

    public abstract void initMachineInventorySlot();

    public void initPlayerInventorySlot() {
        int yOffset = 42;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(this.playerInventory, column + row * 9 + 9, 8 + column * 18, 103 + row * 18 + yOffset));
            }
        }
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(this.playerInventory, column, 8 + column * 18, 161 + yOffset));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < this.machineBlockEntity.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.machineBlockEntity.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.machineBlockEntity.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.machineBlockEntity.stillValid(pPlayer);
    }
}
