package net.zhaiji.manorsbountymachine.event;

import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;

public class CommonEventHandler {
    public static void handlerAddReloadListenerEvent(AddReloadListenerEvent event) {
        RecipeManager recipeManager = event.getServerResources().getRecipeManager();
        SlotInputLimitManager.init(recipeManager);
    }
}
