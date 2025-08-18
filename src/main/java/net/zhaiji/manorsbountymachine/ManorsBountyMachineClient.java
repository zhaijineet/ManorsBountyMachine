package net.zhaiji.manorsbountymachine;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.zhaiji.manorsbountymachine.client.event.ClientEventManager;

@OnlyIn(Dist.CLIENT)
public class ManorsBountyMachineClient {
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        ClientEventManager.init(modEventBus, forgeEventBus);
    }
}
