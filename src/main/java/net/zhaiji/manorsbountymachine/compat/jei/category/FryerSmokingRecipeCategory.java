package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;

public class FryerSmokingRecipeCategory extends FastFryRecipeCategory {
    public FryerSmokingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.recipeType = ManorsBountyMachineJeiPlugin.FRYER_SMOKING;
    }
}
