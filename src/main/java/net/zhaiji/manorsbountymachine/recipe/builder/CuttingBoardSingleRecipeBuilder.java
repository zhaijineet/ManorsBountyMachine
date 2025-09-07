package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class CuttingBoardSingleRecipeBuilder extends BaseRecipeBuilder {
    public final Ingredient tool;
    public final Ingredient input;
    public final Item output;
    public final int outputCount;
    public final Item outgrowth;
    public final float outgrowthChance;

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output) {
        this(tool, input, output, 1, Items.AIR, 0);
    }

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output, Item outgrowth, float outgrowthChance) {
        this(tool, input, output, 1, outgrowth, outgrowthChance);
    }

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output, int outputCount) {
        this(tool, input, output, outputCount, Items.AIR, 0);
    }

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        this.tool = tool;
        this.input = input;
        this.output = output;
        this.outputCount = outputCount;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    @Override
    public String getRecipePath() {
        return "cutting_board_single/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.tool,
                this.input,
                this.output,
                this.outputCount,
                this.outgrowth,
                this.outgrowthChance
        );
    }

    public static class Result extends BaseResult {
        public final Ingredient tool;
        public final Ingredient input;
        public final Item output;
        public final int outputCount;
        public final Item outgrowth;
        public final float outgrowthChance;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, Ingredient tool, Ingredient input, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
            super(id, recipeSerializer);
            this.tool = tool;
            this.input = input;
            this.output = output;
            this.outputCount = outputCount;
            this.outgrowth = outgrowth;
            this.outgrowthChance = outgrowthChance;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addIngredient(pJson, "tool", this.tool);
            this.addInput(pJson, this.input);
            this.addOutput(pJson, this.output, this.outputCount);
            if (this.outgrowth != Items.AIR) {
                this.addOutgrowth(pJson, this.outgrowth);
                this.addOutgrowthChance(pJson, this.outgrowthChance);
            }
        }
    }
}
