package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

public class OvenMenu extends BaseMachineMenu {
    public OvenBlockEntity blockEntity;
    public ContainerData data;

    public OvenMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (OvenBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
    }

    public OvenMenu(int pContainerId, Inventory pPlayerInventory, OvenBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.OVEN.get(), pContainerId, pPlayerInventory, blockEntity);
        this.blockEntity = blockEntity;
        this.data = data;
        this.initSlot();
        this.addDataSlots(data);
    }

    public OvenBlockEntity.Temperature getTemperature() {
        return switch (this.data.get(0)) {
            case 1, 200 -> OvenBlockEntity.Temperature.TWO_HUNDRED;
            case 2, 150 -> OvenBlockEntity.Temperature.ONE_HUNDRED_FIFTY;
            case 3, 250 -> OvenBlockEntity.Temperature.TWO_HUNDRED_FIFTY;
            default -> OvenBlockEntity.Temperature.ZERO;
        };
    }

    public int getCookingTime() {
        return this.data.get(1);
    }

    public OvenBlockEntity.MaxCookingTime getMaxCookingTime() {
        return switch (this.data.get(2)) {
            case 1, 5, 100 -> OvenBlockEntity.MaxCookingTime.FIVE;
            case 2, 10, 200 -> OvenBlockEntity.MaxCookingTime.TEN;
            case 3, 15, 300 -> OvenBlockEntity.MaxCookingTime.FIFTEEN;
            default -> OvenBlockEntity.MaxCookingTime.ZERO;
        };
    }

    public void setMaxCookingTime(int value) {
        this.data.set(2, value);
    }

    @Override
    public void initMachineInventorySlot() {
        int[][] slots = {
                {OvenBlockEntity.TOP_LEFT, 52, 52},
                {OvenBlockEntity.TOP_CENTER, 80, 52},
                {OvenBlockEntity.TOP_RIGHT, 108, 52},
                {OvenBlockEntity.BOTTOM_LEFT, 52, 78},
                {OvenBlockEntity.BOTTOM_CENTER, 80, 78},
                {OvenBlockEntity.BOTTOM_RIGHT, 108, 78}
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return super.mayPlace(pStack) && SlotInputLimitManager.OVEN_INPUT_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack));
                }

                @Override
                public boolean mayPickup(Player pPlayer) {
                    return super.mayPickup(pPlayer) && getCookingTime() == 0;
                }

                @Override
                public int getMaxStackSize() {
                    return 16;
                }
            });
        }
        this.addSlot(new Slot(this.blockEntity, OvenBlockEntity.OUTPUT, 80, 65) {
            @Override
            public boolean isActive() {
                return super.isActive() && this.hasItem();
            }
        });
    }
}
