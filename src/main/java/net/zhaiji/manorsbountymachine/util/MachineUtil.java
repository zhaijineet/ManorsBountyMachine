package net.zhaiji.manorsbountymachine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.zhaiji.manorsbountymachine.block.entity.BaseHasItemBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;

import java.util.List;

public class MachineUtil {
    public static void handlerFluidSlot(int slot, BaseHasItemBlockEntity container, IFluidHandler fluidHandler, Level level, BlockPos blockPos) {
        ItemStack stack = container.getItem(slot);
        if (stack.isEmpty() || !(FluidUtil.getFluidHandler(stack).isPresent() || ManorsBountyCompat.BUCKET_FLUID_MAP.containsKey(stack.getItem())))
            return;
        ItemStack emptyStackableFluidTank = ItemStack.EMPTY;
        int emptyStackableFluidTankCount = 0;
        for (int i = 0; i < stack.getCount(); i++) {
            ItemStack singleTank = stack.copyWithCount(1);
            FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainer(singleTank, fluidHandler, Integer.MAX_VALUE, null, true);
            if (fluidActionResult.isSuccess()) {
                emptyStackableFluidTankCount++;
                emptyStackableFluidTank = fluidActionResult.getResult().copy();
            } else if (
                    fluidHandler.getTankCapacity(0) - fluidHandler.getFluidInTank(0).getAmount() >= 1000
                            && ManorsBountyCompat.BUCKET_FLUID_MAP.containsKey(stack.getItem())
            ) {
                new FluidStack(ManorsBountyCompat.BUCKET_FLUID_MAP.get(stack.getItem()), 1000);
                fluidHandler.fill(new FluidStack(ManorsBountyCompat.BUCKET_FLUID_MAP.get(stack.getItem()), 1000), IFluidHandler.FluidAction.EXECUTE);
                emptyStackableFluidTankCount++;
                emptyStackableFluidTank = Items.BUCKET.getDefaultInstance();
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
            container.getLevel().sendBlockUpdated(container.getBlockPos(), container.getBlockState(), container.getBlockState(), 2);
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
        // MCR的耐久处理是直接塞到CraftRemaining里的，简直无语
        // 这里直接返回空，如果其他mod出了兼容性问题就改这里
        if (input.isDamageableItem()) {
            return ItemStack.EMPTY;
        }
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
