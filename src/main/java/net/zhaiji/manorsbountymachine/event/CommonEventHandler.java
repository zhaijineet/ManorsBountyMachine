package net.zhaiji.manorsbountymachine.event;

import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.AddReloadListenerEvent;

public class CommonEventHandler {
    public static void handlerAddReloadListenerEvent(AddReloadListenerEvent event) {
        RecipeManager recipeManager = event.getServerResources().getRecipeManager();
    }
}
