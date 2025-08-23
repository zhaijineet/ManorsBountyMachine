package net.zhaiji.manorsbountymachine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.client.packet.SyncBlockEntityFluidTankPacket;

import java.util.List;

public class MachineUtil {
    public static void handlerFluidSlot(int slot, Container container, IFluidHandler fluidHandler, Level level, BlockPos blockPos) {
        ItemStack stack = container.getItem(slot);
        if (stack.isEmpty() || !FluidUtil.getFluidHandler(stack).isPresent()) return;
        ItemStack emptyStackableFluidTank = ItemStack.EMPTY;
        int emptyStackableFluidTankCount = 0;
        for (int i = 0; i < stack.getCount(); i++) {
            ItemStack singleTank = stack.copyWithCount(1);
            FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainer(singleTank, fluidHandler, Integer.MAX_VALUE, null, true);
            if (fluidActionResult.isSuccess()) {
                emptyStackableFluidTankCount++;
                emptyStackableFluidTank = fluidActionResult.getResult().copy();
            } else {
                break;
            }
        }
        if (emptyStackableFluidTankCount == 0) return;
        emptyStackableFluidTank.setCount(emptyStackableFluidTankCount);
        stack.shrink(emptyStackableFluidTankCount);
        if (stack.isEmpty()) {
            container.setItem(slot, emptyStackableFluidTank);
        } else {
            popCraftRemaining(level, blockPos, emptyStackableFluidTank);
        }
        FluidStack fluidStack = fluidHandler.getFluidInTank(0);
        if (!level.isClientSide()) {
            ManorsBountyMachinePacket.sendToClientWithChunk((LevelChunk) level.getChunk(blockPos), new SyncBlockEntityFluidTankPacket(blockPos, fluidStack));
        }
        SoundEvent soundevent = fluidStack.getFluid().getFluidType().getSound(fluidStack, SoundActions.BUCKET_EMPTY);
        if (soundevent != null) {
            level.playSound(null, blockPos, soundevent, SoundSource.BLOCKS);
        }
    }

    public static ItemStack getCraftRemaining(ItemStack input) {
        return getCraftRemaining(input, input.getCount());
    }

    public static ItemStack getCraftRemaining(ItemStack input, int count) {
        return input.hasCraftingRemainingItem() ? input.getCraftingRemainingItem().copyWithCount(count) : ItemStack.EMPTY;
    }

    public static void insertCraftRemaining(Container container, int[] slots, List<ItemStack> craftRemaining) {
        for (int slot : slots) {
            if (!craftRemaining.isEmpty() && container.getItem(slot).isEmpty()) {
                container.setItem(slot, craftRemaining.remove(0));
            }
        }
    }

    public static void popCraftRemaining(Level level, BlockPos blockPos, ItemStack craftRemaining) {
        Block.popResourceFromFace(level, blockPos, Direction.UP, craftRemaining);
    }

    public static void popCraftRemaining(Level level, BlockPos blockPos, List<ItemStack> craftRemaining) {
        for (ItemStack remaining : craftRemaining) {
            Block.popResourceFromFace(level, blockPos, Direction.UP, remaining);
        }
    }
}
