package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;

public class OvenSmokingRecipeCategory extends OvenRecipeCategory {
    public static final String TRANSLATABLE = "gui.jei.category.recipe.oven_smoking";

    public OvenSmokingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.recipeType = ManorsBountyMachineJeiPlugin.OVEN_SMOKING;
        this.translatable = TRANSLATABLE;
    }
}
