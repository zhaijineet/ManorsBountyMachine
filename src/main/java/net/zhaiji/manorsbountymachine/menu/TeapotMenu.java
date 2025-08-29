package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.block.entity.TeapotBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

public class TeapotMenu extends AbstractMachineMenu {
    public final TeapotBlockEntity blockEntity;
    public ContainerData data;

    public TeapotMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (TeapotBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public TeapotMenu(int pContainerId, Inventory pPlayerInventory, TeapotBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.TEAPOT_MENU.get(), pContainerId, pPlayerInventory, blockEntity);
        this.blockEntity = blockEntity;
        this.data = data;
        this.initSlot();
        this.addDataSlots(data);
    }

    public int getCookingTime() {
        return this.data.get(0);
    }

    public void setCookingTime(int value) {
        this.data.set(0, value);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.blockEntity.stopOpen(pPlayer);
    }

    @Override
    public void initMachineInventorySlot() {
        int[][] slots = {
                {TeapotBlockEntity.OUTPUT, 33, 90, 0},
                {TeapotBlockEntity.DRINK, 121, 41, 1},
                {TeapotBlockEntity.MATERIAL, 96, 118, 2}
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return super.mayPlace(pStack) && ManorsBountyCompat.isTeapotOutputItem(pStack);
                }

                @Override
                public boolean mayPickup(Player pPlayer) {
                    boolean canPickup = super.mayPickup(pPlayer);
                    if (!canPickup) return false;
                    if (slot[0] == TeapotBlockEntity.OUTPUT) {
                        return getCookingTime() == 0;
                    }
                    return true;
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }
            });
        }
        slots = new int[][]{
                {TeapotBlockEntity.TOP_LEFT, 30, 9},
                {TeapotBlockEntity.TOP_RIGHT, 54, 9},
                {TeapotBlockEntity.BOTTOM_LEFT, 30, 32},
                {TeapotBlockEntity.BOTTOM_RIGHT, 54, 32}
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
