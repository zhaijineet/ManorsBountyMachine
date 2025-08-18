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

public class SlowFryRecipe implements Recipe<FryerBlockEntity.FryerCraftContainer> {
    public final ResourceLocation id;
    public final Ingredient input;
    public final ItemStack output;

    public SlowFryRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(FryerBlockEntity.FryerCraftContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        FryerBlockEntity blockEntity = pContainer.blockEntity;
        if (!blockEntity.isRunning) return false;
        int cookingTime = blockEntity.cookingTime;
        if (cookingTime < FryerBlockEntity.SLOW_COOKING_TIME || cookingTime > FryerBlockEntity.FAIL_COOKING_TIME) return false;
        return this.input.test(pContainer.getItem());
    }

    @Override
    public ItemStack assemble(FryerBlockEntity.FryerCraftContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack output = this.output.copy();
        output.setCount(pContainer.getItem().getCount());
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
        return InitRecipe.SLOW_FRY_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.SLOW_FRY_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<SlowFryRecipe> {
        @Override
        public SlowFryRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new SlowFryRecipe(pRecipeId, input, output);
        }

        @Override
        public @Nullable SlowFryRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            return new SlowFryRecipe(pRecipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SlowFryRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
