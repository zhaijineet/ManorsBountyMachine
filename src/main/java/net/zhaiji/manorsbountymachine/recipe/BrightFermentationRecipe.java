package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

public class BrightFermentationRecipe extends BaseFermentationRecipe {
    public static final FermenterBlockEntity.LightState LIGHT_STATE = FermenterBlockEntity.LightState.BRIGHT;

    public BrightFermentationRecipe(ResourceLocation id, int cookingTime, Ingredient container, NonNullList<Ingredient> input, ItemStack output) {
        super(id, LIGHT_STATE, cookingTime, container, input, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.BRIGHT_FERMENTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.BRIGHT_FERMENTATION_RECIPE_TYPE.get();
    }

    public static class Serializer extends BaseFermentationRecipeSerializer<BrightFermentationRecipe> {
        @Override
        public BrightFermentationRecipe createRecipe(ResourceLocation id, int cookingTime, Ingredient container, NonNullList<Ingredient> input, ItemStack output) {
            return new BrightFermentationRecipe(id, cookingTime, container, input, output);
        }
    }
}