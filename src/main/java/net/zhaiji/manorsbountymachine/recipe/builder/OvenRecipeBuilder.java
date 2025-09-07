package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class OvenRecipeBuilder extends BaseRecipeBuilder {
    public final int temperature;
    public final int cookingTime;
    public final NonNullList<Ingredient> input;
    public final Item output;
    public final int outputCount;

    public OvenRecipeBuilder(int temperature, int cookingTime, NonNullList<Ingredient> input, Item output, int outputCount) {
        this.temperature = temperature;
        this.cookingTime = cookingTime;
        this.input = input;
        this.output = output;
        this.outputCount = outputCount;
    }

    @Override
    public String getRecipePath() {
        return "oven/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.OVEN_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.temperature,
                this.cookingTime,
                this.input,
                this.output,
                this.outputCount
        );
    }

    public static class Result extends BaseResult {
        public final int temperature;
        public final int cookingTime;
        public final NonNullList<Ingredient> input;
        public final Item output;
        public final int outputCount;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, int temperature, int cookingTime, NonNullList<Ingredient> input, Item output, int outputCount) {
            super(id, recipeSerializer);
            this.temperature = temperature;
            this.cookingTime = cookingTime;
            this.input = input;
            this.output = output;
            this.outputCount = outputCount;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addNumber(pJson, "temperature", this.temperature);
            this.addMaxCookingTime(pJson, this.cookingTime);
            this.addInput(pJson, this.input);
            this.addOutput(pJson, this.output, this.outputCount);
        }
    }
}
