package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.fluids.FluidStack;

public abstract class BaseRecipeSerializer<T extends BaseRecipe<?>> implements RecipeSerializer<T> {
    public int getInt(JsonObject serializedRecipe, String name) {
        return GsonHelper.getAsInt(serializedRecipe, name);
    }

    public int getInt(FriendlyByteBuf buffer) {
        return buffer.readInt();
    }

    public float getFloat(JsonObject serializedRecipe, String name) {
        return GsonHelper.getAsFloat(serializedRecipe, name);
    }

    public float getFloat(FriendlyByteBuf buffer) {
        return buffer.readFloat();
    }

    public boolean getBoolean(JsonObject serializedRecipe, String name) {
        return GsonHelper.getAsBoolean(serializedRecipe, name);
    }

    public boolean getBoolean(FriendlyByteBuf buffer) {
        return buffer.readBoolean();
    }

    public Ingredient getIngredient(JsonObject serializedRecipe, String name) {
        return Ingredient.fromJson(GsonHelper.getAsJsonObject(serializedRecipe, name));
    }

    public Ingredient getIngredient(FriendlyByteBuf buffer) {
        return Ingredient.fromNetwork(buffer);
    }

    public Ingredient getNullableIngredient(JsonObject serializedRecipe, String name) {
        return serializedRecipe.has(name)
                ? this.getIngredient(serializedRecipe, name)
                : Ingredient.EMPTY;
    }

    public NonNullList<Ingredient> getIngredients(JsonObject serializedRecipe, String name, int size) {
        JsonArray ingredientsArray = GsonHelper.getAsJsonArray(serializedRecipe, name);
        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
        for (int i = 0; i < ingredientsArray.size(); i++) {
            ingredients.set(i, Ingredient.fromJson(ingredientsArray.get(i)));
        }
        return ingredients;
    }

    public NonNullList<Ingredient> getNullableIngredients(JsonObject serializedRecipe, String name, int size) {
        return serializedRecipe.has(name)
                ? this.getIngredients(serializedRecipe, name, size)
                : NonNullList.withSize(size, Ingredient.EMPTY);
    }

    public NonNullList<Ingredient> getIngredients(FriendlyByteBuf buffer, int size) {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
        ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
        return ingredients;
    }

    public ItemStack getItem(JsonObject serializedRecipe, String name) {
        return ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, name));
    }

    public ItemStack getItem(FriendlyByteBuf buffer) {
        return buffer.readItem();
    }

    public ItemStack getNullableItem(JsonObject serializedRecipe, String name) {
        return serializedRecipe.has(name)
                ? this.getItem(serializedRecipe, name)
                : ItemStack.EMPTY;
    }

    public FluidStack getFluidStack(JsonObject serializedRecipe) {
        return FluidStack.CODEC.parse(
                JsonOps.INSTANCE,
                GsonHelper.getAsJsonObject(serializedRecipe, "fluid")
        ).get().left().orElse(FluidStack.EMPTY);
    }

    public FluidStack getFluidStack(FriendlyByteBuf buffer) {
        return buffer.readFluidStack();
    }

    public int getCookingTime(JsonObject serializedRecipe) {
        return this.getInt(serializedRecipe, "cookingTime");
    }

    public int getCookingTime(FriendlyByteBuf buffer) {
        return this.getInt(buffer);
    }

    public Ingredient getContainer(JsonObject serializedRecipe) {
        return this.getNullableIngredient(serializedRecipe, "container");
    }

    public Ingredient getContainer(FriendlyByteBuf buffer) {
        return this.getIngredient(buffer);
    }

    public Ingredient getInput(JsonObject serializedRecipe) {
        return this.getIngredient(serializedRecipe, "input");
    }

    public Ingredient getInput(FriendlyByteBuf buffer) {
        return this.getIngredient(buffer);
    }

    public NonNullList<Ingredient> getInput(JsonObject serializedRecipe, int size) {
        return this.getIngredients(serializedRecipe, "input", size);
    }

    public NonNullList<Ingredient> getInput(FriendlyByteBuf buffer, int size) {
        return this.getIngredients(buffer, size);
    }

    public NonNullList<Ingredient> getMainInput(JsonObject serializedRecipe, int size) {
        return this.getNullableIngredients(serializedRecipe, "mainInput", size);
    }

    public NonNullList<Ingredient> getMainInput(FriendlyByteBuf buffer, int size) {
        return this.getIngredients(buffer, size);
    }

    public NonNullList<Ingredient> getSecondaryInput(JsonObject serializedRecipe, int size) {
        return this.getNullableIngredients(serializedRecipe, "secondaryInput", size);
    }

    public NonNullList<Ingredient> getSecondaryInput(FriendlyByteBuf buffer, int size) {
        return this.getIngredients(buffer, size);
    }

    public ItemStack getOutput(JsonObject serializedRecipe) {
        return this.getItem(serializedRecipe, "output");
    }

    public ItemStack getOutput(FriendlyByteBuf buffer) {
        return this.getItem(buffer);
    }

    public ItemStack getOutgrowth(JsonObject serializedRecipe) {
        return this.getNullableItem(serializedRecipe, "outgrowth");
    }

    public ItemStack getOutgrowth(FriendlyByteBuf buffer) {
        return this.getItem(buffer);
    }

    public float getOutgrowthChance(JsonObject serializedRecipe) {
        return this.getOutgrowth(serializedRecipe).isEmpty()
                ? 0
                : this.getFloat(serializedRecipe, "outgrowthChance");
    }

    public float getOutgrowthChance(FriendlyByteBuf buffer) {
        return this.getFloat(buffer);
    }

    public void toInt(FriendlyByteBuf buffer, int value) {
        buffer.writeInt(value);
    }

    public void toFloat(FriendlyByteBuf buffer, float value) {
        buffer.writeFloat(value);
    }

    public void toBoolean(FriendlyByteBuf buffer, boolean value) {
        buffer.writeBoolean(value);
    }

    public void toIngredient(FriendlyByteBuf buffer, Ingredient ingredient) {
        ingredient.toNetwork(buffer);
    }

    public void toIngredients(FriendlyByteBuf buffer, NonNullList<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            ingredient.toNetwork(buffer);
        }
    }

    public void toItem(FriendlyByteBuf buffer, ItemStack itemStack) {
        buffer.writeItem(itemStack);
    }

    public void toFluidStack(FriendlyByteBuf buffer, FluidStack fluidStack) {
        buffer.writeFluidStack(fluidStack);
    }

    public void toCookingTime(FriendlyByteBuf buffer, int value) {
        this.toInt(buffer, value);
    }

    public void toContainer(FriendlyByteBuf buffer, Ingredient container) {
        this.toIngredient(buffer, container);
    }

    public void toInput(FriendlyByteBuf buffer, Ingredient input) {
        this.toIngredient(buffer, input);
    }

    public void toInput(FriendlyByteBuf buffer, NonNullList<Ingredient> input) {
        this.toIngredients(buffer, input);
    }

    public void toMainInput(FriendlyByteBuf buffer, NonNullList<Ingredient> mainInput) {
        this.toIngredients(buffer, mainInput);
    }

    public void toSecondaryInput(FriendlyByteBuf buffer, NonNullList<Ingredient> secondaryInput) {
        this.toIngredients(buffer, secondaryInput);
    }

    public void toOutput(FriendlyByteBuf buffer, ItemStack output) {
        this.toItem(buffer, output);
    }

    public void toOutgrowth(FriendlyByteBuf buffer, ItemStack outgrowth) {
        this.toItem(buffer, outgrowth);
    }

    public void toOutgrowthChance(FriendlyByteBuf buffer, float outgrowthChance) {
        this.toFloat(buffer, outgrowthChance);
    }
}
