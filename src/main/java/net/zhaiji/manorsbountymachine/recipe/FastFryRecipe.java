package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.FryerBlockEntity.*;

public class FastFryRecipe extends BaseRecipe<FryerCraftContainer> implements SingleInputRecipe {
    public final Ingredient input;

    public FastFryRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        super(id, output);
        this.input = input;
    }

    @Override
    public boolean matches(FryerCraftContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        int cookingTime = pContainer.blockEntity.cookingTime;
        if (cookingTime < FAST_COOKING_TIME || cookingTime > SLOW_COOKING_TIME)
            return false;
        return this.isInputMatch(pContainer.getItem());
    }

    @Override
    public ItemStack assemble(FryerCraftContainer pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copyWithCount(pContainer.getItem().getCount());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.FAST_FRY_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.FAST_FRY_RECIPE_TYPE.get();
    }

    @Override
    public Ingredient getInput() {
        return this.input;
    }

    public static class Serializer extends BaseRecipeSerializer<FastFryRecipe> {
        @Override
        public FastFryRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new FastFryRecipe(
                    pRecipeId,
                    this.getInput(pSerializedRecipe),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable FastFryRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new FastFryRecipe(
                    pRecipeId,
                    this.getInput(pBuffer),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, FastFryRecipe pRecipe) {
            this.toInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
