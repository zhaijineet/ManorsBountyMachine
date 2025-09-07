package net.zhaiji.manorsbountymachine.recipe.builder;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class DimFermentationRecipeBuilder extends BaseFermentationRecipeBuilder {
    public DimFermentationRecipeBuilder(int cookingTime, NonNullList<Ingredient> input, Item output) {
        super(cookingTime, input, output);
    }

    public DimFermentationRecipeBuilder(int cookingTime, Ingredient container, NonNullList<Ingredient> input, Item output) {
        super(cookingTime, container, input, output);
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.DIM_FERMENTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public String getRecipePath() {
        return "dim_fermentation/";
    }
}
