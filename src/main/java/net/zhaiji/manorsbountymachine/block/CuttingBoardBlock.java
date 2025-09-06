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

    public InteractionResult handlerSingleItem(CuttingBoardBlockEntity blockEntity, Level level, Player player, ItemStack heldItem, ItemStack otherHeldItem) {
        if (!otherHeldItem.isEmpty() && !blockEntity.isFull()) {
            return InteractionResult.PASS;
        }
        boolean flag;
        if (blockEntity.isEmpty()) {
            flag = this.addItem(blockEntity, heldItem);
        } else {
            flag = blockEntity.craftSingleItem(player, heldItem);
        }
        return flag
                ? InteractionResult.sidedSuccess(level.isClientSide())
                : InteractionResult.CONSUME_PARTIAL;
    }

    public InteractionResult handlerMultipleItem(CuttingBoardBlockEntity blockEntity, Level level, BlockPos blockPos, Player player) {
        boolean flag = false;
        if (blockEntity.craftMultipleItem(player)) {
            flag = true;
        } else {
            flag = dropSingleItem(blockEntity, level, blockPos);
        }
        return flag
                ? InteractionResult.sidedSuccess(level.isClientSide())
                : InteractionResult.CONSUME_PARTIAL;
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
            if (!heldItem.isEmpty()) {
                if (pHand == InteractionHand.MAIN_HAND) {
                    return this.handlerSingleItem(blockEntity, pLevel, pPlayer, heldItem, otherHeldItem);
                }
                return this.addItem(blockEntity, heldItem)
                        ? InteractionResult.sidedSuccess(pLevel.isClientSide())
                        : InteractionResult.CONSUME_PARTIAL;
            } else if (otherHeldItem.isEmpty()) {
                return this.handlerMultipleItem(blockEntity, pLevel, pPos, pPlayer);
            }
        }
        return InteractionResult.PASS;
    }
}
