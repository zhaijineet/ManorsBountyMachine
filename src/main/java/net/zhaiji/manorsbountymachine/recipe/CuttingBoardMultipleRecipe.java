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
import net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardMultipleRecipe implements Recipe<CuttingBoardBlockEntity.CuttingBoardCraftContainer> {
    public final ResourceLocation id;
    public final boolean isShaped;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;

    public CuttingBoardMultipleRecipe(ResourceLocation id, boolean isShaped, NonNullList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.isShaped = isShaped;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(CuttingBoardBlockEntity.CuttingBoardCraftContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (isShaped) {
            for (int i = 0; i < this.input.size(); i++) {
                if (!this.input.get(i).test(pContainer.getAllItem().get(i))) {
                    return false;
                }
            }
            return true;
        }
        return RecipeMatcher.findMatches(pContainer.getAllItem(), this.input) != null;
    }

    @Override
    public ItemStack assemble(CuttingBoardBlockEntity.CuttingBoardCraftContainer pContainer, RegistryAccess pRegistryAccess) {
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
        return InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CuttingBoardMultipleRecipe> {
        @Override
        public CuttingBoardMultipleRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            boolean isShaped = GsonHelper.getAsBoolean(pSerializedRecipe, "isShaped");
            JsonArray inputArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "input");
            NonNullList<Ingredient> input = NonNullList.withSize(CuttingBoardBlockEntity.ITEMS_SIZE, Ingredient.EMPTY);
            for (int i = 0; i < inputArray.size(); i++) {
                input.set(i, Ingredient.fromJson(inputArray.get(i)));
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new CuttingBoardMultipleRecipe(pRecipeId, isShaped, input, output);
        }

        @Override
        public @Nullable CuttingBoardMultipleRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            boolean isShaped = pBuffer.readBoolean();
            NonNullList<Ingredient> input = NonNullList.withSize(CuttingBoardBlockEntity.ITEMS_SIZE, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            return new CuttingBoardMultipleRecipe(pRecipeId, isShaped, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CuttingBoardMultipleRecipe pRecipe) {
            pBuffer.writeBoolean(pRecipe.isShaped);
            for (Ingredient ingredient : pRecipe.input) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
