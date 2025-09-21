package net.zhaiji.manorsbountymachine.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity;
import net.zhaiji.manorsbountymachine.client.sound.ShakerSoundInstance;
import net.zhaiji.manorsbountymachine.client.sound.StockPotSoundInstance;

public class SoundUtil {
    public static SoundManager soundManager() {
        return Minecraft.getInstance().getSoundManager();
    }

    public static void playShakerSoundInstance(Player player) {
        soundManager().play(new ShakerSoundInstance(player));
    }

    public static void playStockPotSoundInstance(StockPotBlockEntity blockEntity) {
        soundManager().play(new StockPotSoundInstance(blockEntity));
    }
}
