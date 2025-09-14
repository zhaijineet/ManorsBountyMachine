package net.zhaiji.manorsbountymachine.network.client.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.client.ClientPacketHandler;

import java.util.function.Supplier;

public class StockPotPlaySoundPacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;

    public StockPotPlaySoundPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public static StockPotPlaySoundPacket decode(FriendlyByteBuf buf) {
        return new StockPotPlaySoundPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlerStockPotPlaySound(this)));
        this.context.setPacketHandled(true);
    }
}
