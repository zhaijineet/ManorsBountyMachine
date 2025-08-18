package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class IceCreamRecipeBuilder {
    public final FluidStack fluidStack;
    public final NonNullList<Ingredient> input;
    public final Item output;

    public IceCreamRecipeBuilder(FluidStack fluidStack, NonNullList<Ingredient> input, Item output) {
        this.fluidStack = fluidStack;
        this.input = input;
        this.output = output;
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, pLocation.withPrefix("ice_cream/").getPath());
        pRecipeConsumer.accept(new IceCreamRecipeBuilder.Result(recipePath, this.fluidStack, this.input, this.output));
    }

    public static class Result implements FinishedRecipe {
        public final ResourceLocation id;
        public final FluidStack fluidStack;
        public final NonNullList<Ingredient> input;
        public final Item output;

        public Result(ResourceLocation id, FluidStack fluidStack, NonNullList<Ingredient> input, Item output) {
            this.id = id;
            this.fluidStack = fluidStack;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonObject fluid = new JsonObject();
            fluid.addProperty("FluidName", ForgeRegistries.FLUIDS.getKey(this.fluidStack.getFluid()).toString());
            fluid.addProperty("Amount", this.fluidStack.getAmount());
            pJson.add("fluid", fluid);
            JsonArray input = new JsonArray();
            for (Ingredient ingredient : this.input) {
                input.add(ingredient.toJson());
            }
            pJson.add("input", input);
            JsonObject outputItem = new JsonObject();
            outputItem.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
            pJson.add("output", outputItem);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return InitRecipe.ICE_CREAM_RECIPE_SERIALIZER.get();
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
