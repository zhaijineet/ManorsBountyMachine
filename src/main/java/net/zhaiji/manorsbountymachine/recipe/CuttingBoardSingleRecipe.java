package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity.CuttingBoardCraftContainer;

public class CuttingBoardSingleRecipe extends BaseRecipe<CuttingBoardCraftContainer> implements SingleInputRecipe, HasListOutgrowthRecipe {
    public final Ingredient tool;
    public final Ingredient input;
    public final Map<ItemStack, Float> outgrowths;

    public CuttingBoardSingleRecipe(ResourceLocation id, Ingredient tool, Ingredient input, ItemStack output, Map<ItemStack, Float> outgrowths) {
        super(id, output);
        this.tool = tool;
        this.input = input;
        this.outgrowths = outgrowths;
    }

    @Override
    public boolean matches(CuttingBoardCraftContainer pContainer, Level pLevel) {
        if (!this.tool.test(pContainer.tool)) return false;
        return this.isInputMatch(pContainer.getMaterial());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_TYPE.get();
    }

    @Override
    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public Map<ItemStack, Float> getOutgrowths() {
        return this.outgrowths;
    }

    public static class Serializer extends BaseRecipeSerializer<CuttingBoardSingleRecipe> {
        @Override
        public CuttingBoardSingleRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new CuttingBoardSingleRecipe(
                    pRecipeId,
                    this.getIngredient(pSerializedRecipe, "tool"),
                    this.getInput(pSerializedRecipe),
                    this.getOutput(pSerializedRecipe),
                    this.getOutgrowths(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable CuttingBoardSingleRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new CuttingBoardSingleRecipe(
                    pRecipeId,
                    this.getIngredient(pBuffer),
                    this.getInput(pBuffer),
                    this.getOutput(pBuffer),
                    this.getOutgrowths(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CuttingBoardSingleRecipe pRecipe) {
            this.toIngredient(pBuffer, pRecipe.tool);
            this.toInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
            this.toOutgrowths(pBuffer, pRecipe.outgrowths);
        }
    }
}
