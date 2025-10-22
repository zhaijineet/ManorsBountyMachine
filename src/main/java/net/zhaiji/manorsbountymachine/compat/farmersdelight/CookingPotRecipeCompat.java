package net.zhaiji.manorsbountymachine.compat.farmersdelight;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.recipe.StockPotRecipe;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.ArrayList;
import java.util.List;

import static net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity.MAIN_INPUT_SLOTS;
import static net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity.SECONDARY_INPUT_SLOTS;

public class CookingPotRecipeCompat {
    public static boolean needInit = false;

    public static List<StockPotRecipe> stockPotRecipes = new ArrayList<>();

    public static void reset(RecipeManager recipeManager) {
        if (!FarmersDelightCompat.isLoad()) return;
        stockPotRecipes.clear();
        List<CookingPotRecipe> cookingPotRecipes = recipeManager.getAllRecipesFor(ModRecipeTypes.COOKING.get());
        cookingPotRecipes.forEach(recipe -> stockPotRecipes.add(toStockPotRecipe(recipe)));
    }

    public static void init(RecipeManager recipeManager) {
        if (needInit) {
            reset(recipeManager);
            needInit = false;
        }
    }

    public static StockPotRecipe toStockPotRecipe(CookingPotRecipe recipe) {
        NonNullList<Ingredient> mainInput = NonNullList.withSize(MAIN_INPUT_SLOTS.length, Ingredient.EMPTY);
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            mainInput.set(i, recipe.getIngredients().get(i));
        }
        return new StockPotRecipe(
                recipe.getId(),
                recipe.getCookTime(),
                Ingredient.of(recipe.getOutputContainer()),
                mainInput,
                NonNullList.withSize(SECONDARY_INPUT_SLOTS.length, Ingredient.EMPTY),
                recipe.getResultItem(null)
        );
    }
}
