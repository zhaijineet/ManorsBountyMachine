package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.recipe.builder.*;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {
    public RecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    public static NonNullList<Ingredient> toIngredientList(Item[] input) {
        Ingredient[] ingredients = new Ingredient[input.length];
        for (int i = 0; i < input.length; i++) {
            ingredients[i] = Ingredient.of(input[i]);
        }
        return NonNullList.of(Ingredient.EMPTY, ingredients);
    }

    public static void iceCreamRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, FluidStack fluidStack, Item container, Item output, Item[] input) {
        new IceCreamRecipeBuilder(fluidStack, Ingredient.of(container), toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void iceCreamRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, FluidStack fluidStack, Item output, Item[] input) {
        new IceCreamRecipeBuilder(fluidStack, toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void fastFryRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item input, Item output) {
        new FastFryRecipeBuilder(Ingredient.of(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void slowFryRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item input, Item output) {
        new SlowFryRecipeBuilder(Ingredient.of(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void ovenRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, OvenBlockEntity.Temperature temperature, OvenBlockEntity.MaxCookingTime maxCookingTime, Item[] input, Item output, int count) {
        new OvenRecipeBuilder(temperature.temperature, maxCookingTime.cookingTime, toIngredientList(input), output, count).save(pFinishedRecipeConsumer, output);
    }

    public static void teapotRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item[] input, Item output) {
        new TeapotRecipeBuilder(toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void dimFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item[] input, Item output) {
        new DimFermentationRecipeBuilder(cookingTime, toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void dimFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item container, Item[] input, Item output) {
        new DimFermentationRecipeBuilder(cookingTime, Ingredient.of(container), toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void normalFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item[] input, Item output) {
        new NormalFermentationRecipeBuilder(cookingTime, toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void normalFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item container, Item[] input, Item output) {
        new NormalFermentationRecipeBuilder(cookingTime, Ingredient.of(container), toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void brightFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item[] input, Item output) {
        new BrightFermentationRecipeBuilder(cookingTime, toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void brightFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item container, Item[] input, Item output) {
        new BrightFermentationRecipeBuilder(cookingTime, Ingredient.of(container), toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item[] mainInput, Item[] secondaryInput, Item output) {
        new BlenderRecipeBuilder(toIngredientList(mainInput), toIngredientList(secondaryInput), output).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item[] mainInput, Item[] secondaryInput, Item output, Item outgrowth, float outgrowthChance) {
        new BlenderRecipeBuilder(toIngredientList(mainInput), toIngredientList(secondaryInput), output, outgrowth, outgrowthChance).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item container, Item[] mainInput, Item[] secondaryInput, Item output) {
        new BlenderRecipeBuilder(Ingredient.of(container), toIngredientList(mainInput), toIngredientList(secondaryInput), output).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item container, Item[] mainInput, Item[] secondaryInput, Item output, Item outgrowth, float outgrowthChance) {
        new BlenderRecipeBuilder(Ingredient.of(container), toIngredientList(mainInput), toIngredientList(secondaryInput), output, outgrowth, outgrowthChance).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item[] mainInput, Item[] secondaryInput, Item output, int outputCount) {
        new BlenderRecipeBuilder(toIngredientList(mainInput), toIngredientList(secondaryInput), output, outputCount).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item[] mainInput, Item[] secondaryInput, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        new BlenderRecipeBuilder(toIngredientList(mainInput), toIngredientList(secondaryInput), output, outputCount, outgrowth, outgrowthChance).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item container, Item[] mainInput, Item[] secondaryInput, Item output, int outputCount) {
        new BlenderRecipeBuilder(Ingredient.of(container), toIngredientList(mainInput), toIngredientList(secondaryInput), output, outputCount).save(pFinishedRecipeConsumer, output);
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item container, Item[] mainInput, Item[] secondaryInput, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        new BlenderRecipeBuilder(Ingredient.of(container), toIngredientList(mainInput), toIngredientList(secondaryInput), output, outputCount, outgrowth, outgrowthChance).save(pFinishedRecipeConsumer, output);
    }

    public static void cuttingBoardSingleRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tool, Item input, Item output) {
        new CuttingBoardSingleRecipeBuilder(Ingredient.of(tool), Ingredient.of(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void cuttingBoardSingleRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tool, Item input, Item output, Item outgrowth, float outgrowthChance) {
        new CuttingBoardSingleRecipeBuilder(Ingredient.of(tool), Ingredient.of(input), output, outgrowth, outgrowthChance).save(pFinishedRecipeConsumer, output);
    }

    public static void cuttingBoardSingleRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tool, Item input, Item output, int outputCount) {
        new CuttingBoardSingleRecipeBuilder(Ingredient.of(tool), Ingredient.of(input), output, outputCount).save(pFinishedRecipeConsumer, output);
    }

    public static void cuttingBoardSingleRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tool, Item input, Item output, int outputCount, Item outgrowth, float outgrowthChance) {
        new CuttingBoardSingleRecipeBuilder(Ingredient.of(tool), Ingredient.of(input), output, outputCount, outgrowth, outgrowthChance).save(pFinishedRecipeConsumer, output);
    }

    public static void cuttingBoardMultipleRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, boolean isShaped, Item[] input, Item output) {
        new CuttingBoardMultipleRecipeBuilder(isShaped, toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    public static void stockPotRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item container, Item[] mainInput, Item[] secondaryInput, Item output) {
        new StockPotRecipeBuilder(cookingTime, Ingredient.of(container), toIngredientList(mainInput), toIngredientList(secondaryInput), output).save(pFinishedRecipeConsumer, output);
    }

    public static void shakerRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item[] input, Item output) {
        new ShakerRecipeBuilder(toIngredientList(input), output).save(pFinishedRecipeConsumer, output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.APPLE, Items.DIAMOND, new Item[]{});
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.APPLE, Items.STICK, new Item[]{Items.APPLE});
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.APPLE, Items.STONE, new Item[]{Items.APPLE, Items.APPLE});
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.APPLE, Items.IRON_INGOT, new Item[]{Items.AIR, Items.APPLE});
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.APPLE, Items.GOLD_INGOT, new Item[]{Items.MILK_BUCKET, Items.APPLE});

        fastFryRecipe(pWriter, Items.APPLE, Items.STICK);
        slowFryRecipe(pWriter, Items.APPLE, Items.DIAMOND);
        fastFryRecipe(pWriter, Items.MILK_BUCKET, Items.IRON_INGOT);
        slowFryRecipe(pWriter, Items.MILK_BUCKET, Items.GOLD_INGOT);

        ovenRecipe(pWriter, OvenBlockEntity.Temperature.ONE_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.FIVE, new Item[]{Items.APPLE}, Items.STICK, 4);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.ONE_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.TEN, new Item[]{Items.APPLE, Items.APPLE}, Items.STONE, 4);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.TWO_HUNDRED, OvenBlockEntity.MaxCookingTime.FIFTEEN, new Item[]{Items.APPLE, Items.APPLE, Items.APPLE}, Items.DIAMOND, 4);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.TWO_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.FIFTEEN, new Item[]{Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE}, Items.IRON_INGOT, 4);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.TWO_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.FIFTEEN, new Item[]{Items.MILK_BUCKET, Items.MILK_BUCKET, Items.APPLE, Items.APPLE}, Items.GOLD_INGOT, 4);

        teapotRecipe(pWriter, new Item[]{Items.IRON_INGOT, Items.MILK_BUCKET, Items.DIAMOND}, Items.APPLE);
        teapotRecipe(pWriter, new Item[]{Items.GOLD_INGOT, Items.MILK_BUCKET, Items.DIAMOND}, Items.STICK);

        dimFermentationRecipe(pWriter, 1200, new Item[]{Items.IRON_INGOT}, Items.APPLE);
        normalFermentationRecipe(pWriter, 1200, new Item[]{Items.GOLD_INGOT}, Items.STONE);
        brightFermentationRecipe(pWriter, 1200, Items.IRON_INGOT, new Item[]{Items.GOLD_INGOT}, Items.DIAMOND);

        blenderRecipe(pWriter, new Item[]{Items.IRON_INGOT}, new Item[]{}, Items.APPLE, 4);
        blenderRecipe(pWriter, Items.IRON_INGOT, new Item[]{Items.IRON_INGOT}, new Item[]{}, Items.STONE, 4);

        cuttingBoardSingleRecipe(pWriter, ItemTags.SWORDS, Items.APPLE, Items.DIAMOND);

        cuttingBoardMultipleRecipe(pWriter, true, new Item[]{Items.APPLE, Items.DIAMOND, Items.APPLE}, Items.IRON_INGOT);
        cuttingBoardMultipleRecipe(pWriter, false, new Item[]{Items.APPLE, Items.DIAMOND, Items.STICK}, Items.GOLD_INGOT);

        stockPotRecipe(pWriter, 200, Items.APPLE, new Item[]{Items.IRON_INGOT}, new Item[]{Items.GOLD_INGOT}, Items.DIAMOND);

        shakerRecipe(pWriter, new Item[]{Items.IRON_INGOT, Items.GOLD_INGOT}, Items.APPLE);
        shakerRecipe(pWriter, new Item[]{Items.IRON_INGOT, Items.MILK_BUCKET}, Items.DIAMOND);
    }
}
