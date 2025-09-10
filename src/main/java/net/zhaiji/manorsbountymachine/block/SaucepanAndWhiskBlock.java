package net.zhaiji.manorsbountymachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitBlock;
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
}
