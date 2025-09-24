package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.zhaiji.manorsbountymachine.recipe.BaseFermentationRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public abstract class BaseFermentationRecipeCategory<T extends BaseFermentationRecipe> extends BaseRecipeCategory<T> {
    public static final int TIME_X_OFFSET = 48;
    public static final int TIME_Y_OFFSET = 80;
    public static final int TIME_WIDTH = 16;
    public static final int TIME_HEIGHT = 13;

    public static final Rect2i TIME_RECT = new Rect2i(
            80,
            53,
            18,
            18
    );

    public BaseFermentationRecipeCategory(IGuiHelper guiHelper, RecipeType<T> recipeType, ResourceLocation background, String translatable) {
        super(guiHelper, recipeType, background, translatable);
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
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        int minutes = recipe.maxCookingTime / (20 * 60);
        int index = 0;
        if (minutes >= 40) {
            index = 3;
        } else if(minutes>=20){
            index = 2;
        } else if (minutes >= 10) {
            index = 1;
        }
        this.guiHelper.createDrawable(this.background, TIME_X_OFFSET, TIME_Y_OFFSET + TIME_HEIGHT * index, TIME_WIDTH, TIME_HEIGHT).draw(guiGraphics, 80, 53);
        if (TIME_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal(minutes + "minutes"), (int) mouseX, (int) mouseY);
        }
    }
}
