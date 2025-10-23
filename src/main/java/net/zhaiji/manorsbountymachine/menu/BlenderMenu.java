package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

import static net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity.*;

public class BlenderMenu extends BaseMachineMenu {
    public BlenderBlockEntity blockEntity;
    public ContainerData data;

    public BlenderMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (BlenderBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public BlenderMenu(int pContainerId, Inventory pPlayerInventory, BlenderBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.BLENDER.get(), pContainerId, pPlayerInventory, blockEntity);
        this.blockEntity = blockEntity;
        this.data = data;
        this.playerInventoryYOffset += 5;
        this.initSlot();
        this.addDataSlots(data);
    }

    public int getCookingTime() {
        return this.data.get(0);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.blockEntity.stopOpen(pPlayer);
    }

    @Override
    public void initMachineInventorySlot() {
        this.addSlot(new Slot(this.blockEntity, CONTAINER, 28, 63) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack)
                        && SlotInputLimitManager.BLENDER_INPUT_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack))
                        && getCookingTime() == 0;
            }

            @Override
            public boolean mayPickup(Player pPlayer) {
                return super.mayPickup(pPlayer) && getCookingTime() == 0;
            }

            @Override
            public int getMaxStackSize() {
                return 8;
            }
        });
        int[][] slots = {
                {MAIN_TOP_LEFT, 103, 54},
                {MAIN_TOP_RIGHT, 129, 54},
                {MAIN_CENTER_LEFT, 103, 74},
                {MAIN_CENTER_RIGHT, 129, 74},
                {MAIN_BOTTOM_LEFT, 103, 94},
                {MAIN_BOTTOM_RIGHT, 129, 94},
                {SECONDARY_TOP_LEFT, 18, 14},
                {SECONDARY_TOP_RIGHT, 38, 14},
                {SECONDARY_BOTTOM_LEFT, 18, 34},
                {SECONDARY_BOTTOM_RIGHT, 38, 34},
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPickup(Player pPlayer) {
                    return super.mayPickup(pPlayer) && getCookingTime() == 0;
                }

                @Override
                public int getMaxStackSize() {
                    return 8;
                }
            });
        }
        slots = new int[][]{
                {SECONDARY_OUTPUT, 28, 90},
                {MAIN_OUTPUT, 28, 115}
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
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
}
