package net.zhaiji.manorsbountymachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.manager.BlockShapeManager;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardBlock extends AbstractMachineBlock {
    public CuttingBoardBlock() {
        super(InitBlock.getBlockProperties());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CuttingBoardBlockEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> BlockShapeManager.NORTH_CUTTING_BOARD_SHAPE;
            case SOUTH -> BlockShapeManager.SOUTH_CUTTING_BOARD_SHAPE;
            case WEST -> BlockShapeManager.WEST_CUTTING_BOARD_SHAPE;
            default -> BlockShapeManager.EAST_CUTTING_BOARD_SHAPE;
        };
    }

    public boolean addItem(CuttingBoardBlockEntity blockEntity, ItemStack itemStack) {
        for (int i = 0; i < CuttingBoardBlockEntity.ITEMS_SIZE; i++) {
            if (blockEntity.getItem(i).isEmpty()) {
                blockEntity.setItem(i, itemStack.copyWithCount(1));
                itemStack.shrink(1);
                return true;
            }
        }
        return false;
    }

    public boolean dropSingleItem(CuttingBoardBlockEntity blockEntity, Level level, BlockPos pos) {
        for (int i = CuttingBoardBlockEntity.ITEMS_SIZE; i >= 0; i--) {
            ItemStack itemStack = blockEntity.getItem(i);
            if (!itemStack.isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                return true;
            }
        }
        return false;
    }

    public InteractionResult handlerSingleItem(CuttingBoardBlockEntity blockEntity, Level level, ItemStack heldItem, ItemStack otherHeldItem) {
        if (!otherHeldItem.isEmpty() && !blockEntity.isFull()) {
            return InteractionResult.PASS;
        }
        if (blockEntity.isEmpty()) {
            addItem(blockEntity, heldItem);
        } else {
            blockEntity.craftSingleItem(heldItem);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public InteractionResult handlerMultipleItem(CuttingBoardBlockEntity blockEntity, Level level, BlockPos blockPos, ItemStack otherHeldItem) {
        if (blockEntity.craftMultipleItem()) {
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            return dropSingleItem(blockEntity, level, blockPos)
                    ? InteractionResult.sidedSuccess(level.isClientSide())
                    : InteractionResult.CONSUME_PARTIAL;
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        if (pLevel.getBlockEntity(pPos) instanceof CuttingBoardBlockEntity blockEntity) {
            ItemStack heldItem = pPlayer.getItemInHand(pHand);
            ItemStack otherHeldItem = pPlayer.getItemInHand(pHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
            if (pPlayer.isShiftKeyDown() && !blockEntity.isEmpty()) {
                Containers.dropContents(pLevel, pPos, blockEntity);
                return InteractionResult.sidedSuccess(pLevel.isClientSide());
            }
            // TODO 检测持有物品匹配tool
            // 添加对资源重载的监听
            if (pHand == InteractionHand.MAIN_HAND && ManorsBountyCompat.isKnife(heldItem)) {
                return this.handlerSingleItem(blockEntity, pLevel, heldItem, otherHeldItem);
            }
            if (!heldItem.isEmpty()) {
                return this.addItem(blockEntity, heldItem)
                        ? InteractionResult.sidedSuccess(pLevel.isClientSide())
                        : InteractionResult.CONSUME_PARTIAL;
            } else if (otherHeldItem.isEmpty()) {
                return this.handlerMultipleItem(blockEntity, pLevel, pPos, otherHeldItem);
            }
        }
        return InteractionResult.PASS;
    }
}
