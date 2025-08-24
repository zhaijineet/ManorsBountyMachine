package net.zhaiji.manorsbountymachine.network.server.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.server.ServerPacketHandler;

import java.util.function.Supplier;

public class FermentationStopPacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;

    public FermentationStopPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public static FermentationStopPacket decode(FriendlyByteBuf buf) {
        return new FermentationStopPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> ServerPacketHandler.handlerFermentationStop(this));
        this.context.setPacketHandled(true);
    }
}
