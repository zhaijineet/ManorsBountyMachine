package net.zhaiji.manorsbountymachine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zhaiji.manorsbountymachine.block.CooktopBlock;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;

public class CooktopBlockEntity extends BlockEntity {
    public int time;

    public CooktopBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntityType.COOKTOP.get(), pPos, pBlockState);
    }

    public static void clientTick(Level pLevel, BlockPos pPos, BlockState pState, CooktopBlockEntity pBlockEntity) {
        pBlockEntity.time++;
        if (pState.getValue(CooktopBlock.RUNNING) && pLevel.getBlockState(pPos.above()).isAir() && pBlockEntity.time % 5 == 0) {
            pBlockEntity.time = 0;
            double x = pPos.getX() + 0.5;
            double y = pPos.getY() + 1.15;
            double z = pPos.getZ() + 0.5;
            pLevel.addParticle(ParticleTypes.FLAME, x + 0.22, y, z + 0.22, 0, 0, 0);
            pLevel.addParticle(ParticleTypes.FLAME, x + 0.22, y, z - 0.22, 0, 0, 0);
            pLevel.addParticle(ParticleTypes.FLAME, x - 0.22, y, z + 0.22, 0, 0, 0);
            pLevel.addParticle(ParticleTypes.FLAME, x - 0.22, y, z - 0.22, 0, 0, 0);
        }
    }
}
