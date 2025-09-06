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
import net.zhaiji.manorsbountymachine.recipe.BaseFermentationRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public abstract class BaseFermentationRecipeCategory<T extends BaseFermentationRecipe> implements IRecipeCategory<T> {
    public static final Rect2i TIME_RECT = new Rect2i(
            80,
            53,
            18,
            18
    );
    public IGuiHelper guiHelper;
    public RecipeType<T> recipeType;
    public ResourceLocation background;
    public String translatable;

    public BaseFermentationRecipeCategory(IGuiHelper guiHelper, RecipeType<T> recipeType, ResourceLocation background, String translatable) {
        this.guiHelper = guiHelper;
        this.recipeType = recipeType;
        this.background = background;
        this.translatable = translatable;
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
    public RecipeType<T> getRecipeType() {
        return recipeType;
    }

    @Override
    public Component getTitle() {
        return Component.translatable(translatable);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.FERMENTER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        if (recipe.hasContainer()) {
            builder.addInputSlot(110, 53)
                    .addIngredients(recipe.container);
        }
        int[][] slots = {
                {0, 25, 20},
                {1, 45, 20},
                {2, 25, 40},
                {3, 45, 40},
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
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.guiHelper.createDrawable(background, 0, 0, this.getWidth(), this.getHeight()).draw(guiGraphics);
        int minutes = recipe.maxCookingTime / (20 * 60);
        String texture = "textures/item/light_" + (minutes < 10 ? "0" + minutes : minutes) + ".png";
        guiGraphics.blit(ResourceLocation.withDefaultNamespace(texture), 80, 53, 0, 0, 16, 16, 16, 16);
        if (TIME_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(minutes + "minutes"), (int) mouseX, (int) mouseY);
        }
    }
}
