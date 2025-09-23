package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.item.ShakerItem;
import net.zhaiji.manorsbountymachine.register.InitItem;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

import static net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider.*;

public class ShakerMenu extends AbstractContainerMenu {
    public final Player player;
    public final Inventory playerInventory;
    public final IItemHandler itemHandler;
    public int playerInventoryYOffset;

    public ShakerMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory, new ItemStackHandler(ITEMS_SIZE));
    }

    public ShakerMenu(int pContainerId, Inventory pPlayerInventory, IItemHandler itemHandler) {
        super(InitMenuType.SHAKER.get(), pContainerId);
        this.player = pPlayerInventory.player;
        this.playerInventory = pPlayerInventory;
        if (!this.canOpen()) {
            this.removed(this.player);
        }
        this.itemHandler = itemHandler;
        this.playerInventoryYOffset = 42;
        this.initShakerSlot();
        this.initPlayerInventorySlot();
    }

    public ItemStack getShaker() {
        return this.playerInventory.getSelected();
    }

    public boolean canOpen() {
        return this.getShaker().is(InitItem.SHAKER.get());
    }

    public void initShakerSlot() {
        this.addSlot(new SlotItemHandler(this.itemHandler, OUTPUT, 80, 91) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack)
                        && !pStack.is(InitItem.SHAKER.get())
                        && SlotInputLimitManager.SHAKER_INPUT_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack));
            }
        });
        int[][] slots = {
                {TOP_LEFT, 62, 54},
                {TOP_CENTER, 80, 54},
                {TOP_RIGHT, 98, 54},
                {BOTTOM_LEFT, 62, 72},
                {BOTTOM_CENTER, 80, 72},
                {BOTTOM_RIGHT, 98, 72},
        };
        for (int[] slot : slots) {
            this.addSlot(new SlotItemHandler(this.itemHandler, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return super.mayPlace(pStack) && !pStack.is(InitItem.SHAKER.get());
                }
            });
        }
    }

    public void initPlayerInventorySlot() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(this.playerInventory, column + row * 9 + 9, 8 + column * 18, 103 + row * 18 + this.playerInventoryYOffset));
            }
        }
        for (int column = 0; column < 9; column++) {
            if (column == this.playerInventory.selected) {
                this.addSlot(new Slot(this.playerInventory, column, 8 + column * 18, 161 + this.playerInventoryYOffset) {
                    @Override
                    public boolean mayPlace(ItemStack pStack) {
                        return false;
                    }

                    @Override
                    public boolean mayPickup(Player pPlayer) {
                        return false;
                    }
                });
            } else {
                this.addSlot(new Slot(this.playerInventory, column, 8 + column * 18, 161 + this.playerInventoryYOffset));
            }
        }
    }

    @Override
    public void removed(Player pPlayer) {
        boolean flag = ShakerItem.getRecipe(pPlayer.level(), new RecipeWrapper((ItemStackHandler) this.itemHandler)).isPresent();
        ShakerItem.setCanStartUsing(this.getShaker(), flag);
        super.removed(pPlayer);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int size = this.itemHandler.getSlots();
            if (pIndex < size) {
                if (!this.moveItemStackTo(itemstack1, size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, size, false)) {
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
        return this.canOpen();
    }
}
