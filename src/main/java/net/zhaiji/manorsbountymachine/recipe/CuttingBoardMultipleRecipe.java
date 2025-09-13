package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity.CuttingBoardCraftContainer;
import static net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity.ITEMS_SIZE;

public class CuttingBoardMultipleRecipe extends BaseRecipe<CuttingBoardCraftContainer> implements OneInputRecipe {
    public final boolean isShaped;
    public final Ingredient tool;
    public final NonNullList<Ingredient> input;

    public CuttingBoardMultipleRecipe(ResourceLocation id, boolean isShaped, Ingredient tool, NonNullList<Ingredient> input, ItemStack output) {
        super(id, output);
        this.tool = tool;
        this.isShaped = isShaped;
        this.input = input;
    }

    public boolean hasTool() {
        return !this.tool.isEmpty();
    }

    public boolean isToolMatch(ItemStack itemStack) {
        return this.tool.test(itemStack);
    }

    @Override
    public boolean matches(CuttingBoardCraftContainer pContainer, Level pLevel) {
        if (this.hasTool() && !this.isToolMatch(pContainer.tool)) return false;
        if (isShaped) {
            for (int i = 0; i < this.input.size(); i++) {
                if (!this.input.get(i).test(pContainer.getAllItem().get(i))) {
                    return false;
                }
            }
            return true;
        }
        return this.isInputMatch(pContainer.getAllItem());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getInput() {
        return this.input;
    }

    public static class Serializer extends BaseRecipeSerializer<CuttingBoardMultipleRecipe> {
        @Override
        public CuttingBoardMultipleRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new CuttingBoardMultipleRecipe(
                    pRecipeId,
                    this.getBoolean(pSerializedRecipe, "isShaped"),
                    this.getNullableIngredient(pSerializedRecipe, "tool"),
                    this.getInput(pSerializedRecipe, ITEMS_SIZE),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable CuttingBoardMultipleRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new CuttingBoardMultipleRecipe(
                    pRecipeId,
                    this.getBoolean(pBuffer),
                    this.getIngredient(pBuffer),
                    this.getInput(pBuffer, ITEMS_SIZE),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CuttingBoardMultipleRecipe pRecipe) {
            this.toBoolean(pBuffer, pRecipe.isShaped);
            this.toIngredient(pBuffer, pRecipe.tool);
            this.toMainInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
