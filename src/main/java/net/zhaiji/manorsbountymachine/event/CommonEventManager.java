package net.zhaiji.manorsbountymachine.event;

import net.minecraftforge.eventbus.api.IEventBus;

public class CommonEventManager {
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        CommonEventManager.modBusListener(modEventBus);
        CommonEventManager.forgeBusListener(forgeEventBus);
    }

    public static void modBusListener(IEventBus modEventBus) {
    }

    public static void forgeBusListener(IEventBus forgeEventBus) {
        forgeEventBus.addListener(CommonEventHandler::handlerAddReloadListenerEvent);
        forgeEventBus.addListener(CommonEventHandler::handlerVillagerTradesEvent);
    }
}
