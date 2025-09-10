package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

import static net.zhaiji.manorsbountymachine.recipe.SaucepanAndWhiskRecipe.HeatState;


public class SaucepanAndWhiskRecipeBuilder extends BaseRecipeBuilder {
    public final HeatState heatState;
    public final Ingredient container;
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;
    public final Item output;

    public SaucepanAndWhiskRecipeBuilder(HeatState heatState, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output) {
        this.heatState = heatState;
        this.container = container;
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
    }

    @Override
    public String getRecipePath() {
        return "saucepan_and_whisk/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.heatState,
                this.container,
                this.mainInput,
                this.secondaryInput,
                this.output
        );
    }

    public static class Result extends BaseResult {
        public final HeatState heatState;
        public final Ingredient container;
        public final NonNullList<Ingredient> mainInput;
        public final NonNullList<Ingredient> secondaryInput;
        public final Item output;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, HeatState heatState, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output) {
            super(id, recipeSerializer);
            this.heatState = heatState;
            this.container = container;
            this.mainInput = mainInput;
            this.secondaryInput = secondaryInput;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addString(pJson, "heatState", this.heatState.toString().toLowerCase());
            this.addContainer(pJson, this.container);
            this.addMainInput(pJson, this.mainInput);
            this.addSecondaryInput(pJson, this.secondaryInput);
            this.addOutput(pJson, this.output);
        }
    }
}
