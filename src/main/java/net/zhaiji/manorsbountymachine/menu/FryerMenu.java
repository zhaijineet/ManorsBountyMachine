package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.zhaiji.manorsbountymachine.block.entity.FryerBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.recipe.FastFryRecipe;
import net.zhaiji.manorsbountymachine.recipe.SlowFryRecipe;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

public class FryerMenu extends BaseMachineMenu {
    public final FluidTank fluidTank;
    public final FryerBlockEntity blockEntity;
    public final ContainerData data;

    public FryerMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (FryerBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public FryerMenu(int pContainerId, Inventory pPlayerInventory, FryerBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.FRYER_MENU.get(), pContainerId, pPlayerInventory, blockEntity);
        this.fluidTank = blockEntity.fluidTank;
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
    public void initMachineInventorySlot() {
        this.addSlot(new Slot(this.blockEntity, FryerBlockEntity.FLUID_TANK_SLOT, 141, 35) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack) && FluidUtil.getFluidHandler(pStack).isPresent() || ManorsBountyCompat.BUCKET_FLUID_MAP.containsKey(pStack.getItem());
            }
        });
        int[][] slots = {
                {FryerBlockEntity.TOP_LEFT, 42, 22},
                {FryerBlockEntity.TOP_RIGHT, 76, 22},
                {FryerBlockEntity.BOTTOM_LEFT, 42, 56},
                {FryerBlockEntity.BOTTOM_RIGHT, 76, 56}
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    boolean canPlace = super.mayPlace(pStack) && getCookingTime() == 0;
                    if (!canPlace) return false;
                    for (FastFryRecipe recipe : blockEntity.getAllFastRecipe()) {
                        if (recipe.input.test(pStack)) {
                            return true;
                        }
                    }
                    for (SlowFryRecipe recipe : blockEntity.getAllSlowRecipe()) {
                        if (recipe.input.test(pStack)) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public boolean mayPickup(Player pPlayer) {
                    return super.mayPickup(pPlayer) && getCookingTime() == 0;
                }

                @Override
                public int getMaxStackSize() {
                    return 4;
                }

                @Override
                public boolean isActive() {
                    return super.isActive() && getCookingTime() == 0;
                }
            });
        }
    }
}
