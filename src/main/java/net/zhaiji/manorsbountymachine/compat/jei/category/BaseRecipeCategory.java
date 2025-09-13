package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseRecipeCategory<T> implements IRecipeCategory<T> {
    public IGuiHelper guiHelper;
    public int width;
    public int height;
    public RecipeType<T> recipeType;
    public ResourceLocation background;
    public String translatable;

    public BaseRecipeCategory(IGuiHelper guiHelper, RecipeType<T> recipeType, ResourceLocation background, String translatable) {
        this.guiHelper = guiHelper;
        this.width = 154;
        this.height = 74;
        this.recipeType = recipeType;
        this.background = background;
        this.translatable = translatable;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public RecipeType<T> getRecipeType() {
        return this.recipeType;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(this.translatable);
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.guiHelper.createDrawable(background, 0, 0, this.getWidth(), this.getHeight()).draw(guiGraphics);
    }
}
