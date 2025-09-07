package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class TeapotRecipeBuilder extends BaseRecipeBuilder {
    public final NonNullList<Ingredient> input;
    public final Item output;

    public TeapotRecipeBuilder(NonNullList<Ingredient> input, Item output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String getRecipePath() {
        return "teapot/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.TEAPOT_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.input,
                this.output
        );
    }

    public static class Result extends BaseResult {
        public final NonNullList<Ingredient> input;
        public final Item output;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, NonNullList<Ingredient> input, Item output) {
            super(id, recipeSerializer);
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addInput(pJson, this.input);
            this.addOutput(pJson, this.output);
        }
    }
}
