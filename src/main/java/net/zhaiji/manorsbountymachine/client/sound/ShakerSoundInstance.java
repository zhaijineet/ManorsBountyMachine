package net.zhaiji.manorsbountymachine.client.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.manorsbountymachine.register.InitItem;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

public class ShakerSoundInstance extends AbstractTickableSoundInstance {
    public final Player player;
    public int time = 0;

    public ShakerSoundInstance(Player player) {
        super(InitSoundEvent.SHAKER_SHAKE.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
    }

    @Override
    public void tick() {
        this.time++;
        if (this.player.isRemoved() || !this.player.getUseItem().is(InitItem.SHAKER.get())) {
            this.stop();
        } else {
            this.x = this.player.getX();
            this.y = this.player.getY();
            this.z = this.player.getZ();
        }
        if (this.time >= 8) {
            this.volume = 1;
        } else {
            this.volume = 0;
        }
    }
}
