package net.zhaiji.manorsbountymachine.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity;
import net.zhaiji.manorsbountymachine.network.client.packet.StockPotPlaySoundPacket;

public class ClientPacketHandler {
    public static void handlerStockPotPlaySound(StockPotPlaySoundPacket packet) {
        Player player = Minecraft.getInstance().player;
        if (player.level().getBlockEntity(packet.blockPos) instanceof StockPotBlockEntity blockEntity) {
            blockEntity.isRunning = true;
        }
    }
}
