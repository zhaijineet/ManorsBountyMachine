package net.zhaiji.manorsbountymachine.network.client.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.client.ClientPacketHandler;

import java.util.function.Supplier;

public class SyncBlockEntityDataPacket {
    public NetworkEvent.Context context;
    public CompoundTag syncTag;
    public BlockPos blockPos;

    public SyncBlockEntityDataPacket(BlockPos blockPos, CompoundTag syncTag) {
        this.blockPos = blockPos;
        this.syncTag = syncTag;
    }

    public static SyncBlockEntityDataPacket decode(FriendlyByteBuf buf) {
        return new SyncBlockEntityDataPacket(buf.readBlockPos(), buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeNbt(this.syncTag);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlerSyncBlockEntityRenderer(this)));
        this.context.setPacketHandled(true);
    }
}
