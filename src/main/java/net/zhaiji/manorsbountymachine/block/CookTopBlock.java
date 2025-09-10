package net.zhaiji.manorsbountymachine.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class CookTopBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty RUNNING = BooleanProperty.create("running");

    public CookTopBlock() {
        super(InitBlock.getBlockProperties().lightLevel(blockState -> blockState.getValue(RUNNING) ? 15 : 0));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(RUNNING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, RUNNING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        boolean running = pState.getValue(RUNNING);
        pLevel.setBlock(pPos, pState.setValue(RUNNING, !running), 1);
        if (running) {
            pLevel.playSound(pPlayer, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);
        } else {
            pLevel.playSound(pPlayer, pPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1F, pLevel.getRandom().nextFloat() * 0.4F + 0.8F);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pState.getValue(RUNNING) && pLevel.getBlockState(pPos.above()).isAir() && pLevel.isClientSide()) {
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
