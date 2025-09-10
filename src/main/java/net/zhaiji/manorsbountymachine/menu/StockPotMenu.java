package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

import static net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity.*;

public class StockPotMenu extends BaseMachineMenu {
    public StockPotBlockEntity blockEntity;
    public ContainerData data;

    public StockPotMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (StockPotBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(21));
    }

    public StockPotMenu(int pContainerId, Inventory pPlayerInventory, StockPotBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.STOCK_POT.get(), pContainerId, pPlayerInventory, blockEntity);
        this.blockEntity = blockEntity;
        this.data = data;
        this.playerInventoryYOffset += 5;
        this.initSlot();
        this.addDataSlots(data);
    }

    public int getCookingTime() {
        return this.data.get(0);
    }

    public int getMaxCookingTime() {
        return this.data.get(1);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.blockEntity.stopOpen(pPlayer);
    }

    @Override
    public void initMachineInventorySlot() {
        int[][] slots = {
                {OUTPUT, 80, 81},
                {MAIN_TOP_LEFT, 51, 102},
                {MAIN_TOP_CENTER_LEFT, 70, 102},
                {MAIN_TOP_CENTER_RIGHT, 90, 102},
                {MAIN_TOP_RIGHT, 109, 102},
                {MAIN_BOTTOM_LEFT, 51, 121},
                {MAIN_BOTTOM_CENTER_LEFT, 70, 121},
                {MAIN_BOTTOM_CENTER_RIGHT, 90, 121},
                {MAIN_BOTTOM_RIGHT, 109, 121},
                {SECONDARY_TOP_LEFT, 31, 27},
                {SECONDARY_TOP_RIGHT, 129, 27},
                {SECONDARY_BOTTOM_LEFT, 26, 47},
                {SECONDARY_BOTTOM_RIGHT, 134, 47}
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPickup(Player pPlayer) {
                    return super.mayPickup(pPlayer) && getCookingTime() == 0;
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean isActive() {
                    return super.isActive() && getCookingTime() == 0;
                }
            });
        }
    }
}
