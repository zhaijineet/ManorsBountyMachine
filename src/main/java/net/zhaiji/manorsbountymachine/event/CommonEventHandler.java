package net.zhaiji.manorsbountymachine.event;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;

public class CommonEventHandler {
    public static void handlerAddReloadListenerEvent(AddReloadListenerEvent event) {
        SmokingRecipeManager.needInit = true;
        SlotInputLimitManager.needInit = true;
    }
}
