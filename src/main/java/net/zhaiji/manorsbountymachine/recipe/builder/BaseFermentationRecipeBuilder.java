package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class BaseFermentationRecipeBuilder {
    public final int cookingTime;
    public final Ingredient container;
    public final NonNullList<Ingredient> input;
    public final Item output;

    public BaseFermentationRecipeBuilder(int cookingTime, NonNullList<Ingredient> input, Item output) {
        this(cookingTime, Ingredient.EMPTY, input, output);
    }

    public BaseFermentationRecipeBuilder(int cookingTime, Ingredient container, NonNullList<Ingredient> input, Item output) {
        this.cookingTime = cookingTime;
        this.container = container;
        this.input = input;
        this.output = output;
    }

    public abstract RecipeSerializer<?> getRecipeSerializer();

    public abstract String getRecipeLocation();

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, pLocation.withPrefix(this.getRecipeLocation()).getPath());
        pRecipeConsumer.accept(new BaseFermentationRecipeBuilder.Result(recipePath, this.getRecipeSerializer(), this.cookingTime, this.container, this.input, this.output));
    }

    public static class Result implements FinishedRecipe {
        public final ResourceLocation id;
        public final RecipeSerializer<?> recipeSerializer;
        public final int cookingTime;
        public final Ingredient container;
        public final NonNullList<Ingredient> input;
        public final Item output;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, int cookingTime, Ingredient container, NonNullList<Ingredient> input, Item output) {
            this.id = id;
            this.recipeSerializer = recipeSerializer;
            this.cookingTime = cookingTime;
            this.container = container;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("cookingTime", this.cookingTime);
            if (!this.container.isEmpty()) {
                pJson.add("container", this.container.toJson());
            }
            JsonArray input = new JsonArray();
            for (Ingredient ingredient : this.input) {
                input.add(ingredient.toJson());
            }
            pJson.add("input", input);
            JsonObject output = new JsonObject();
            output.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
            pJson.add("output", output);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.recipeSerializer;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
