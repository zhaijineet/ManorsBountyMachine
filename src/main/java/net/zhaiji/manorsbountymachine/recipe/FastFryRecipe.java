package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.block.entity.FryerBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class FastFryRecipe implements Recipe<FryerBlockEntity.FryerCraftContainer> {
    public final ResourceLocation id;
    public final Ingredient input;
    public final ItemStack output;

    public FastFryRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(FryerBlockEntity.FryerCraftContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        FryerBlockEntity blockEntity = pContainer.blockEntity;
        int cookingTime = blockEntity.cookingTime;
        if (cookingTime < FryerBlockEntity.FAST_COOKING_TIME || cookingTime > FryerBlockEntity.SLOW_COOKING_TIME)
            return false;
        return input.test(pContainer.getItem());
    }

    @Override
    public ItemStack assemble(FryerBlockEntity.FryerCraftContainer pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copyWithCount(pContainer.getItem().getCount());
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
        return InitRecipe.FAST_FRY_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.FAST_FRY_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FastFryRecipe> {
        @Override
        public FastFryRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new FastFryRecipe(pRecipeId, input, output);
        }

        @Override
        public @Nullable FastFryRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            return new FastFryRecipe(pRecipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FastFryRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
