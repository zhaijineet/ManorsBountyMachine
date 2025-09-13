package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class CuttingBoardMultipleRecipeBuilder extends BaseRecipeBuilder {
    public final boolean isShaped;
    public final Ingredient tool;
    public final NonNullList<Ingredient> input;
    public final Item output;

    public CuttingBoardMultipleRecipeBuilder(boolean isShaped, NonNullList<Ingredient> input, Item output) {
        this(isShaped, Ingredient.EMPTY, input, output);
    }

    public CuttingBoardMultipleRecipeBuilder(boolean isShaped, Ingredient tool, NonNullList<Ingredient> input, Item output) {
        this.isShaped = isShaped;
        this.tool = tool;
        this.input = input;
        this.output = output;
    }

    @Override
    public String getRecipePath() {
        return "cutting_board_multiple/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.isShaped,
                this.tool,
                this.input,
                this.output
        );
    }

    public static class Result extends BaseResult {
        public final boolean isShaped;
        public final Ingredient tool;
        public final NonNullList<Ingredient> input;
        public final Item output;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, boolean isShaped, Ingredient tool, NonNullList<Ingredient> input, Item output) {
            super(id, recipeSerializer);
            this.isShaped = isShaped;
            this.tool = tool;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addBoolean(pJson, "isShaped", this.isShaped);
            this.addNullableIngredient(pJson, "tool", this.tool);
            this.addInput(pJson, this.input);
            this.addOutput(pJson, this.output);
        }
    }
}
