package net.zhaiji.manorsbountymachine.network.server.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.server.ServerPacketHandler;

import java.util.function.Supplier;

public class BrewingStartPacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;

    public BrewingStartPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public static BrewingStartPacket decode(FriendlyByteBuf buf) {
        return new BrewingStartPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> ServerPacketHandler.handlerBrewingStart(this));
        this.context.setPacketHandled(true);
    }
}
