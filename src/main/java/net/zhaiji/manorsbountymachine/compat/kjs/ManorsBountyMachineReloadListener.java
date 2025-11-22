package net.zhaiji.manorsbountymachine.compat.kjs;

import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.compat.farmersdelight.CookingPotRecipeCompat;
import net.zhaiji.manorsbountymachine.compat.farmersdelight.CuttingBoardRecipeCompat;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;

public class ManorsBountyMachineReloadListener implements ResourceManagerReloadListener {
    public static ReloadableServerResources serverResources;

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        if (serverResources != null) {
            RecipeManager recipeManager = serverResources.getRecipeManager();
            SmokingRecipeManager.init(recipeManager);
            CuttingBoardRecipeCompat.init(recipeManager);
            CookingPotRecipeCompat.init(recipeManager);
            SlotInputLimitManager.init(recipeManager);
        }
    }
}
