package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.recipe.BlenderRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class BlenderRecipeCategory extends BaseRecipeCategory<BlenderRecipe> {
    public static final ResourceLocation BLENDER_RECIPE_BACKGROUND = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/jei/blender_recipe_background.png");
    public static final String TRANSLATABLE = "gui.jei.category.recipe.blender";

    public BlenderRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, ManorsBountyMachineJeiPlugin.BLENDER, BLENDER_RECIPE_BACKGROUND, TRANSLATABLE);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.guiHelper.createDrawableItemLike(InitBlock.BLENDER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlenderRecipe recipe, IFocusGroup focuses) {
        int[][] slots = {
                {16, 10},
                {35, 10},
                {16, 29},
                {35, 29},
                {16, 48},
                {35, 48}
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
                {64, 18},
                {84, 18},
                {64, 38},
                {84, 38},
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
            builder.addInputSlot(116, 52)
                    .addItemStack(Arrays.stream(recipe.container.getItems()).findFirst().get().copyWithCount(recipe.output.getCount()));
        }
        builder.addOutputSlot(116, 29)
                .addItemStack(recipe.output);
        if (!recipe.outgrowth.isEmpty()) {
            builder.addOutputSlot(116, 6)
                    .addItemStack(recipe.outgrowth)
                    .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                        if (recipe.outgrowthChance < 1) {
                            tooltip.add(
                                    Component.translatable("gui.jei.category.recipe.chance", (int) (recipe.outgrowthChance * 100))
                                            .withStyle(ChatFormatting.YELLOW)
                            );
                        }
                    });
        }
    }
}
