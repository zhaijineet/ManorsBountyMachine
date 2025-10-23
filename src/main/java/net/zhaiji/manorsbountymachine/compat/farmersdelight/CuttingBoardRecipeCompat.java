package net.zhaiji.manorsbountymachine.compat.farmersdelight;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.recipe.CuttingBoardSingleRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuttingBoardRecipeCompat {
    public static boolean needInit = false;

    public static List<CuttingBoardSingleRecipe> cuttingBoardSingleRecipes = new ArrayList<>();

    public static void reset(RecipeManager recipeManager) {
        if (!FarmersDelightCompat.isLoad()) return;
        cuttingBoardSingleRecipes.clear();
        List<CuttingBoardRecipe> cuttingRecipes = recipeManager.getAllRecipesFor(ModRecipeTypes.CUTTING.get());
        cuttingRecipes.forEach(recipe -> {
            if (recipe.getResults().size() - 1 <= 4) {
                cuttingBoardSingleRecipes.add(toCuttingBoardRecipe(recipe));
            }
        });
    }

    public static void init(RecipeManager recipeManager) {
        if (needInit) {
            reset(recipeManager);
            needInit = false;
        }
    }

    public static CuttingBoardSingleRecipe toCuttingBoardRecipe(CuttingBoardRecipe recipe) {
        Map<ItemStack, Float> outgrowths = new HashMap<>();
        for (int i = 1; i < recipe.getRollableResults().size(); i++) {
            ChanceResult chanceResult = recipe.getRollableResults().get(i);
            outgrowths.put(chanceResult.getStack(), chanceResult.getChance());
        }
        return new CuttingBoardSingleRecipe(
                recipe.getId(),
                recipe.getTool(),
                recipe.getIngredients().get(0),
                recipe.getResultItem(null),
                outgrowths
        );
    }
}
