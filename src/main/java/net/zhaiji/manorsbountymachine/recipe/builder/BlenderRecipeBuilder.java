package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class BlenderRecipeBuilder extends BaseRecipeBuilder {
    public final Ingredient container;
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;
    public final Item output;
    public final int outputCount;
    public final Item outgrowth;
    public final float outgrowthChance;

    public BlenderRecipeBuilder(NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output) {
        this(Ingredient.EMPTY, mainInput, secondaryInput, output, 1, Items.AIR, 0);
    }

    public BlenderRecipeBuilder(NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, Item outgrowth, float outgrowthChance) {
        this(Ingredient.EMPTY, mainInput, secondaryInput, output, 1, outgrowth, outgrowthChance);
    }

    public BlenderRecipeBuilder(Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output) {
        this(container, mainInput, secondaryInput, output, 1, Items.AIR, 0);
    }

    public BlenderRecipeBuilder(Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, Item outgrowth, float outgrowthChance) {
        this(container, mainInput, secondaryInput, output, 1, outgrowth, outgrowthChance);
    }

    public BlenderRecipeBuilder(NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, int outputCount) {
        this(Ingredient.EMPTY, mainInput, secondaryInput, output, outputCount, Items.AIR, 0);
    }

    public BlenderRecipeBuilder(NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        this(Ingredient.EMPTY, mainInput, secondaryInput, output, outputCount, outgrowth, outgrowthChance);
    }

    public BlenderRecipeBuilder(Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, int outputCount) {
        this(container, mainInput, secondaryInput, output, outputCount, Items.AIR, 0);
    }

    public BlenderRecipeBuilder(Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        this.container = container;
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
        this.outputCount = outputCount;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    @Override
    public String getRecipePath() {
        return "blender/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.BLENDER_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.container,
                this.mainInput,
                this.secondaryInput,
                this.output,
                this.outputCount,
                this.outgrowth,
                this.outgrowthChance
        );
    }

    public static class Result extends BaseResult {
        public final Ingredient container;
        public final NonNullList<Ingredient> mainInput;
        public final NonNullList<Ingredient> secondaryInput;
        public final Item output;
        public final int outputCount;
        public final Item outgrowth;
        public final float outgrowthChance;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
            super(id, recipeSerializer);
            this.container = container;
            this.mainInput = mainInput;
            this.secondaryInput = secondaryInput;
            this.output = output;
            this.outputCount = outputCount;
            this.outgrowth = outgrowth;
            this.outgrowthChance = outgrowthChance;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addContainer(pJson, this.container);
            this.addMainInput(pJson, this.mainInput);
            this.addSecondaryInput(pJson, this.secondaryInput);
            this.addOutput(pJson, this.output, this.outputCount);
            if (this.outgrowth != Items.AIR) {
                this.addOutgrowth(pJson, this.outgrowth);
                this.addOutgrowthChance(pJson, this.outgrowthChance);
            }
        }
    }
}
