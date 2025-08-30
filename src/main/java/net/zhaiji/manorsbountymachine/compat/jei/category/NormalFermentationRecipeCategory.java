package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.NormalFermentationRecipe;

public class NormalFermentationRecipeCategory extends BaseFermentationRecipeCategory<NormalFermentationRecipe> {
    public static final ResourceLocation NORMAL_FERMENTATION_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/normal_fermentation_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.normal_fermentation";

    public NormalFermentationRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.NORMAL_FERMENTATION, NORMAL_FERMENTATION_RECIPE_BACKGROUND, TRANSLATABLE);
    }
}
