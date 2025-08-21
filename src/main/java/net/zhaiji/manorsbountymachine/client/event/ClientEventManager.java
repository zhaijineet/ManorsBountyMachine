package net.zhaiji.manorsbountymachine.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;

@OnlyIn(Dist.CLIENT)
public class ClientEventManager {
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        ClientEventManager.modBusListener(modEventBus);
        ClientEventManager.forgeBusListener(forgeEventBus);
    }

    public static void modBusListener(IEventBus modEventBus) {
        modEventBus.addListener(ClientEventHandler::handlerFMLClientSetupEvent);
        modEventBus.addListener(ClientEventHandler::handlerEntityRenderersEvent$RegisterRenderers);
    }

    public static void forgeBusListener(IEventBus forgeEventBus) {
    }
}
