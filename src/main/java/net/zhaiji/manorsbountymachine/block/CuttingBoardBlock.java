package net.zhaiji.manorsbountymachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.manager.BlockShapeManager;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardBlock extends BaseMachineBlock {
    public CuttingBoardBlock() {
        super(InitBlock.getBlockProperties());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CuttingBoardBlockEntity(pPos, pState);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockPos = pPos.below();
        return super.canSurvive(pState, pLevel, pPos)
                && canSupportRigidBlock(pLevel, blockPos)
                && canSupportCenter(pLevel, blockPos, Direction.UP);
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

    public void playAddItemSound(CuttingBoardBlockEntity blockEntity) {
        blockEntity.getLevel().playSound(null, blockEntity.getBlockPos(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS);
    }

    public void playDropItemSound(CuttingBoardBlockEntity blockEntity) {
        blockEntity.getLevel().playSound(null, blockEntity.getBlockPos(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
    }

    public boolean addItem(CuttingBoardBlockEntity blockEntity, ItemStack itemStack) {
        for (int i = 0; i < CuttingBoardBlockEntity.ITEMS_SIZE; i++) {
            if (blockEntity.getItem(i).isEmpty()) {
                blockEntity.setItem(i, itemStack.copyWithCount(1));
                itemStack.shrink(1);
                this.playAddItemSound(blockEntity);
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
                this.playDropItemSound(blockEntity);
                return true;
            }
        }
        return false;
    }

    public InteractionResult tryMultipleCraft(CuttingBoardBlockEntity blockEntity, Level level, Player player, BlockPos blockPos, ItemStack heldItem) {
        boolean flag = blockEntity.craftMultipleItem(player, heldItem);
        if (!flag) {
            flag = dropSingleItem(blockEntity, level, blockPos);
        }
        return flag
                ? InteractionResult.sidedSuccess(level.isClientSide())
                : InteractionResult.CONSUME_PARTIAL;
    }

    public InteractionResult tryCraftItem(CuttingBoardBlockEntity blockEntity, Level level, Player player, ItemStack heldItem, ItemStack otherHeldItem) {
        boolean flag = blockEntity.craftMultipleItem(player, heldItem);
        if (!flag) {
            if (!otherHeldItem.isEmpty() && !blockEntity.isFull() && !blockEntity.inCraft) {
                return InteractionResult.PASS;
            }
            if (blockEntity.craftSingleItem(player, heldItem)) {
                flag = true;
            } else if (!blockEntity.isFull()) {
                flag = this.addItem(blockEntity, heldItem);
            }
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
                this.playDropItemSound(blockEntity);
                return InteractionResult.sidedSuccess(pLevel.isClientSide());
            }
            if (!heldItem.isEmpty()) {
                if (pHand == InteractionHand.MAIN_HAND) {
                    return this.tryCraftItem(blockEntity, pLevel, pPlayer, heldItem, otherHeldItem);
                }
                return this.addItem(blockEntity, heldItem)
                        ? InteractionResult.sidedSuccess(pLevel.isClientSide())
                        : InteractionResult.CONSUME_PARTIAL;
            } else if (otherHeldItem.isEmpty()) {
                return this.tryMultipleCraft(blockEntity, pLevel, pPlayer, pPos, heldItem);
            }
        }
        return InteractionResult.PASS;
    }
}
