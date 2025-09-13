package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.SlowFryRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class SlowFryRecipeCategory extends BaseRecipeCategory<SlowFryRecipe> {
    public static final ResourceLocation SLOW_FRY_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/slow_fry_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.slow_fry";

    public SlowFryRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.SLOW_FRY, SLOW_FRY_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.FRYER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SlowFryRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(40, 27)
                .addIngredients(recipe.input);
        builder.addOutputSlot(108, 27)
                .addItemStack(recipe.output);
    }
}
