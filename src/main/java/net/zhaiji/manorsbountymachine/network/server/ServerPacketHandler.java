package net.zhaiji.manorsbountymachine.network.server;

import net.minecraft.world.entity.player.Player;
import net.zhaiji.manorsbountymachine.block.entity.*;
import net.zhaiji.manorsbountymachine.network.server.packet.*;

public class ServerPacketHandler {
    public static void handlerIceCreamTowFlavorSwitch(IceCreamTowFlavorSwitchPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof IceCreamMachineBlockEntity blockEntity) {
            blockEntity.setTwoFlavor(packet.isOpen);
        }
    }

    public static void handlerIceCreamCraft(IceCreamCraftPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof IceCreamMachineBlockEntity blockEntity) {
            blockEntity.craftItem();
        }
    }

    public static void handlerFryerStart(FryingStartPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof FryerBlockEntity blockEntity) {
            blockEntity.startRunning();
        }
    }

    public static void handlerFriedItemCraft(StopFryingPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof FryerBlockEntity blockEntity) {
            blockEntity.stopRunning();
        }
    }

    public static void handlerSyncOvenTimeOrTemperature(SyncOvenTimeOrTemperaturePacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof OvenBlockEntity blockEntity) {
            switch (packet.button) {
                case 0 -> blockEntity.setTemperature(packet.value);
                case 1 -> blockEntity.setMaxCookingTime(packet.value);
            }
        }
    }

    public static void handlerBakeItemCraft(BakeItemCraftPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof OvenBlockEntity blockEntity) {
            blockEntity.startRunning();
        }
    }

    public static void handlerBrewingStart(BrewingStartPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof TeapotBlockEntity blockEntity) {
            blockEntity.startRunning();
        }
    }

    public static void handlerFermentationStart(FermentationStartPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof FermenterBlockEntity blockEntity) {
            blockEntity.startRunning();
        }
    }

    public static void handlerFermentationStop(FermentationStopPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof FermenterBlockEntity blockEntity) {
            blockEntity.stopRunning();
        }
    }

    public static void handlerBlenderStart(BlenderStartPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof BlenderBlockEntity blockEntity) {
            blockEntity.startRunning();
        }
    }

    public static void handlerStockPotStart(StockPotStartPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof StockPotBlockEntity blockEntity) {
            blockEntity.startRunning();
        }
    }

    public static void handlerTrySaucepanAndWhiskCraft(TrySaucepanAndWhiskCraftPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof SaucepanAndWhiskBlockEntity blockEntity) {
            blockEntity.addStirsCount();
            blockEntity.triggerAnim("saucepan_and_whisk", "animation.saucepan_and_whisk.working");
            if (blockEntity.stirsCount >= SaucepanAndWhiskBlockEntity.MAX_STIRS_COUNT) {
                blockEntity.craftItem();
                blockEntity.setStirsCount(SaucepanAndWhiskBlockEntity.MAX_STIRS_COUNT);
            }
        }
    }
}
