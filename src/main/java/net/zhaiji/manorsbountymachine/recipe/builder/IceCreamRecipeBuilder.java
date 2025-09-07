package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class IceCreamRecipeBuilder extends BaseRecipeBuilder {
    public final FluidStack fluidStack;
    public final Ingredient container;
    public final NonNullList<Ingredient> input;
    public final Item output;

    public IceCreamRecipeBuilder(FluidStack fluidStack, NonNullList<Ingredient> input, Item output) {
        this(fluidStack, Ingredient.EMPTY, input, output);
    }

    public IceCreamRecipeBuilder(FluidStack fluidStack, Ingredient container, NonNullList<Ingredient> input, Item output) {
        this.fluidStack = fluidStack;
        this.container = container;
        this.input = input;
        this.output = output;
    }

    @Override
    public String getRecipePath() {
        return "ice_cream/";
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.ICE_CREAM_RECIPE_SERIALIZER.get();
    }

    @Override
    public FinishedRecipe createResult(ResourceLocation path, RecipeSerializer<?> recipeSerializer) {
        return new Result(
                path,
                recipeSerializer,
                this.fluidStack,
                this.container,
                this.input,
                this.output
        );
    }

    public static class Result extends BaseResult {
        public final FluidStack fluidStack;
        public final Ingredient container;
        public final NonNullList<Ingredient> input;
        public final Item output;

        public Result(ResourceLocation id, RecipeSerializer<?> recipeSerializer, FluidStack fluidStack, Ingredient container, NonNullList<Ingredient> input, Item output) {
            super(id, recipeSerializer);
            this.fluidStack = fluidStack;
            this.container = container;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.addFluidStack(pJson, this.fluidStack);
            this.addContainer(pJson, this.container);
            this.addInput(pJson, this.input);
            this.addOutput(pJson, this.output);
        }
    }
}
