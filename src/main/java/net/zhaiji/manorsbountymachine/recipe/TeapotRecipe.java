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
import net.zhaiji.manorsbountymachine.block.entity.TeapotBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class TeapotRecipe implements Recipe<TeapotBlockEntity> {
    public final ResourceLocation id;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;

    public TeapotRecipe(ResourceLocation id, NonNullList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(TeapotBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (!this.input.get(0).test(pContainer.getItem(TeapotBlockEntity.OUTPUT))) return false;
        if (!this.input.get(1).test(pContainer.getItem(TeapotBlockEntity.DRINK))) return false;
        if (!this.input.get(2).test(pContainer.getItem(TeapotBlockEntity.MATERIAL))) return false;
        return RecipeMatcher.findMatches(pContainer.items, this.input) != null;
    }

    @Override
    public ItemStack assemble(TeapotBlockEntity pContainer, RegistryAccess pRegistryAccess) {
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
        return InitRecipe.TEAPOT_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.TEAPOT_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<TeapotRecipe> {
        @Override
        public TeapotRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            JsonArray inputsArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "input");
            NonNullList<Ingredient> input = NonNullList.withSize(TeapotBlockEntity.ITEMS_SIZE, Ingredient.EMPTY);
            for (int i = 0; i < inputsArray.size(); i++) {
                input.set(i, Ingredient.fromJson(inputsArray.get(i)));
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new TeapotRecipe(pRecipeId, input, output);
        }

        @Override
        public @Nullable TeapotRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> input = NonNullList.withSize(TeapotBlockEntity.ITEMS_SIZE, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            return new TeapotRecipe(pRecipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, TeapotRecipe pRecipe) {
            for (Ingredient ingredient : pRecipe.input) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
