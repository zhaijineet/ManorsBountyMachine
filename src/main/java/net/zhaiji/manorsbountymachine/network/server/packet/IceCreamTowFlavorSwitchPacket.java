package net.zhaiji.manorsbountymachine.network.server.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.server.ServerPacketHandler;

import java.util.function.Supplier;

public class IceCreamTowFlavorSwitchPacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;
    public boolean isOpen;

    public IceCreamTowFlavorSwitchPacket(BlockPos blockPos, boolean isOpen) {
        this.blockPos = blockPos;
        this.isOpen = isOpen;
    }

    public static IceCreamTowFlavorSwitchPacket decode(FriendlyByteBuf buf) {
        return new IceCreamTowFlavorSwitchPacket(buf.readBlockPos(), buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeBoolean(this.isOpen);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> ServerPacketHandler.handlerIceCreamTowFlavorSwitch(this));
        this.context.setPacketHandled(true);
    }
}
