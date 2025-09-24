package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
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

public class OvenRecipeCategory extends BaseRecipeCategory<OvenRecipe> {
    public static final ResourceLocation OVEN_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/oven_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.oven";

    public static final int TEMPERATURE_X_OFFSET = 154;
    public static final int TEMPERATURE_Y_OFFSET = 0;
    public static final int TEMPERATURE_WIDTH = 19;
    public static final int TEMPERATURE_HEIGHT = 14;

    public static final int TIME_X_OFFSET = 154;
    public static final int TIME_Y_OFFSET = 15;
    public static final int TIME_WIDTH = 16;
    public static final int TIME_HEIGHT = 13;

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

    public OvenRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.OVEN, OVEN_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.OVEN.get());
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
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        int temperatureIndex = this.getTemperatureIndex(recipe.temperature) - 1;
        this.guiHelper.createDrawable(OVEN_RECIPE_BACKGROUND, TEMPERATURE_X_OFFSET + TEMPERATURE_WIDTH * temperatureIndex, TEMPERATURE_Y_OFFSET, TEMPERATURE_WIDTH, TEMPERATURE_HEIGHT).draw(guiGraphics, 14, 2);
        int cookingTimeIndex = this.getCookingTimeIndex(recipe.maxCookingTime) - 1;
        this.guiHelper.createDrawable(OVEN_RECIPE_BACKGROUND, TIME_X_OFFSET, TIME_Y_OFFSET + TIME_HEIGHT * cookingTimeIndex, TIME_WIDTH, TIME_HEIGHT).draw(guiGraphics, 122, 5);
        Font font = Minecraft.getInstance().font;
        if (TEMPERATURE_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(font, Component.literal(recipe.temperature + "°"), (int) mouseX, (int) mouseY);
        }
        if (TIME_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(font, Component.literal(recipe.maxCookingTime / 20 + "s"), (int) mouseX, (int) mouseY);
        }
    }
}
