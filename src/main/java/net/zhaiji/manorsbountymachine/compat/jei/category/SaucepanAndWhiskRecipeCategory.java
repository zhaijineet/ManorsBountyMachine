package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.SaucepanAndWhiskRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class SaucepanAndWhiskRecipeCategory extends BaseRecipeCategory<SaucepanAndWhiskRecipe> {
    public static final ResourceLocation SAUCEPAN_AND_WHISK_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/saucepan_and_whisk_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.saucepan_and_whisk";
    public static final Rect2i HEAT_STATE_RECT = new Rect2i(
            22,
            9,
            14,
            14
    );

    public SaucepanAndWhiskRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.SAUCEPAN_AND_WHISK, SAUCEPAN_AND_WHISK_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.SAUCEPAN_AND_WHISK.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SaucepanAndWhiskRecipe recipe, IFocusGroup focuses) {
        int[][] slots = {
                {40, 12},
                {59, 12},
                {78, 12},
                {40, 32},
                {59, 32},
                {78, 32}
        };
        for (int i = 0; i < slots.length; i++) {
            Ingredient ingredient = recipe.mainInput.get(i);
            if (ingredient.isEmpty()) {
                continue;
            }
            builder.addInputSlot(slots[i][0], slots[i][1])
                    .addIngredients(ingredient);
        }
        slots = new int[][]{
                {4, 24},
                {22, 24},
                {4, 42},
                {22, 42},
        };
        for (int i = 0; i < slots.length; i++) {
            Ingredient ingredient = recipe.secondaryInput.get(i);
            if (ingredient.isEmpty()) {
                continue;
            }
            builder.addInputSlot(slots[i][0], slots[i][1])
                    .addIngredients(ingredient);
        }
        if (recipe.hasContainer()) {
            builder.addInputSlot(109, 52)
                    .addIngredients(recipe.container);
        }
        builder.addOutputSlot(109, 29)
                .addItemStack(recipe.output);
    }

    public Component getHeatStateTooltip(SaucepanAndWhiskRecipe recipe) {
        return Component.translatable("gui.jei.category.recipe.saucepan_and_whisk.heat_check_" + recipe.heatState.ordinal());
    }

    @Override
    public void draw(SaucepanAndWhiskRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        switch (recipe.heatState) {
            case NEED ->
                    this.guiHelper.createDrawable(SAUCEPAN_AND_WHISK_RECIPE_BACKGROUND, 154, 13, 16, 11).draw(guiGraphics, 22, 10);
            case NO_NEED ->
                    this.guiHelper.createDrawable(SAUCEPAN_AND_WHISK_RECIPE_BACKGROUND, 154, 0, 12, 12).draw(guiGraphics, 23, 10);
        }
        if (HEAT_STATE_RECT.contains((int) mouseX, (int) mouseY)) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, this.getHeatStateTooltip(recipe), (int) mouseX, (int) mouseY);
        }
    }
}
