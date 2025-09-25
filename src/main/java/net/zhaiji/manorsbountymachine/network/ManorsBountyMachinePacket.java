package net.zhaiji.manorsbountymachine.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.network.server.packet.*;

public class ManorsBountyMachinePacket {
    public static final String VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "main"),
            () -> VERSION,
            VERSION::equals,
            VERSION::equals
    );
    public static int id = 0;

    public static void register() {
        registerServerPacket();
        registerClientPacket();
    }

    public static void registerServerPacket() {
        INSTANCE.messageBuilder(IceCreamTowFlavorSwitchPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(IceCreamTowFlavorSwitchPacket::decode)
                .encoder(IceCreamTowFlavorSwitchPacket::encode)
                .consumerMainThread(IceCreamTowFlavorSwitchPacket::handle)
                .add();

        INSTANCE.messageBuilder(IceCreamCraftPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(IceCreamCraftPacket::decode)
                .encoder(IceCreamCraftPacket::encode)
                .consumerMainThread(IceCreamCraftPacket::handle)
                .add();

        INSTANCE.messageBuilder(StopFryingPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(StopFryingPacket::decode)
                .encoder(StopFryingPacket::encode)
                .consumerMainThread(StopFryingPacket::handle)
                .add();

        INSTANCE.messageBuilder(FryingStartPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(FryingStartPacket::decode)
                .encoder(FryingStartPacket::encode)
                .consumerMainThread(FryingStartPacket::handle)
                .add();

        INSTANCE.messageBuilder(SyncOvenTimeOrTemperaturePacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncOvenTimeOrTemperaturePacket::decode)
                .encoder(SyncOvenTimeOrTemperaturePacket::encode)
                .consumerMainThread(SyncOvenTimeOrTemperaturePacket::handle)
                .add();

        INSTANCE.messageBuilder(BakeItemCraftPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(BakeItemCraftPacket::decode)
                .encoder(BakeItemCraftPacket::encode)
                .consumerMainThread(BakeItemCraftPacket::handle)
                .add();

        INSTANCE.messageBuilder(BrewingStartPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(BrewingStartPacket::decode)
                .encoder(BrewingStartPacket::encode)
                .consumerMainThread(BrewingStartPacket::handle)
                .add();

        INSTANCE.messageBuilder(FermentationStartPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(FermentationStartPacket::decode)
                .encoder(FermentationStartPacket::encode)
                .consumerMainThread(FermentationStartPacket::handle)
                .add();

        INSTANCE.messageBuilder(FermentationStopPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(FermentationStopPacket::decode)
                .encoder(FermentationStopPacket::encode)
                .consumerMainThread(FermentationStopPacket::handle)
                .add();

        INSTANCE.messageBuilder(BlenderStartPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(BlenderStartPacket::decode)
                .encoder(BlenderStartPacket::encode)
                .consumerMainThread(BlenderStartPacket::handle)
                .add();

        INSTANCE.messageBuilder(StockPotStartPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(StockPotStartPacket::decode)
                .encoder(StockPotStartPacket::encode)
                .consumerMainThread(StockPotStartPacket::handle)
                .add();

        INSTANCE.messageBuilder(TrySaucepanAndWhiskCraftPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(TrySaucepanAndWhiskCraftPacket::decode)
                .encoder(TrySaucepanAndWhiskCraftPacket::encode)
                .consumerMainThread(TrySaucepanAndWhiskCraftPacket::handle)
                .add();
    }

    public static void registerClientPacket() {
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(ServerPlayer serverPlayer, MSG message) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

    public static <MSG> void sendToClientWithChunk(LevelChunk levelChunk, MSG message) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> levelChunk), message);
    }
}
