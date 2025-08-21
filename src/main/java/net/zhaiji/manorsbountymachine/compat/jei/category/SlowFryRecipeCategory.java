package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.SlowFryRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class SlowFryRecipeCategory implements IRecipeCategory<SlowFryRecipe> {
    public static final ResourceLocation SLOW_FRY_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/slow_fry_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.slow_fry";

    public IGuiHelper guiHelper;

    public SlowFryRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
    }

    @Override
    public int getWidth() {
        return 154;
    }

    @Override
    public int getHeight() {
        return 74;
    }

    @Override
    public RecipeType<SlowFryRecipe> getRecipeType() {
        return ManorsBountyMachineJeiPlugin.SLOW_FRY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(TRANSLATABLE);
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

    @Override
    public void draw(SlowFryRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.guiHelper.createDrawable(SLOW_FRY_RECIPE_BACKGROUND, 0, 0, this.getWidth(), this.getHeight()).draw(guiGraphics);
    }
}
