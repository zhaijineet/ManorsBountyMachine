package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.zhaiji.manorsbountymachine.menu.FermenterMenu;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import org.jetbrains.annotations.Nullable;

public class FermenterBlockEntity extends AbstractMachineBlockEntity {
    public static final int ITEMS_SIZE = 7;
    public final NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);

    public FermenterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.FERMENTER.get(), pPos, pBlockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public IItemHandler getItemHandler() {
        return new InvWrapper(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FermenterMenu(pContainerId, pPlayerInventory, this);
    }
}
