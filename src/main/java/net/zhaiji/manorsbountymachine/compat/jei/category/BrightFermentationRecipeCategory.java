package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.BrightFermentationRecipe;

public class BrightFermentationRecipeCategory extends BaseFermentationRecipeCategory<BrightFermentationRecipe> {
    public static final ResourceLocation BRIGHT_FERMENTATION_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/bright_fermentation_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.bright_fermentation";


    public BrightFermentationRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.BRIGHT_FERMENTATION, BRIGHT_FERMENTATION_RECIPE_BACKGROUND, TRANSLATABLE);
    }
}
