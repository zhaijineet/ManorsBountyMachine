package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class OvenRecipe implements Recipe<OvenBlockEntity> {
    public final ResourceLocation id;
    public final int temperature;
    public final int cookingTime;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;

    public OvenRecipe(ResourceLocation id, int temperature, int cookingTime, NonNullList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.temperature = temperature;
        this.cookingTime = cookingTime;
        this.input = input;
        this.output = output;
    }

    public boolean isTemperatureMatch(OvenBlockEntity pContainer) {
        return this.temperature == pContainer.temperature.temperature;
    }

    public boolean isCookingTimeMatch(OvenBlockEntity pContainer) {
        return this.cookingTime >= pContainer.maxCookingTime.cookingTime;
    }

    public boolean isStateMatch(OvenBlockEntity pContainer) {
        return this.isTemperatureMatch(pContainer) || this.isCookingTimeMatch(pContainer);
    }

    @Override
    public boolean matches(OvenBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (!pContainer.isRunning) return false;
        if (!pContainer.getItem(OvenBlockEntity.OUTPUT).isEmpty()) return false;
        return RecipeMatcher.findMatches(pContainer.getInput(), this.input) != null;
    }

    @Override
    public ItemStack assemble(OvenBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        ItemStack output = this.output.copy();
        int count = output.getCount();
        int multiple = Math.min(pContainer.getMaxStackSize(), output.getMaxStackSize()) / count;
        for (int slot : OvenBlockEntity.INPUT_SLOTS) {
            ItemStack input = pContainer.getItem(slot);
            if (input.isEmpty()) continue;
            multiple = Math.min(multiple, input.getCount());
        }
        pContainer.outputMultiple = multiple;
        output.setCount(count * multiple);
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.OVEN_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.OVEN_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<OvenRecipe> {
        @Override
        public OvenRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            int temperature = GsonHelper.getAsInt(pSerializedRecipe, "temperature");
            int cookingTime = GsonHelper.getAsInt(pSerializedRecipe, "cookingTime");
            JsonArray inputsArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "input");
            NonNullList<Ingredient> input = NonNullList.withSize(OvenBlockEntity.INPUT_SLOTS.length, Ingredient.EMPTY);
            for (int i = 0; i < inputsArray.size(); i++) {
                input.set(i, Ingredient.fromJson(inputsArray.get(i)));
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new OvenRecipe(pRecipeId, temperature, cookingTime, input, output);
        }

        @Override
        public @Nullable OvenRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int temperature = pBuffer.readInt();
            int cookingTime = pBuffer.readInt();
            NonNullList<Ingredient> input = NonNullList.withSize(OvenBlockEntity.INPUT_SLOTS.length, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            return new OvenRecipe(pRecipeId, temperature, cookingTime, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, OvenRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.temperature);
            pBuffer.writeInt(pRecipe.cookingTime);
            for (Ingredient ingredient : pRecipe.input) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
