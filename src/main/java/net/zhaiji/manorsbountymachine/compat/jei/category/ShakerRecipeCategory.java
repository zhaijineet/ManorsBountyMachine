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
import net.zhaiji.manorsbountymachine.recipe.ShakerRecipe;
import net.zhaiji.manorsbountymachine.register.InitItem;
import org.jetbrains.annotations.Nullable;

public class ShakerRecipeCategory implements IRecipeCategory<ShakerRecipe> {
    public static final ResourceLocation OVEN_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/shaker_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.shaker";

    public IGuiHelper guiHelper;

    public ShakerRecipeCategory(IGuiHelper guiHelper) {
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
    public RecipeType<ShakerRecipe> getRecipeType() {
        return ManorsBountyMachineJeiPlugin.SHAKER;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitItem.SHAKER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ShakerRecipe recipe, IFocusGroup focuses) {
        int[][] slots = {
                {110, 51},
                {17, 19},
                {35, 19},
                {53, 19},
                {17, 37},
                {35, 37},
                {53, 37}
        };
        for (int i = 0; i < slots.length; i++) {
            builder.addInputSlot(slots[i][0], slots[i][1])
                    .addIngredients(recipe.input.get(i));
        }
        builder.addOutputSlot(110, 28)
                .addItemStack(recipe.output);
    }

    @Override
    public void draw(ShakerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.guiHelper.createDrawable(OVEN_RECIPE_BACKGROUND, 0, 0, this.getWidth(), this.getHeight()).draw(guiGraphics);
    }
}
