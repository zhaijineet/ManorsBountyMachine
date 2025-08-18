package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.recipe.builder.FastFryRecipeBuilder;
import net.zhaiji.manorsbountymachine.recipe.builder.IceCreamRecipeBuilder;
import net.zhaiji.manorsbountymachine.recipe.builder.OvenRecipeBuilder;
import net.zhaiji.manorsbountymachine.recipe.builder.SlowFryRecipeBuilder;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {
    public RecipeProvider(PackOutput pOutput) {
        super(pOutput);
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
    }

    public static void iceCreamRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, FluidStack fluidStack, Item output, Item... input) {
        Ingredient[] ingredients = new Ingredient[input.length + 1];
//        ingredients[0] = Ingredient.of(InitItem.ICE_CREAM_CONE.get());
        ingredients[0] = Ingredient.of(Items.APPLE);
        for (int i = 0; i < input.length; i++) {
            ingredients[i + 1] = Ingredient.of(input[i]);
        }
        new IceCreamRecipeBuilder(fluidStack, NonNullList.of(Ingredient.EMPTY, ingredients), output).save(pFinishedRecipeConsumer, ForgeRegistries.ITEMS.getKey(output));
    }

    public static void fastFryRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item input, Item output) {
        new FastFryRecipeBuilder(Ingredient.of(input), output).save(pFinishedRecipeConsumer, ForgeRegistries.ITEMS.getKey(output));
    }

    public static void slowFryRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item input, Item output) {
        new SlowFryRecipeBuilder(Ingredient.of(input), output).save(pFinishedRecipeConsumer, ForgeRegistries.ITEMS.getKey(output));
    }

    public static void ovenRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, OvenBlockEntity.Temperature temperature, OvenBlockEntity.MaxCookingTime maxCookingTime, Item output, int count, Item... input) {
        Ingredient[] ingredients = new Ingredient[input.length];
        for (int i = 0; i < input.length; i++) {
            ingredients[i] = Ingredient.of(input[i]);
        }
        new OvenRecipeBuilder(temperature.temperature, maxCookingTime.cookingTime, NonNullList.of(Ingredient.EMPTY, ingredients), output, count).save(pFinishedRecipeConsumer, ForgeRegistries.ITEMS.getKey(output));
    }
}
