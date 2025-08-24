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

public class BrightFermentationRecipeBuilder extends BaseFermentationRecipeBuilder{
    public BrightFermentationRecipeBuilder(int cookingTime, NonNullList<Ingredient> input, Item output) {
        super(cookingTime, input, output);
    }

    @Override
    public RecipeSerializer<?> getRecipeSerializer() {
        return InitRecipe.BRIGHT_FERMENTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public String getRecipeLocation() {
        return "bright_fermentation/";
    }
}
