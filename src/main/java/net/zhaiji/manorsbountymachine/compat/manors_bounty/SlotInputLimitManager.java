package net.zhaiji.manorsbountymachine.compat.manors_bounty;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.material.Fluid;
import net.zhaiji.manorsbountymachine.compat.farmersdelight.CookingPotRecipeCompat;
import net.zhaiji.manorsbountymachine.compat.farmersdelight.CuttingBoardRecipeCompat;
import net.zhaiji.manorsbountymachine.recipe.BaseFermentationRecipe;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

import java.util.ArrayList;
import java.util.List;

public class SlotInputLimitManager {
    public static final List<Fluid> ICE_CREAM_MACHINE_FLUID_LIMIT = new ArrayList<>();
    public static final List<Ingredient> ICE_CREAM_MACHINE_INPUT_LIMIT = new ArrayList<>();
    public static final List<Ingredient> FRYER_INPUT_LIMIT = new ArrayList<>();
    public static final List<Ingredient> OVEN_INPUT_LIMIT = new ArrayList<>();
    public static final List<Ingredient> TEAPOT_CUP_LIMIT = new ArrayList<>();
    public static final List<Ingredient> TEAPOT_DRINK_LIMIT = new ArrayList<>();
    public static final List<Ingredient> TEAPOT_MATERIAL_LIMIT = new ArrayList<>();
    public static final List<Ingredient> FERMENTER_INPUT_LIMIT = new ArrayList<>();
    public static final List<Ingredient> BLENDER_INPUT_LIMIT = new ArrayList<>();
    public static final List<Ingredient> CUTTING_BOARD_TOOL_LIMIT = new ArrayList<>();
    public static final List<Ingredient> STOCK_POT_INPUT_LIMIT = new ArrayList<>();
    public static final List<Ingredient> SAUCEPAN_AND_WHISK_INPUT_LIMIT = new ArrayList<>();
    public static final List<Ingredient> SHAKER_INPUT_LIMIT = new ArrayList<>();

    public static void reset(RecipeManager recipeManager) {
        ICE_CREAM_MACHINE_FLUID_LIMIT.clear();
        ICE_CREAM_MACHINE_INPUT_LIMIT.clear();
        FRYER_INPUT_LIMIT.clear();
        OVEN_INPUT_LIMIT.clear();
        TEAPOT_CUP_LIMIT.clear();
        TEAPOT_DRINK_LIMIT.clear();
        TEAPOT_MATERIAL_LIMIT.clear();
        FERMENTER_INPUT_LIMIT.clear();
        BLENDER_INPUT_LIMIT.clear();
        CUTTING_BOARD_TOOL_LIMIT.clear();
        STOCK_POT_INPUT_LIMIT.clear();
        SAUCEPAN_AND_WHISK_INPUT_LIMIT.clear();
        SHAKER_INPUT_LIMIT.clear();

        initIceCreamMachineSlotLimit(recipeManager);
        initFryerSlotLimit(recipeManager);
        initOvenSlotLimit(recipeManager);
        initTeapotSlotLimit(recipeManager);
        initFermenterSlotLimit(recipeManager);
        initBlenderSlotLimit(recipeManager);
        initCuttingBoardToolLimit(recipeManager);
        initStockPotSlotLimit(recipeManager);
        initSaucepanAndWhiskSlotLimit(recipeManager);
        initShakerSlotLimit(recipeManager);
    }

    public static void init(RecipeManager recipeManager) {
        reset(recipeManager);
    }

    public static void initIceCreamMachineSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.ICE_CREAM_RECIPE_TYPE.get()).forEach(recipe -> {
            Fluid fluid = recipe.fluidStack.getFluid();
            if (!ICE_CREAM_MACHINE_FLUID_LIMIT.contains(fluid)) {
                ICE_CREAM_MACHINE_FLUID_LIMIT.add(fluid);
            }
            Ingredient container = recipe.container;
            if (!container.isEmpty() && !ingredientOverlaps(ICE_CREAM_MACHINE_INPUT_LIMIT, container)) {
                ICE_CREAM_MACHINE_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initFryerSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.FAST_FRY_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient input = recipe.input;
            if (!ingredientOverlaps(FRYER_INPUT_LIMIT, input)) {
                FRYER_INPUT_LIMIT.add(input);
            }
        });
        SmokingRecipeManager.fastFryRecipes.forEach(recipe -> {
            Ingredient input = recipe.input;
            if (!ingredientOverlaps(FRYER_INPUT_LIMIT, input)) {
                FRYER_INPUT_LIMIT.add(input);
            }
        });
        recipeManager.getAllRecipesFor(InitRecipe.SLOW_FRY_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient input = recipe.input;
            if (!ingredientOverlaps(FRYER_INPUT_LIMIT, input)) {
                FRYER_INPUT_LIMIT.add(input);
            }
        });
    }

    public static void initOvenSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.OVEN_RECIPE_TYPE.get()).forEach(recipe -> {
            recipe.input.forEach(ingredient -> {
                if (!ingredientOverlaps(OVEN_INPUT_LIMIT, ingredient)) {
                    OVEN_INPUT_LIMIT.add(ingredient);
                }
            });
        });
        SmokingRecipeManager.ovenRecipes.forEach(recipe -> {
            recipe.input.forEach(ingredient -> {
                if (!ingredientOverlaps(OVEN_INPUT_LIMIT, ingredient)) {
                    OVEN_INPUT_LIMIT.add(ingredient);
                }
            });
        });
    }

    public static void initTeapotSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.TEAPOT_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!ingredientOverlaps(TEAPOT_CUP_LIMIT, container)) {
                TEAPOT_CUP_LIMIT.add(container);
            }
            ItemStack output = recipe.output;
            Ingredient outputIngredient = Ingredient.of(output.getItem());
            if (!ingredientOverlaps(TEAPOT_CUP_LIMIT, outputIngredient)) {
                TEAPOT_CUP_LIMIT.add(outputIngredient);
            }
            Ingredient drink = recipe.input.get(0);
            if (!ingredientOverlaps(TEAPOT_DRINK_LIMIT, drink)) {
                TEAPOT_DRINK_LIMIT.add(drink);
            }
            Ingredient material = recipe.input.get(1);
            if (!ingredientOverlaps(TEAPOT_MATERIAL_LIMIT, material)) {
                TEAPOT_MATERIAL_LIMIT.add(material);
            }
        });
    }

    public static void initFermenterSlotLimit(RecipeManager recipeManager) {
        List<BaseFermentationRecipe> recipes = new ArrayList<>();
        recipes.addAll(recipeManager.getAllRecipesFor(InitRecipe.BRIGHT_FERMENTATION_RECIPE_TYPE.get()));
        recipes.addAll(recipeManager.getAllRecipesFor(InitRecipe.NORMAL_FERMENTATION_RECIPE_TYPE.get()));
        recipes.addAll(recipeManager.getAllRecipesFor(InitRecipe.DIM_FERMENTATION_RECIPE_TYPE.get()));
        recipes.forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!ingredientOverlaps(FERMENTER_INPUT_LIMIT, container)) {
                FERMENTER_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initBlenderSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.BLENDER_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!ingredientOverlaps(BLENDER_INPUT_LIMIT, container)) {
                BLENDER_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initCuttingBoardToolLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient tool = recipe.tool;
            if (!ingredientOverlaps(CUTTING_BOARD_TOOL_LIMIT, tool)) {
                CUTTING_BOARD_TOOL_LIMIT.add(tool);
            }
        });
        recipeManager.getAllRecipesFor(InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient tool = recipe.tool;
            if (!ingredientOverlaps(CUTTING_BOARD_TOOL_LIMIT, tool)) {
                CUTTING_BOARD_TOOL_LIMIT.add(tool);
            }
        });
        CuttingBoardRecipeCompat.cuttingBoardSingleRecipes.forEach(recipe -> {
            Ingredient tool = recipe.tool;
            if (!ingredientOverlaps(CUTTING_BOARD_TOOL_LIMIT, tool)) {
                CUTTING_BOARD_TOOL_LIMIT.add(tool);
            }
        });
    }

    public static void initStockPotSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.STOCK_POT_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!ingredientOverlaps(STOCK_POT_INPUT_LIMIT, container)) {
                STOCK_POT_INPUT_LIMIT.add(container);
            }
        });
        CookingPotRecipeCompat.stockPotRecipes.forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!ingredientOverlaps(STOCK_POT_INPUT_LIMIT, container)) {
                STOCK_POT_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initSaucepanAndWhiskSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!ingredientOverlaps(SAUCEPAN_AND_WHISK_INPUT_LIMIT, container)) {
                SAUCEPAN_AND_WHISK_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initShakerSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.SHAKER_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!ingredientOverlaps(SHAKER_INPUT_LIMIT, container)) {
                SHAKER_INPUT_LIMIT.add(container);
            }
        });
    }

    private static boolean ingredientOverlaps(List<Ingredient> list, Ingredient newIngredient) {
        if (newIngredient.isEmpty()) return false;
        for (Ingredient existing : list) {
            if (existing.isEmpty()) continue;
            // 获取现有 Ingredient 的所有物品堆叠
            ItemStack[] existingItems = existing.getItems();
            for (ItemStack stack : existingItems) {
                if (!stack.isEmpty() && newIngredient.test(stack)) {
                    return true; // 找到重叠
                }
            }
        }
        return false;
    }
}
