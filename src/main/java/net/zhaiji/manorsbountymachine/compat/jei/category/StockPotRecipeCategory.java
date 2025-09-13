package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.StockPotRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

public class StockPotRecipeCategory extends BaseRecipeCategory<StockPotRecipe> {
    public static final ResourceLocation STOCK_POT_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/stock_pot_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.stock_pot";

    public StockPotRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.STOCK_POT, STOCK_POT_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.STOCK_POT.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StockPotRecipe recipe, IFocusGroup focuses) {
        int[][] slots = {
                {2, 26},
                {21, 26},
                {40, 26},
                {59, 26},
                {2, 45},
                {21, 45},
                {40, 45},
                {59, 45}
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
                {85, 7},
                {129, 7},
                {80, 26},
                {135, 26},
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
            builder.addInputSlot(107, 52)
                    .addIngredients(recipe.container);
        }
        builder.addOutputSlot(107, 29)
                .addItemStack(recipe.output);
    }
}
