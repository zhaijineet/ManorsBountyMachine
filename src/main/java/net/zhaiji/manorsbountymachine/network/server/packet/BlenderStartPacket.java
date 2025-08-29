package net.zhaiji.manorsbountymachine.network.server.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.server.ServerPacketHandler;

import java.util.function.Supplier;

public class BlenderStartPacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;

    public BlenderStartPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public static BlenderStartPacket decode(FriendlyByteBuf buf) {
        return new BlenderStartPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> ServerPacketHandler.handlerBlenderStart(this));
        this.context.setPacketHandled(true);
    }
}
