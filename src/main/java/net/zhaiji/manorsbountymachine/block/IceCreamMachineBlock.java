package net.zhaiji.manorsbountymachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.zhaiji.manorsbountymachine.block.entity.IceCreamMachineBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;
import org.jetbrains.annotations.Nullable;

public class IceCreamMachineBlock extends AbstractMachineBlock {
    public IceCreamMachineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IceCreamMachineBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        pLevel.playSound(pPlayer, pPos, InitSoundEvent.ICE_CREAM_MACHINE_CLANK.get(), SoundSource.BLOCKS, 0.15F, 1F);
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
