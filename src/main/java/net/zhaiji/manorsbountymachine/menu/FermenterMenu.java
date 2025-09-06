package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.recipe.BrightFermentationRecipe;
import net.zhaiji.manorsbountymachine.recipe.DimFermentationRecipe;
import net.zhaiji.manorsbountymachine.recipe.NormalFermentationRecipe;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

import static net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity.*;

public class FermenterMenu extends BaseMachineMenu {
    public FermenterBlockEntity blockEntity;
    public ContainerData data;

    public FermenterMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (FermenterBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public FermenterMenu(int pContainerId, Inventory pPlayerInventory, FermenterBlockEntity blockEntity, ContainerData data) {
        super(InitMenuType.FERMENTER_MENU.get(), pContainerId, pPlayerInventory, blockEntity);
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

    public int getMaxCookingTime() {
        return this.data.get(1);
    }

    public void setMaxCookingTime(int value) {
        this.data.set(1, value);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.blockEntity.stopOpen(pPlayer);
    }

    @Override
    public void initMachineInventorySlot() {
        int[][] slots = {
                {CATALYSTS_TOP, 144, 48},
                {CATALYSTS_BOTTOM, 144, 74}
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return super.mayPlace(pStack) && ManorsBountyCompat.isCatalysts(pStack);
                }

                @Override
                public boolean isActive() {
                    return super.isActive() && getCookingTime() == 0 && blockEntity.getItem(OUTPUT).isEmpty();
                }
            });
        }
        // TODO 之后添加自定义资源读取监听，这里要做调整
        this.addSlot(new Slot(this.blockEntity, CONTAINER, 121, 116) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                boolean canPlace = super.mayPlace(pStack);
                if (!canPlace) return false;
                for (DimFermentationRecipe recipe : blockEntity.getAllDimRecipe()) {
                    if (recipe.container.test(pStack)) {
                        return true;
                    }
                }
                for (NormalFermentationRecipe recipe : blockEntity.getAllNormalRecipe()) {
                    if (recipe.container.test(pStack)) {
                        return true;
                    }
                }
                for (BrightFermentationRecipe recipe : blockEntity.getAllBrightRecipe()) {
                    if (recipe.container.test(pStack)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean mayPickup(Player pPlayer) {
                return super.mayPickup(pPlayer) && getCookingTime() == 0;
            }
        });
        slots = new int[][]{
                {TOP_LEFT, 40, 50},
                {TOP_RIGHT, 72, 50},
                {BOTTOM_LEFT, 40, 76},
                {BOTTOM_RIGHT, 72, 76},
        };
        for (int[] slot : slots) {
            this.addSlot(new Slot(this.blockEntity, slot[0], slot[1], slot[2]) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return super.mayPlace(pStack) && getCookingTime() == 0;
                }

                @Override
                public boolean mayPickup(Player pPlayer) {
                    return super.mayPickup(pPlayer) && getCookingTime() == 0;
                }
            });
        }
        this.addSlot(new Slot(this.blockEntity, OUTPUT, 57, 63) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public boolean isActive() {
                return super.isActive() && this.hasItem();
            }
        });
    }
}
