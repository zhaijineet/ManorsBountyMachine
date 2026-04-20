package net.zhaiji.manorsbountymachine.event;

import net.minecraftforge.eventbus.api.IEventBus;
import net.zhaiji.manorsbountymachine.ManorsBountyMachineConfig;

public class CommonEventManager {
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        CommonEventManager.modBusListener(modEventBus);
        CommonEventManager.forgeBusListener(forgeEventBus);
    }

    public static void modBusListener(IEventBus modEventBus) {
        modEventBus.addListener(ManorsBountyMachineConfig::handlerModConfigEvent);
    }

    public static void forgeBusListener(IEventBus forgeEventBus) {
        forgeEventBus.addListener(CommonEventHandler::handlerServerStartedEvent);
        forgeEventBus.addListener(CommonEventHandler::handlerTagsUpdatedEvent);
        forgeEventBus.addListener(CommonEventHandler::handlerVillagerTradesEvent);
        forgeEventBus.addListener(CommonEventHandler::handlerMissingMappingsEvent);
    }
}
