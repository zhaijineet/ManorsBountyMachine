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

import static net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity.CuttingBoardCraftContainer;

public class CuttingBoardSingleRecipe extends BaseRecipe<CuttingBoardCraftContainer> implements SingleInputRecipe, HasOutgrowthRecipe {
    public final Ingredient tool;
    public final Ingredient input;
    public final ItemStack outgrowth;
    public final float outgrowthChance;

    public CuttingBoardSingleRecipe(ResourceLocation id, Ingredient tool, Ingredient input, ItemStack output, ItemStack outgrowth, float outgrowthChance) {
        super(id, output);
        this.tool = tool;
        this.input = input;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    @Override
    public boolean matches(CuttingBoardCraftContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
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
    public ItemStack getOutgrowth() {
        return this.outgrowth;
    }

    @Override
    public float getOutgrowthChance() {
        return this.outgrowthChance;
    }

    @Override
    public Ingredient getInput() {
        return this.input;
    }

    public static class Serializer extends BaseRecipeSerializer<CuttingBoardSingleRecipe> {
        @Override
        public CuttingBoardSingleRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new CuttingBoardSingleRecipe(
                    pRecipeId,
                    this.getIngredient(pSerializedRecipe, "tool"),
                    this.getInput(pSerializedRecipe),
                    this.getOutput(pSerializedRecipe),
                    this.getOutgrowth(pSerializedRecipe),
                    this.getOutgrowthChance(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable CuttingBoardSingleRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new CuttingBoardSingleRecipe(
                    pRecipeId,
                    this.getIngredient(pBuffer),
                    this.getInput(pBuffer),
                    this.getOutput(pBuffer),
                    this.getOutgrowth(pBuffer),
                    this.getOutgrowthChance(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CuttingBoardSingleRecipe pRecipe) {
            this.toIngredient(pBuffer, pRecipe.tool);
            this.toInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
            this.toOutgrowth(pBuffer, pRecipe.outgrowth);
            this.toOutgrowthChance(pBuffer, pRecipe.outgrowthChance);
        }
    }
}
