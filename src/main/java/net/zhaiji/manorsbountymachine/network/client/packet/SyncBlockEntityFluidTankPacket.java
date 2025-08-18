package net.zhaiji.manorsbountymachine.network.client.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.zhaiji.manorsbountymachine.network.client.ClientPacketHandler;

import java.util.function.Supplier;

public class SyncBlockEntityFluidTankPacket {
    public NetworkEvent.Context context;
    public BlockPos blockPos;
    public FluidStack fluidStack;

    public SyncBlockEntityFluidTankPacket(BlockPos blockPos, FluidStack fluidStack) {
        this.blockPos = blockPos;
        this.fluidStack = fluidStack;
    }

    public static SyncBlockEntityFluidTankPacket decode(FriendlyByteBuf buf) {
        return new SyncBlockEntityFluidTankPacket(buf.readBlockPos(), buf.readFluidStack());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeFluidStack(this.fluidStack);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        this.context = supplier.get();
        this.context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handlerSyncBlockEntityFluidTank(this)));
        this.context.setPacketHandled(true);
    }
}
