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
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class OvenRecipeBuilder {
    public final int temperature;
    public final int cookingTime;
    public final NonNullList<Ingredient> input;
    public final Item output;
    public final int count;

    public OvenRecipeBuilder(int temperature, int cookingTime, NonNullList<Ingredient> input, Item output, int count) {
        this.temperature = temperature;
        this.cookingTime = cookingTime;
        this.input = input;
        this.output = output;
        this.count = count;
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, pLocation.withPrefix("oven/").getPath());
        pRecipeConsumer.accept(new OvenRecipeBuilder.Result(recipePath, this.temperature, this.cookingTime, this.input, this.output, this.count));
    }

    public static class Result implements FinishedRecipe {
        public final ResourceLocation id;
        public final int temperature;
        public final int cookingTime;
        public final NonNullList<Ingredient> input;
        public final Item output;
        public final int count;

        public Result(ResourceLocation id, int temperature, int cookingTime, NonNullList<Ingredient> input, Item output, int count) {
            this.id = id;
            this.temperature = temperature;
            this.cookingTime = cookingTime;
            this.input = input;
            this.output = output;
            this.count = count;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("temperature", this.temperature);
            pJson.addProperty("cookingTime", this.cookingTime);
            JsonArray input = new JsonArray();
            for (Ingredient ingredient : this.input) {
                input.add(ingredient.toJson());
            }
            pJson.add("input", input);
            JsonObject output = new JsonObject();
            output.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
            if (this.count > 1) {
                output.addProperty("count", this.count);
            }
            pJson.add("output", output);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return InitRecipe.OVEN_RECIPE_SERIALIZER.get();
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
