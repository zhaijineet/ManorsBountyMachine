package net.zhaiji.manorsbountymachine.compat.manors_bounty;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.zhaiji.manorsbountymachine.recipe.FastFryRecipe;
import net.zhaiji.manorsbountymachine.recipe.OvenRecipe;

import java.util.ArrayList;
import java.util.List;

import static net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity.INPUT_SLOTS;

public class SmokingRecipeManager {
    public static List<FastFryRecipe> fastFryRecipes = new ArrayList<>();

    public static List<OvenRecipe> ovenRecipes = new ArrayList<>();

    public static void reset() {
        fastFryRecipes.clear();
        ovenRecipes.clear();
    }

    public static void init(RecipeManager recipeManager) {
        reset();
        List<SmokingRecipe> smokingRecipes = recipeManager.getAllRecipesFor(RecipeType.SMOKING);
        smokingRecipes.forEach(recipe -> fastFryRecipes.add(toFastFryRecipe(recipe)));
        smokingRecipes.forEach(recipe -> ovenRecipes.add(toOvenRecipe(recipe)));
    }

    public static FastFryRecipe toFastFryRecipe(SmokingRecipe recipe) {
        return new FastFryRecipe(recipe.getId(), recipe.getIngredients().get(0), recipe.getResultItem(null));
    }

    public static OvenRecipe toOvenRecipe(SmokingRecipe recipe) {
        NonNullList<Ingredient> input = NonNullList.withSize(INPUT_SLOTS.length, Ingredient.EMPTY);
        input.set(0, recipe.getIngredients().get(0));
        return new OvenRecipe(recipe.getId(), 200, 10 * 20, input, recipe.getResultItem(null));
    }
}
