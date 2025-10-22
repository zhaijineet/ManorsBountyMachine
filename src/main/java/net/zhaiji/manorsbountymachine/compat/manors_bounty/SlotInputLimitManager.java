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

    public static boolean needInit = false;

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
        if (needInit) {
            reset(recipeManager);
            needInit = false;
        }
    }

    public static void initIceCreamMachineSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.ICE_CREAM_RECIPE_TYPE.get()).forEach(recipe -> {
            Fluid fluid = recipe.fluidStack.getFluid();
            if (!ICE_CREAM_MACHINE_FLUID_LIMIT.contains(fluid)) {
                ICE_CREAM_MACHINE_FLUID_LIMIT.add(fluid);
            }
            Ingredient container = recipe.container;
            if (!container.isEmpty() && !ICE_CREAM_MACHINE_INPUT_LIMIT.contains(container)) {
                ICE_CREAM_MACHINE_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initFryerSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.FAST_FRY_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient input = recipe.input;
            if (!FRYER_INPUT_LIMIT.contains(input)) {
                FRYER_INPUT_LIMIT.add(input);
            }
        });
        SmokingRecipeManager.fastFryRecipes.forEach(recipe -> {
            Ingredient input = recipe.input;
            if (!FRYER_INPUT_LIMIT.contains(input)) {
                FRYER_INPUT_LIMIT.add(input);
            }
        });
        recipeManager.getAllRecipesFor(InitRecipe.SLOW_FRY_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient input = recipe.input;
            if (!FRYER_INPUT_LIMIT.contains(input)) {
                FRYER_INPUT_LIMIT.add(input);
            }
        });
    }

    public static void initOvenSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.OVEN_RECIPE_TYPE.get()).forEach(recipe -> {
            recipe.input.forEach(ingredient -> {
                if (!OVEN_INPUT_LIMIT.contains(ingredient)) {
                    OVEN_INPUT_LIMIT.add(ingredient);
                }
            });
        });
        SmokingRecipeManager.ovenRecipes.forEach(recipe -> {
            recipe.input.forEach(ingredient -> {
                if (!OVEN_INPUT_LIMIT.contains(ingredient)) {
                    OVEN_INPUT_LIMIT.add(ingredient);
                }
            });
        });
    }

    public static void initTeapotSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.TEAPOT_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!TEAPOT_CUP_LIMIT.contains(container)) {
                TEAPOT_CUP_LIMIT.add(container);
            }
            ItemStack output = recipe.output;
            if (!TEAPOT_CUP_LIMIT.contains(Ingredient.of(output.getItem()))) {
                TEAPOT_CUP_LIMIT.add(Ingredient.of(output.getItem()));
            }
            Ingredient drink = recipe.input.get(0);
            if (!TEAPOT_DRINK_LIMIT.contains(drink)) {
                TEAPOT_DRINK_LIMIT.add(drink);
            }
            Ingredient material = recipe.input.get(1);
            if (!TEAPOT_MATERIAL_LIMIT.contains(material)) {
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
            if (!FERMENTER_INPUT_LIMIT.contains(container)) {
                FERMENTER_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initBlenderSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.BLENDER_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!BLENDER_INPUT_LIMIT.contains(container)) {
                BLENDER_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initCuttingBoardToolLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient tool = recipe.tool;
            if (!CUTTING_BOARD_TOOL_LIMIT.contains(tool)) {
                CUTTING_BOARD_TOOL_LIMIT.add(tool);
            }
        });
        recipeManager.getAllRecipesFor(InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient tool = recipe.tool;
            if (!CUTTING_BOARD_TOOL_LIMIT.contains(tool)) {
                CUTTING_BOARD_TOOL_LIMIT.add(tool);
            }
        });
        CuttingBoardRecipeCompat.cuttingBoardSingleRecipes.forEach(recipe -> {
            Ingredient tool = recipe.tool;
            if (!CUTTING_BOARD_TOOL_LIMIT.contains(tool)) {
                CUTTING_BOARD_TOOL_LIMIT.add(tool);
            }
        });
    }

    public static void initStockPotSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.STOCK_POT_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!STOCK_POT_INPUT_LIMIT.contains(container)) {
                STOCK_POT_INPUT_LIMIT.add(container);
            }
        });
        CookingPotRecipeCompat.stockPotRecipes.forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!STOCK_POT_INPUT_LIMIT.contains(container)) {
                STOCK_POT_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initSaucepanAndWhiskSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!SAUCEPAN_AND_WHISK_INPUT_LIMIT.contains(container)) {
                SAUCEPAN_AND_WHISK_INPUT_LIMIT.add(container);
            }
        });
    }

    public static void initShakerSlotLimit(RecipeManager recipeManager) {
        recipeManager.getAllRecipesFor(InitRecipe.SHAKER_RECIPE_TYPE.get()).forEach(recipe -> {
            Ingredient container = recipe.container;
            if (!SHAKER_INPUT_LIMIT.contains(container)) {
                SHAKER_INPUT_LIMIT.add(container);
            }
        });
    }
}
