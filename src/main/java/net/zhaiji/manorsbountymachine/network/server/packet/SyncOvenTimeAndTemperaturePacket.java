package net.zhaiji.manorsbountymachine.network.server.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.server.ServerPacketHandler;

import java.util.function.Supplier;

public class SyncOvenTimeAndTemperaturePacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;
    public int button;
    public int value;

    public SyncOvenTimeAndTemperaturePacket(BlockPos blockPos, int button, int value) {
        this.blockPos = blockPos;
        this.button = button;
        this.value = value;
    }

    public static SyncOvenTimeAndTemperaturePacket decode(FriendlyByteBuf buf) {
        return new SyncOvenTimeAndTemperaturePacket(buf.readBlockPos(), buf.readInt(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeInt(this.button);
        buf.writeInt(this.value);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> ServerPacketHandler.handlerSyncOvenTimeAndTemperature(this));
        this.context.setPacketHandled(true);
    }
}
