package net.zhaiji.manorsbountymachine.compat.kjs;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.compat.farmersdelight.CookingPotRecipeCompat;
import net.zhaiji.manorsbountymachine.compat.farmersdelight.CuttingBoardRecipeCompat;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;

public class ManorsBountyMachineReloadListener implements ResourceManagerReloadListener {
    private final RecipeManager recipeManager;

    public ManorsBountyMachineReloadListener(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        SmokingRecipeManager.init(recipeManager);
        CuttingBoardRecipeCompat.init(recipeManager);
        CookingPotRecipeCompat.init(recipeManager);
        SlotInputLimitManager.init(recipeManager);
    }
}
