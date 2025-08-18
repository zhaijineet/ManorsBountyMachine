package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.OvenRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class OvenRecipeCategory implements IRecipeCategory<OvenRecipe> {
    public static final ResourceLocation OVEN_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/oven_recipe_background.png");

    public static final int TEMPERATURE_X_OFFSET = 192;
    public static final int TEMPERATURE_Y_OFFSET = 0;
    public static final int TEMPERATURE_WIDTH = 32;
    public static final int TEMPERATURE_HEIGHT = 32;

    public static final int TIME_X_OFFSET = 160;
    public static final int TIME_Y_OFFSET = 0;
    public static final int TIME_WIDTH = 32;
    public static final int TIME_HEIGHT = 32;

    public static final Rect2i TEMPERATURE_RECT = new Rect2i(
            15,
            1,
            18,
            18
    );
    public static final Rect2i TIME_RECT = new Rect2i(
            121,
            1,
            18,
            18
    );

    public IGuiHelper guiHelper;

    public OvenRecipeCategory(IGuiHelper guiHelper) {
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
    public RecipeType<OvenRecipe> getRecipeType() {
        return ManorsBountyMachineJeiPlugin.OVEN;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.jei.category.recipe.oven");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return guiHelper.createDrawableItemLike(InitBlock.OVEN.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OvenRecipe recipe, IFocusGroup focuses) {
        int[][] slots = {
                {11, 26},
                {32, 26},
                {53, 26},
                {11, 48},
                {32, 48},
                {53, 48}
        };
        for (int i = 0; i < slots.length; i++) {
            Ingredient input = recipe.input.get(i);
            if (input.isEmpty()) continue;
            builder.addInputSlot(slots[i][0], slots[i][1])
                    .addIngredients(input);
        }
        builder.addOutputSlot(106, 37)
                .addItemStack(recipe.output);
    }

    public int getTemperatureIndex(int value) {
        return switch (value) {
            case 150 -> OvenBlockEntity.Temperature.ONE_HUNDRED_FIFTY.state;
            case 200 -> OvenBlockEntity.Temperature.TWO_HUNDRED.state;
            case 250 -> OvenBlockEntity.Temperature.TWO_HUNDRED_FIFTY.state;
            default -> 0;
        };
    }

    public int getCookingTimeIndex(int value) {
        return switch (value) {
            case 100 -> OvenBlockEntity.MaxCookingTime.FIVE.state;
            case 200 -> OvenBlockEntity.MaxCookingTime.TEN.state;
            case 300 -> OvenBlockEntity.MaxCookingTime.FIFTEEN.state;
            default -> 0;
        };
    }

    @Override
    public void draw(OvenRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        guiHelper.createDrawable(OVEN_RECIPE_BACKGROUND, 0, 0, this.getWidth(), this.getHeight()).draw(guiGraphics);
        guiGraphics.pose().pushPose();
        float scale = 0.5F;
        guiGraphics.pose().scale(scale, scale, scale);
        int temperatureIndex = this.getTemperatureIndex(recipe.temperature) - 1;
        guiHelper.createDrawable(OVEN_RECIPE_BACKGROUND, TEMPERATURE_X_OFFSET, TEMPERATURE_Y_OFFSET + TEMPERATURE_HEIGHT * temperatureIndex, TEMPERATURE_WIDTH, TEMPERATURE_HEIGHT).draw(guiGraphics, (int) (16 / scale), (int) (2 / scale));
        int cookingTimeIndex = this.getCookingTimeIndex(recipe.cookingTime) - 1;
        guiHelper.createDrawable(OVEN_RECIPE_BACKGROUND, TIME_X_OFFSET, TIME_Y_OFFSET + TIME_HEIGHT * cookingTimeIndex, TIME_WIDTH, TIME_HEIGHT).draw(guiGraphics, (int) (122 / scale), (int) (2 / scale));
        guiGraphics.pose().popPose();
        Font font = Minecraft.getInstance().font;
        if (TEMPERATURE_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(font, Component.literal(recipe.temperature + "°"), (int) mouseX, (int) mouseY);
        }
        if (TIME_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(font, Component.literal(recipe.cookingTime / 20 + "s"), (int) mouseX, (int) mouseY);
        }
    }
}
