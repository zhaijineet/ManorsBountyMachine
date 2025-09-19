package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.block.entity.TeapotBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

import static net.zhaiji.manorsbountymachine.block.entity.TeapotBlockEntity.*;

public class TeapotMenu extends BaseMachineMenu {
    public final TeapotBlockEntity blockEntity;
    public ContainerData data;

    public TeapotMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (TeapotBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public TeapotMenu(int pContainerId, Inventory pPlayerInventory, TeapotBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.TEAPOT.get(), pContainerId, pPlayerInventory, blockEntity);
        this.blockEntity = blockEntity;
        this.data = data;
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
        this.addSlot(new Slot(this.blockEntity, OUTPUT, 33, 90) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack)
                        && SlotInputLimitManager.TEAPOT_CUP_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack))
                        && getCookingTime() == 0;
            }

            @Override
            public boolean mayPickup(Player pPlayer) {
                return super.mayPickup(pPlayer) && getCookingTime() == 0;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.blockEntity, DRINK, 121, 41) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack) && SlotInputLimitManager.TEAPOT_DRINK_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack));
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.blockEntity, MATERIAL, 96, 118) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack) && SlotInputLimitManager.TEAPOT_MATERIAL_LIMIT.stream().anyMatch(ingredient -> ingredient.test(pStack));
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        int[][] slots = {
                {TOP_LEFT, 30, 9},
                {TOP_RIGHT, 54, 9},
                {BOTTOM_LEFT, 30, 32},
                {BOTTOM_RIGHT, 54, 32}
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
