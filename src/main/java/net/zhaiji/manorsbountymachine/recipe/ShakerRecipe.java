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
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class ShakerRecipe implements Recipe<RecipeWrapper> {
    public final ResourceLocation id;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;

    public ShakerRecipe(ResourceLocation id, NonNullList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        ItemStack cup = pContainer.getItem(ShakerCapabilityProvider.OUTPUT);
        if (cup.isEmpty()) return false;
        int size = pContainer.getContainerSize();
        NonNullList<ItemStack> input = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            input.set(i, pContainer.getItem(i));
        }
        return RecipeMatcher.findMatches(input, this.input) != null;
    }

    @Override
    public ItemStack assemble(RecipeWrapper pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copy();
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
        return InitRecipe.SHAKER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.SHAKER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ShakerRecipe> {
        @Override
        public ShakerRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonArray inputsArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "input");
            NonNullList<Ingredient> input = NonNullList.withSize(ShakerCapabilityProvider.ITEMS_SIZE, Ingredient.EMPTY);
            for (int i = 0; i < inputsArray.size(); i++) {
                input.set(i, Ingredient.fromJson(inputsArray.get(i)));
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new ShakerRecipe(pRecipeId, input, output);
        }

        @Override
        public @Nullable ShakerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> input = NonNullList.withSize(ShakerCapabilityProvider.ITEMS_SIZE, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            return new ShakerRecipe(pRecipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ShakerRecipe pRecipe) {
            for (Ingredient ingredient : pRecipe.input) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
