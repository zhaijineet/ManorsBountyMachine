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

public class CuttingBoardMultipleRecipeBuilder {
    public final boolean isShaped;
    public final NonNullList<Ingredient> input;
    public final Item output;

    public CuttingBoardMultipleRecipeBuilder(boolean isShaped, NonNullList<Ingredient> input, Item output) {
        this.isShaped = isShaped;
        this.input = input;
        this.output = output;
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, pLocation.withPrefix("cutting_board_multiple/").getPath());
        pRecipeConsumer.accept(new CuttingBoardMultipleRecipeBuilder.Result(recipePath, this.isShaped, this.input, this.output));
    }

    public static class Result implements FinishedRecipe {
        public final ResourceLocation id;
        public final boolean isShaped;
        public final NonNullList<Ingredient> input;
        public final Item output;

        public Result(ResourceLocation id, boolean isShaped, NonNullList<Ingredient> input, Item output) {
            this.id = id;
            this.isShaped = isShaped;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("isShaped", this.isShaped);
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
            return InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_SERIALIZER.get();
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
