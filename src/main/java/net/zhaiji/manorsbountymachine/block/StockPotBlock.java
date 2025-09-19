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
import net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.manager.BlockShapeManager;
import org.jetbrains.annotations.Nullable;

public class StockPotBlock extends BaseMachineBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public StockPotBlock() {
        super(InitBlock.getBlockProperties());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OPEN);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StockPotBlockEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BlockShapeManager.STOCK_POT_SHAPE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(
                pBlockEntityType,
                InitBlockEntityType.STOCK_POT.get(),
                pLevel.isClientSide()
                        ? StockPotBlockEntity::clientTick
                        : StockPotBlockEntity::serverTick
        );
    }
}
