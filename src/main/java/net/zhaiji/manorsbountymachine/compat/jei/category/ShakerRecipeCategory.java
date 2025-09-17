package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.ShakerRecipe;
import net.zhaiji.manorsbountymachine.register.InitItem;
import org.jetbrains.annotations.Nullable;

public class ShakerRecipeCategory extends BaseRecipeCategory<ShakerRecipe> {
    public static final ResourceLocation OVEN_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/shaker_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.shaker";

    public ShakerRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.SHAKER, OVEN_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitItem.SHAKER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ShakerRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(110, 51)
                .addIngredients(recipe.container);
        int[][] slots = {
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
}
