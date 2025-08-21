package net.zhaiji.manorsbountymachine.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.zhaiji.manorsbountymachine.block.entity.AbstractMachineBlockEntity;
import net.zhaiji.manorsbountymachine.network.client.packet.SyncBlockEntityDataPacket;
import net.zhaiji.manorsbountymachine.network.client.packet.SyncBlockEntityFluidTankPacket;

public class ClientPacketHandler {
    public static void handlerSyncBlockEntityFluidTank(SyncBlockEntityFluidTankPacket packet) {
        Player player = Minecraft.getInstance().player;
        LazyOptional<IFluidHandler> fluidHandler = player.level().getBlockEntity(packet.blockPos).getCapability(ForgeCapabilities.FLUID_HANDLER);
        fluidHandler.ifPresent(iFluidHandler -> {
            if (iFluidHandler instanceof FluidTank fluidTank) {
                fluidTank.setFluid(packet.fluidStack);
            }
        });
    }

    public static void handlerSyncBlockEntityRenderer(SyncBlockEntityDataPacket packet) {
        Player player = Minecraft.getInstance().player;
        if (player.level().getBlockEntity(packet.blockPos) instanceof AbstractMachineBlockEntity blockEntity) {
            blockEntity.handleUpdateTag(packet.syncTag);
        }
    }
}
