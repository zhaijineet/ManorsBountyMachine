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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.manager.BlockShapeManager;
import org.jetbrains.annotations.Nullable;

public class FermenterBlock extends BaseMachineBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public FermenterBlock() {
        super(InitBlock.getBlockProperties());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH, SOUTH -> BlockShapeManager.FERMENTER_NORTH_SOUTH_SHAPE;
            default -> BlockShapeManager.FERMENTER_WEST_EAST_SHAPE;
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FermenterBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, InitBlockEntityType.FERMENTER.get(), FermenterBlockEntity::serverTick);
    }
}
