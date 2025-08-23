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
import net.minecraft.world.item.crafting.Ingredient;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.TeapotRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class TeapotRecipeCategory implements IRecipeCategory<TeapotRecipe> {
    public static final ResourceLocation TEAPOT_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/teapot_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.teapot";

    public IGuiHelper guiHelper;

    public TeapotRecipeCategory(IGuiHelper guiHelper) {
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
    public RecipeType<TeapotRecipe> getRecipeType() {
        return ManorsBountyMachineJeiPlugin.TEAPOT;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.TEAPOT.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TeapotRecipe recipe, IFocusGroup focuses) {
        int[][] slots = {
                {110, 6},
                {77, 52},
                {110, 52},
                {25, 19},
                {45, 19},
                {25, 39},
                {45, 39}
        };
        for (int i = 0; i < slots.length; i++) {
            Ingredient input = recipe.input.get(i);
            if (input.isEmpty()) continue;
            builder.addInputSlot(slots[i][0], slots[i][1])
                    .addIngredients(input);
        }
        builder.addOutputSlot(110, 29)
                .addItemStack(recipe.output);
    }

    @Override
    public void draw(TeapotRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.guiHelper.createDrawable(TEAPOT_RECIPE_BACKGROUND, 0, 0, this.getWidth(), this.getHeight()).draw(guiGraphics);
    }
}
