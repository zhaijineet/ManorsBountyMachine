package net.zhaiji.manorsbountymachine.event;

import net.minecraftforge.eventbus.api.IEventBus;
import net.zhaiji.manorsbountymachine.client.event.ClientEventManager;
import net.zhaiji.manorsbountymachine.datagen.DataGenHandler;

public class CommonEventManager {
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        CommonEventManager.modBusListener(modEventBus);
        CommonEventManager.forgeBusListener(forgeEventBus);
    }

    public static void modBusListener(IEventBus modEventBus) {
    }

    public static void forgeBusListener(IEventBus forgeEventBus) {
    }
}
