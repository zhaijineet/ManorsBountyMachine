package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
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

    public static ResourceLocation getItemRegisterKey(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    public static void iceCreamRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, FluidStack fluidStack, Item output, Item... input) {
        Ingredient[] ingredients = new Ingredient[input.length + 1];
//        ingredients[0] = Ingredient.of(InitItem.ICE_CREAM_CONE.get());
        ingredients[0] = Ingredient.of(Items.APPLE);
        for (int i = 0; i < input.length; i++) {
            ingredients[i + 1] = Ingredient.of(input[i]);
        }
        new IceCreamRecipeBuilder(fluidStack, NonNullList.of(Ingredient.EMPTY, ingredients), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void fastFryRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item input, Item output) {
        new FastFryRecipeBuilder(Ingredient.of(input), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void slowFryRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item input, Item output) {
        new SlowFryRecipeBuilder(Ingredient.of(input), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void ovenRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, OvenBlockEntity.Temperature temperature, OvenBlockEntity.MaxCookingTime maxCookingTime, Item output, int count, Item... input) {
        new OvenRecipeBuilder(temperature.temperature, maxCookingTime.cookingTime, toIngredientList(input), output, count).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void teapotRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item output, Item... input) {
        new TeapotRecipeBuilder(toIngredientList(input), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void dimFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item output, Item... input) {
        new DimFermentationRecipeBuilder(cookingTime, toIngredientList(input), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void normalFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item output, Item... input) {
        new NormalFermentationRecipeBuilder(cookingTime, toIngredientList(input), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void brightFermentationRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, int cookingTime, Item output, Item... input) {
        new BrightFermentationRecipeBuilder(cookingTime, toIngredientList(input), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void blenderRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item output, int outputCount, Item outgrowth, float outgrowthChance, Item[] mainInput, Item[] secondaryInput) {
        new BlenderRecipeBuilder(toIngredientList(mainInput), toIngredientList(secondaryInput), output, outputCount, outgrowth, outgrowthChance).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    public static void shakerRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item output, Item... input) {
        new ShakerRecipeBuilder(toIngredientList(input), output).save(pFinishedRecipeConsumer, getItemRegisterKey(output));
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.DIAMOND);
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.STICK, Items.APPLE);
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.STONE, Items.APPLE, Items.APPLE);
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.IRON_INGOT, Items.AIR, Items.APPLE);
        iceCreamRecipe(pWriter, new FluidStack(Fluids.WATER, 250), Items.GOLD_INGOT, Items.MILK_BUCKET, Items.APPLE);

        fastFryRecipe(pWriter, Items.APPLE, Items.STICK);
        slowFryRecipe(pWriter, Items.APPLE, Items.DIAMOND);
        fastFryRecipe(pWriter, Items.MILK_BUCKET, Items.IRON_INGOT);
        slowFryRecipe(pWriter, Items.MILK_BUCKET, Items.GOLD_INGOT);

        ovenRecipe(pWriter, OvenBlockEntity.Temperature.ONE_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.FIVE, Items.STICK, 4, Items.APPLE);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.ONE_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.TEN, Items.STONE, 4, Items.APPLE, Items.APPLE);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.TWO_HUNDRED, OvenBlockEntity.MaxCookingTime.FIFTEEN, Items.DIAMOND, 4, Items.APPLE, Items.APPLE, Items.APPLE);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.TWO_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.FIFTEEN, Items.IRON_INGOT, 4, Items.APPLE, Items.APPLE, Items.APPLE, Items.APPLE);
        ovenRecipe(pWriter, OvenBlockEntity.Temperature.TWO_HUNDRED_FIFTY, OvenBlockEntity.MaxCookingTime.FIFTEEN, Items.GOLD_INGOT, 4, Items.MILK_BUCKET, Items.MILK_BUCKET, Items.APPLE, Items.APPLE);

        teapotRecipe(pWriter, Items.APPLE, Items.IRON_INGOT, Items.MILK_BUCKET, Items.DIAMOND);
        teapotRecipe(pWriter, Items.STICK, Items.GOLD_INGOT, Items.MILK_BUCKET, Items.DIAMOND);

        dimFermentationRecipe(pWriter, 1200, Items.APPLE, Items.AIR, Items.IRON_INGOT);
        normalFermentationRecipe(pWriter, 1200, Items.STONE, Items.AIR, Items.GOLD_INGOT);
        brightFermentationRecipe(pWriter, 1200, Items.DIAMOND, Items.IRON_INGOT, Items.GOLD_INGOT);

        blenderRecipe(pWriter, Items.APPLE, 4, Items.AIR, 0, new Item[]{Items.IRON_INGOT}, new Item[]{});

        shakerRecipe(pWriter, Items.APPLE, Items.IRON_INGOT, Items.GOLD_INGOT);
        shakerRecipe(pWriter, Items.DIAMOND, Items.IRON_INGOT, Items.MILK_BUCKET);
    }
}
