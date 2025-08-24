package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class BrightFermentationRecipe extends BaseFermentationRecipe {
    public static final FermenterBlockEntity.LightState LIGHT_STATE = FermenterBlockEntity.LightState.BRIGHT;

    public BrightFermentationRecipe(ResourceLocation id, int cookingTime, NonNullList<Ingredient> input, ItemStack output) {
        super(id, LIGHT_STATE, cookingTime, input, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.BRIGHT_FERMENTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.BRIGHT_FERMENTATION_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<BrightFermentationRecipe> {
        @Override
        public BrightFermentationRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            int cookingTime = GsonHelper.getAsInt(pSerializedRecipe, "cookingTime");
            JsonArray inputsArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "input");
            NonNullList<Ingredient> input = NonNullList.withSize(FermenterBlockEntity.ITEMS_SIZE, Ingredient.EMPTY);
            for (int i = 0; i < inputsArray.size(); i++) {
                input.set(i, Ingredient.fromJson(inputsArray.get(i)));
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new BrightFermentationRecipe(pRecipeId, cookingTime, input, output);
        }

        @Override
        public @Nullable BrightFermentationRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int cookingTime = pBuffer.readInt();
            NonNullList<Ingredient> input = NonNullList.withSize(FermenterBlockEntity.ITEMS_SIZE, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            return new BrightFermentationRecipe(pRecipeId, cookingTime, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BrightFermentationRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.cookingTime);
            for (Ingredient ingredient : pRecipe.input) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}