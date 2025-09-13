package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.FastFryRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class FastFryRecipeCategory extends BaseRecipeCategory<FastFryRecipe> {
    public static final ResourceLocation FAST_FRY_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/fast_fry_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.fast_fry";

    public FastFryRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.FAST_FRY, FAST_FRY_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.FRYER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FastFryRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(40, 27)
                .addIngredients(recipe.input);
        builder.addOutputSlot(108, 27)
                .addItemStack(recipe.output);
    }
}
