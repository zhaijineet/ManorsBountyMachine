package net.zhaiji.manorsbountymachine.network.server;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.zhaiji.manorsbountymachine.block.entity.*;
import net.zhaiji.manorsbountymachine.network.server.packet.*;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

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
            blockEntity.getLevel().playSound(null, blockEntity.getBlockPos(), InitSoundEvent.OVEN_KNOB_TURN.get(), SoundSource.BLOCKS);
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

    public static void handlerStockPotStop(StockPotStopPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof StockPotBlockEntity blockEntity) {
            blockEntity.stopRunning();
        }
    }

    public static void handlerTrySaucepanAndWhiskCraft(TrySaucepanAndWhiskCraftPacket packet) {
        Player player = packet.context.getSender();
        if (player.level().getBlockEntity(packet.blockPos) instanceof SaucepanAndWhiskBlockEntity blockEntity) {
            blockEntity.addStirsCount();
            blockEntity.triggerAnim("saucepan_and_whisk", "animation.saucepan_and_whisk.working");
            BlockPos blockPos = blockEntity.getBlockPos();
            if (blockEntity.stirsCount >= SaucepanAndWhiskBlockEntity.MAX_STIRS_COUNT) {
                blockEntity.getLevel().playSound(
                        null,
                        blockPos,
                        InitSoundEvent.SAUCEPAN_AND_WHISK_DONE.get(),
                        SoundSource.BLOCKS,
                        1,
                        1
                );
            } else {
                blockEntity.getLevel().playSound(
                        null,
                        blockPos,
                        blockEntity.stirsCount % 2 == 1
                                ? InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_1.get()
                                : InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_2.get(),
                        SoundSource.BLOCKS,
                        1.5F,
                        1
                );
            }
            if (blockEntity.stirsCount >= SaucepanAndWhiskBlockEntity.MAX_STIRS_COUNT) {
                blockEntity.craftItem();
                blockEntity.setStirsCount(SaucepanAndWhiskBlockEntity.MAX_STIRS_COUNT);
            }
        }
    }
}
