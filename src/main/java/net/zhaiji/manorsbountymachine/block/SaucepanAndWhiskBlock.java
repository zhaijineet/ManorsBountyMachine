package net.zhaiji.manorsbountymachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.manager.BlockShapeManager;
import org.jetbrains.annotations.Nullable;

public class SaucepanAndWhiskBlock extends BaseMachineBlock {
    public SaucepanAndWhiskBlock() {
        super(InitBlock.getBlockProperties());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SaucepanAndWhiskBlockEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BlockShapeManager.SAUCEPAN_AND_WHISK_SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
