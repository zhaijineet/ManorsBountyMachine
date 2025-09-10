package net.zhaiji.manorsbountymachine.network.server.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.server.ServerPacketHandler;

import java.util.function.Supplier;

public class TrySaucepanAndWhiskCraftPacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;

    public TrySaucepanAndWhiskCraftPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public static TrySaucepanAndWhiskCraftPacket decode(FriendlyByteBuf buf) {
        return new TrySaucepanAndWhiskCraftPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> ServerPacketHandler.handlerTrySaucepanAndWhiskCraft(this));
        this.context.setPacketHandled(true);
    }
}
