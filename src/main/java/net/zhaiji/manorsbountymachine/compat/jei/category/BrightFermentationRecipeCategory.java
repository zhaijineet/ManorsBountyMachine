package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.BrightFermentationRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class BrightFermentationRecipeCategory implements IRecipeCategory<BrightFermentationRecipe> {
    public static final ResourceLocation BRIGHT_FERMENTATION_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/bright_fermentation_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.bright_fermentation";

    public static final Rect2i TIME_RECT = new Rect2i(
            80,
            53,
            18,
            18
    );

    public IGuiHelper guiHelper;

    public BrightFermentationRecipeCategory(IGuiHelper guiHelper) {
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
    public RecipeType<BrightFermentationRecipe> getRecipeType() {
        return ManorsBountyMachineJeiPlugin.BRIGHT_FERMENTATION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.FERMENTER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BrightFermentationRecipe recipe, IFocusGroup focuses) {
        if (recipe.hasBottle()) {
            builder.addInputSlot(110, 53)
                    .addIngredients(recipe.getBottle());
        }
        int[][] slots = {
                {1, 25, 20},
                {2, 45, 20},
                {3, 25, 40},
                {4, 45, 40},
        };
        for (int[] slot : slots) {
            Ingredient input = recipe.input.get(slot[0]);
            if (!input.isEmpty()) {
                builder.addInputSlot(slot[1], slot[2])
                        .addIngredients(input);
            }
        }
        builder.addOutputSlot(110, 30)
                .addItemStack(recipe.output);
    }

    @Override
    public void draw(BrightFermentationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.guiHelper.createDrawable(BRIGHT_FERMENTATION_RECIPE_BACKGROUND, 0, 0, this.getWidth(), this.getHeight()).draw(guiGraphics);
        if (TIME_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(recipe.cookingTime / (20 * 60) + "minutes"), (int) mouseX, (int) mouseY);
        }
    }
}
