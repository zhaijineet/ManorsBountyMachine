package net.zhaiji.manorsbountymachine.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

@OnlyIn(Dist.CLIENT)
public class OvenSoundInstance extends AbstractTickableSoundInstance {
    public OvenBlockEntity blockEntity;

    public OvenSoundInstance(OvenBlockEntity blockEntity) {
        super(InitSoundEvent.OVEN_RUNNING.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.blockEntity = blockEntity;
        BlockPos blockPos = blockEntity.getBlockPos();
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
        this.volume = 1;
        this.looping = true;
        this.delay = 0;
    }

    @Override
    public void tick() {
        if (this.blockEntity.isRemoved() || !this.blockEntity.isRunning) {
            this.blockEntity.isPlaySound = false;
            this.stop();
        }
    }
}
