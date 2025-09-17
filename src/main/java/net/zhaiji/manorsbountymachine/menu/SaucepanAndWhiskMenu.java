package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

import static net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity.*;

public class SaucepanAndWhiskMenu extends BaseMachineMenu {
    public SaucepanAndWhiskBlockEntity blockEntity;
    public ContainerData data;

    public SaucepanAndWhiskMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (SaucepanAndWhiskBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public SaucepanAndWhiskMenu(int pContainerId, Inventory pPlayerInventory, SaucepanAndWhiskBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.SAUCEPAN_AND_WHISK.get(), pContainerId, pPlayerInventory, blockEntity);
        this.blockEntity = blockEntity;
        this.data = data;
        this.playerInventoryYOffset += 9;
        this.initSlot();
        this.addDataSlots(data);
    }

    public int getStirsCount() {
        return this.data.get(0);
    }

    @Override
    public void initMachineInventorySlot() {
        this.addSlot(new Slot(this.blockEntity, OUTPUT, 80, 47) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack) && SlotInputLimitManager.SAUCEPAN_AND_WHISK_INPUT_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack));
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        int[][] slots = {
                {MAIN_TOP_LEFT, 61, 67},
                {MAIN_TOP_CENTER, 80, 67},
                {MAIN_TOP_RIGHT, 99, 67},
                {MAIN_BOTTOM_LEFT, 61, 87},
                {MAIN_BOTTOM_CENTER, 80, 87},
                {MAIN_BOTTOM_RIGHT, 99, 87},
                {SECONDARY_TOP_LEFT, 8, 27},
                {SECONDARY_TOP_RIGHT, 26, 27},
                {SECONDARY_BOTTOM_LEFT, 8, 45},
                {SECONDARY_BOTTOM_RIGHT, 26, 45},
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public int getMaxStackSize() {
                    return 1;
                }
            });
        }
    }
}
