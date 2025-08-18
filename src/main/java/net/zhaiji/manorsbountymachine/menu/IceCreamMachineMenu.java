package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.zhaiji.manorsbountymachine.block.entity.IceCreamMachineBlockEntity;
import net.zhaiji.manorsbountymachine.recipe.IceCreamRecipe;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

public class IceCreamMachineMenu extends AbstractMachineMenu {
    public final FluidTank fluidTank;
    public final IceCreamMachineBlockEntity blockEntity;

    public IceCreamMachineMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (IceCreamMachineBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public IceCreamMachineMenu(int pContainerId, Inventory pPlayerInventory, IceCreamMachineBlockEntity blockEntity) {
        super(InitMenuType.ICE_CREAM_MACHINE_MENU.get(), pContainerId, pPlayerInventory, blockEntity);
        this.fluidTank = blockEntity.fluidTank;
        this.blockEntity = blockEntity;
        this.initSlot();
    }

    @Override
    public void initMachineInventorySlot() {
        this.addSlot(new Slot(this.blockEntity, IceCreamMachineBlockEntity.FLUID_TANK_SLOT, 141, 41) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack) && FluidUtil.getFluidHandler(pStack).isPresent();
            }
        });
        this.addSlot(new Slot(this.blockEntity, IceCreamMachineBlockEntity.OUTPUT_SLOT, 38, 118) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                boolean canPlace = super.mayPlace(pStack);
                if (!canPlace) return false;
                for (IceCreamRecipe recipe : blockEntity.getAllRecipe()) {
                    if (recipe.input.get(0).test(pStack)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.blockEntity, IceCreamMachineBlockEntity.LEFT_INPUT_SLOT, 104, 118));
        this.addSlot(new Slot(this.blockEntity, IceCreamMachineBlockEntity.RIGHT_INPUT_SLOT, 143, 118) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return super.mayPlace(pStack) && blockEntity.isTwoFlavor;
            }

            @Override
            public boolean isActive() {
                return super.isActive() && blockEntity.isTwoFlavor;
            }
        });
    }
}
