package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;

public class FryerSmokingRecipeCategory extends FastFryRecipeCategory {
    public static final String TRANSLATABLE = "gui.jei.category.recipe.fryer_smoking";

    public FryerSmokingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.recipeType = ManorsBountyMachineJeiPlugin.FRYER_SMOKING;
        this.translatable = TRANSLATABLE;
    }
}
