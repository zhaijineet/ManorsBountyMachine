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

import static net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity.*;

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

    public Temperature getTemperature() {
        return switch (this.data.get(0)) {
            case 1, 200 -> Temperature.TWO_HUNDRED;
            case 2, 150 -> Temperature.ONE_HUNDRED_FIFTY;
            case 3, 250 -> Temperature.TWO_HUNDRED_FIFTY;
            default -> Temperature.ZERO;
        };
    }

    public int getCookingTime() {
        return this.data.get(1);
    }

    public MaxCookingTime getMaxCookingTime() {
        return switch (this.data.get(2)) {
            case 1, 5, 100 -> MaxCookingTime.FIVE;
            case 2, 10, 200 -> MaxCookingTime.TEN;
            case 3, 15, 300 -> MaxCookingTime.FIFTEEN;
            default -> MaxCookingTime.ZERO;
        };
    }

    public void setMaxCookingTime(int value) {
        this.data.set(2, value);
    }

    @Override
    public void initMachineInventorySlot() {
        int[][] slots = {
                {TOP_LEFT, 52, 52},
                {TOP_CENTER, 80, 52},
                {TOP_RIGHT, 108, 52},
                {BOTTOM_LEFT, 52, 78},
                {BOTTOM_CENTER, 80, 78},
                {BOTTOM_RIGHT, 108, 78}
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return super.mayPlace(pStack)
                            && SlotInputLimitManager.OVEN_INPUT_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack))
                            && getCookingTime() == 0;
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
        this.addSlot(new Slot(this.blockEntity, OUTPUT, 80, 65) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player pPlayer) {
                return super.mayPickup(pPlayer) && getCookingTime() == 0;
            }

            @Override
            public boolean isActive() {
                return super.isActive() && this.hasItem();
            }
        });
    }
}
