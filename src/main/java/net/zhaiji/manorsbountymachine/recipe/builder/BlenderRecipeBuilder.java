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
import net.minecraftforge.registries.ForgeRegistries;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BlenderRecipeBuilder {
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;
    public final Item output;
    public final int outputCount;
    public final Item outgrowth;
    public final float outgrowthChance;

    public BlenderRecipeBuilder(NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
        this.outputCount = outputCount;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, pLocation.withPrefix("blender/").getPath());
        pRecipeConsumer.accept(new BlenderRecipeBuilder.Result(recipePath, this.mainInput, this.secondaryInput, this.output, this.outputCount, this.outgrowth, this.outgrowthChance));
    }

    public static class Result implements FinishedRecipe {
        public final ResourceLocation id;
        public final NonNullList<Ingredient> mainInput;
        public final NonNullList<Ingredient> secondaryInput;
        public final Item output;
        public final int outputCount;
        public final Item outgrowth;
        public final float outgrowthChance;

        public Result(ResourceLocation id, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
            this.id = id;
            this.mainInput = mainInput;
            this.secondaryInput = secondaryInput;
            this.output = output;
            this.outputCount = outputCount;
            this.outgrowth = outgrowth;
            this.outgrowthChance = outgrowthChance;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray mainInput = new JsonArray();
            for (Ingredient ingredient : this.mainInput) {
                mainInput.add(ingredient.toJson());
            }
            pJson.add("mainInput", mainInput);
            if (!this.secondaryInput.isEmpty()) {
                JsonArray secondaryInput = new JsonArray();
                for (Ingredient ingredient : this.secondaryInput) {
                    secondaryInput.add(ingredient.toJson());
                }
                pJson.add("secondaryInput", secondaryInput);
            }
            JsonObject output = new JsonObject();
            output.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output).toString());
            if (this.outputCount > 1) {
                output.addProperty("count", this.outputCount);
            }
            pJson.add("output", output);
            if (this.outgrowth != Items.AIR) {
                JsonObject outgrowth = new JsonObject();
                outgrowth.addProperty("item", ForgeRegistries.ITEMS.getKey(this.outgrowth).toString());
                pJson.add("outgrowth", outgrowth);
                pJson.addProperty("outgrowthChance", this.outgrowthChance);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return InitRecipe.BLENDER_RECIPE_SERIALIZER.get();
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
