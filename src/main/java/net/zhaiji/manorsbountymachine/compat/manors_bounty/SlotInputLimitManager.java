package net.zhaiji.manorsbountymachine.compat.manors_bounty;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

import java.util.ArrayList;
import java.util.List;

public class SlotInputLimitManager {
    public static final List<Ingredient> ICE_CREAM_MACHINE_INPUT_LIMIT = new ArrayList<>();

    public static void initIceCreamMachineInputLimit(RecipeManager recipeManager) {
        ICE_CREAM_MACHINE_INPUT_LIMIT.clear();
        recipeManager.getAllRecipesFor(InitRecipe.ICE_CREAM_RECIPE_TYPE.get()).forEach(iceCreamRecipe -> {
            Ingredient container = iceCreamRecipe.container;
            if (!container.isEmpty() && !ICE_CREAM_MACHINE_INPUT_LIMIT.contains(container)) {
                ICE_CREAM_MACHINE_INPUT_LIMIT.add(container);
            }
        });
    }
}
