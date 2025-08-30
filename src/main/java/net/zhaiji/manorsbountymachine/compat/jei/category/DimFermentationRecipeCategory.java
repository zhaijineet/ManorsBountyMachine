package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.DimFermentationRecipe;

public class DimFermentationRecipeCategory extends BaseFermentationRecipeCategory<DimFermentationRecipe> {
    public static final ResourceLocation DIM_FERMENTATION_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/dim_fermentation_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.dim_fermentation";

    public DimFermentationRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.DIM_FERMENTATION, DIM_FERMENTATION_RECIPE_BACKGROUND, TRANSLATABLE);
    }
}
