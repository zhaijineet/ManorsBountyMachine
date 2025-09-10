package net.zhaiji.manorsbountymachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zhaiji.manorsbountymachine.block.entity.FryerBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.manager.BlockShapeManager;
import org.jetbrains.annotations.Nullable;

public class FryerBlock extends BaseMachineBlock {
    public static final BooleanProperty HAS_OIL = BooleanProperty.create("has_oil");

    public FryerBlock() {
        super(InitBlock.getBlockProperties());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(HAS_OIL, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FryerBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HAS_OIL);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> BlockShapeManager.NORTH_FRYER_SHAPE;
            case SOUTH -> BlockShapeManager.SOUTH_FRYER_SHAPE;
            case WEST -> BlockShapeManager.WEST_FRYER_SHAPE;
            default -> BlockShapeManager.EAST_FRYER_SHAPE;
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, InitBlockEntityType.FRYER.get(), FryerBlockEntity::serverTick);
    }
}
