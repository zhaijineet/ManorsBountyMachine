package net.zhaiji.manorsbountymachine.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

@OnlyIn(Dist.CLIENT)
public class StockPotSoundInstance extends AbstractTickableSoundInstance {
    public final StockPotBlockEntity blockEntity;

    public StockPotSoundInstance(StockPotBlockEntity blockEntity) {
        super(InitSoundEvent.STOCK_POT_RUNNING.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.blockEntity = blockEntity;
        this.looping = true;
    }

    @Override
    public void tick() {
        if (this.blockEntity.isRemoved() || !this.blockEntity.isRunning) {
            this.stop();
        } else {
            BlockPos blockPos = blockEntity.getBlockPos();
            this.x = blockPos.getX();
            this.y = blockPos.getY();
            this.z = blockPos.getZ();
        }
        if (this.blockEntity.isOnStockPotHeatBlock()) {
            this.volume = 1;
        } else {
            this.volume = 0;
        }
    }
}
