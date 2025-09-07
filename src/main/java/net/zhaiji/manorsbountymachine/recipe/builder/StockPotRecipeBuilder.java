package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class StockPotRecipeBuilder extends BaseRecipeBuilder {
    public final int maxCookingTime;
    public final Ingredient container;
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;
    public final Item output;

    public StockPotRecipeBuilder(int maxCookingTime, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output) {
        this.maxCookingTime = maxCookingTime;
        this.container = container;
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
    }

    @Override
    public String getRecipePath() {
        return "stock_pot/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.STOCK_POT_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.maxCookingTime,
                this.container,
                this.mainInput,
                this.secondaryInput,
                this.output
        );
    }

    public static class Result extends BaseResult {
        public final int maxCookingTime;
        public final Ingredient container;
        public final NonNullList<Ingredient> mainInput;
        public final NonNullList<Ingredient> secondaryInput;
        public final Item output;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, int maxCookingTime, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output) {
            super(id, recipeSerializer);
            this.maxCookingTime = maxCookingTime;
            this.container = container;
            this.mainInput = mainInput;
            this.secondaryInput = secondaryInput;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addMaxCookingTime(pJson, this.maxCookingTime);
            this.addContainer(pJson, this.container);
            this.addMainInput(pJson, this.mainInput);
            this.addSecondaryInput(pJson, this.secondaryInput);
            this.addOutput(pJson, this.output);
        }
    }
}
