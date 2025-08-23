package net.zhaiji.manorsbountymachine.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

public class FermenterMenu extends AbstractMachineMenu {
    public FermenterMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, (FermenterBlockEntity) pPlayerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public FermenterMenu(int pContainerId, Inventory pPlayerInventory, FermenterBlockEntity blockEntity) {
        super(InitMenuType.FERMENTER_MENU.get(), pContainerId, pPlayerInventory, blockEntity);
        this.initSlot();
    }

    @Override
    public void initMachineInventorySlot() {

    }
}
