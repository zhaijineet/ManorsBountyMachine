package net.zhaiji.manorsbountymachine.recipe.builder;

import com.google.gson.JsonObject;
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

public class CuttingBoardSingleRecipeBuilder {
    public final Ingredient tool;
    public final Ingredient input;
    public final Item output;
    public final int outputCount;
    public final Item outgrowth;
    public final float outgrowthChance;

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output) {
        this(tool, input, output, 1, Items.AIR, 0);
    }

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output, Item outgrowth, float outgrowthChance) {
        this(tool, input, output, 1, outgrowth, outgrowthChance);
    }

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output, int outputCount) {
        this(tool, input, output, outputCount, Items.AIR, 0);
    }

    public CuttingBoardSingleRecipeBuilder(Ingredient tool, Ingredient input, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        this.tool = tool;
        this.input = input;
        this.output = output;
        this.outputCount = outputCount;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
        ResourceLocation recipePath = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, pLocation.withPrefix("cutting_board_single/").getPath());
        pRecipeConsumer.accept(new CuttingBoardSingleRecipeBuilder.Result(recipePath, this.tool, this.input, this.output, this.outputCount, this.outgrowth, this.outgrowthChance));
    }

    public static class Result implements FinishedRecipe {
        public final ResourceLocation id;
        public final Ingredient tool;
        public final Ingredient input;
        public final Item output;
        public final int outputCount;
        public final Item outgrowth;
        public final float outgrowthChance;

        public Result(ResourceLocation id, Ingredient tool, Ingredient input, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
            this.id = id;
            this.tool = tool;
            this.input = input;
            this.output = output;
            this.outputCount = outputCount;
            this.outgrowth = outgrowth;
            this.outgrowthChance = outgrowthChance;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("tool", this.tool.toJson());
            pJson.add("input", this.input.toJson());
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
            return InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_SERIALIZER.get();
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
