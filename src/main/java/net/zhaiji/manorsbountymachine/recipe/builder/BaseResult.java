package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public abstract class BaseResult implements FinishedRecipe {
    public final ResourceLocation id;
    public final RecipeSerializer<?> recipeSerializer;

    public BaseResult(ResourceLocation id, RecipeSerializer<?> recipeSerializer) {
        this.id = id;
        this.recipeSerializer = recipeSerializer;
    }

    public void addNumber(JsonObject json, String name, Number value) {
        json.addProperty(name, value);
    }

    public void addBoolean(JsonObject json, String name, boolean value) {
        json.addProperty(name, value);
    }

    public void addString(JsonObject json, String name, String value) {
        json.addProperty(name, value);
    }

    public void addIngredient(JsonObject json, String name, Ingredient ingredient) {
        json.add(name, ingredient.toJson());
    }

    public void addNullableIngredient(JsonObject json, String name, Ingredient ingredient) {
        if (!ingredient.isEmpty()) {
            this.addIngredient(json, name, ingredient);
        }
    }

    public void addIngredients(JsonObject json, String name, NonNullList<Ingredient> ingredients) {
        JsonArray array = new JsonArray();
        for (Ingredient ingredient : ingredients) {
            array.add(ingredient.toJson());
        }
        json.add(name, array);
    }

    public void addNullableIngredients(JsonObject json, String name, NonNullList<Ingredient> ingredients) {
        if (!ingredients.isEmpty()) {
            this.addIngredients(json, name, ingredients);
        }
    }

    public void addItem(JsonObject json, String name, Item item, int size) {
        JsonObject object = new JsonObject();
        object.addProperty("item", ForgeRegistries.ITEMS.getKey(item).toString());
        if (size > 1) {
            object.addProperty("count", size);
        }
        json.add(name, object);
    }

    public void addItem(JsonObject json, String name, Item item) {
        this.addItem(json, name, item, 1);
    }

    public void addNullableItem(JsonObject json, String name, Item item) {
        if (item != Items.AIR) {
            this.addItem(json, name, item);
        }
    }

    public void addFluidStack(JsonObject json, FluidStack fluidStack) {
        JsonObject fluid = new JsonObject();
        fluid.addProperty("FluidName", ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid()).toString());
        fluid.addProperty("Amount", fluidStack.getAmount());
        json.add("fluid", fluid);
    }

    public void addMaxCookingTime(JsonObject json, int maxCookingTime) {
        this.addNumber(json, "cookingTime", maxCookingTime);
    }

    public void addContainer(JsonObject json, Ingredient container) {
        this.addNullableIngredient(json, "container", container);
    }

    public void addInput(JsonObject json, Ingredient input) {
        this.addIngredient(json, "input", input);
    }

    public void addInput(JsonObject json, NonNullList<Ingredient> input) {
        this.addIngredients(json, "input", input);
    }

    public void addMainInput(JsonObject json, NonNullList<Ingredient> mainInput) {
        this.addNullableIngredients(json, "mainInput", mainInput);
    }

    public void addSecondaryInput(JsonObject json, NonNullList<Ingredient> secondaryInput) {
        this.addNullableIngredients(json, "secondaryInput", secondaryInput);
    }

    public void addOutput(JsonObject json, Item output, int size) {
        this.addItem(json, "output", output, size);
    }

    public void addOutput(JsonObject json, Item output) {
        this.addItem(json, "output", output);
    }

    public void addOutgrowth(JsonObject json, Item outgrowth) {
        this.addItem(json, "outgrowth", outgrowth);
    }

    public void addOutgrowthChance(JsonObject json, float value) {
        this.addNumber(json, "outgrowthChance", value);
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
