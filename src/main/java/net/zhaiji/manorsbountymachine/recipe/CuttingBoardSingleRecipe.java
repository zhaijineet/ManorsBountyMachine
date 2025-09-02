package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class CuttingBoardSingleRecipe implements Recipe<CuttingBoardBlockEntity.CuttingBoardCraftContainer> {
    public final ResourceLocation id;
    public final Ingredient tool;
    public final Ingredient input;
    public final ItemStack output;
    public final ItemStack outgrowth;
    public final float outgrowthChance;

    public CuttingBoardSingleRecipe(ResourceLocation id, Ingredient tool, Ingredient input, ItemStack output, ItemStack outgrowth, float outgrowthChance) {
        this.id = id;
        this.tool = tool;
        this.input = input;
        this.output = output;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    public ItemStack getOutgrowth(Level level) {
        return level.random.nextFloat() < this.outgrowthChance ? this.outgrowth.copy() : ItemStack.EMPTY;
    }

    @Override
    public boolean matches(CuttingBoardBlockEntity.CuttingBoardCraftContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (!this.tool.test(pContainer.tool)) return false;
        return this.input.test(pContainer.getMaterial());
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
        return InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CuttingBoardSingleRecipe> {
        @Override
        public CuttingBoardSingleRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient tool = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "tool"));
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            ItemStack outgrowth = pSerializedRecipe.has("outgrowth")
                    ? ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "outgrowth"))
                    : ItemStack.EMPTY;
            float outgrowthChance = outgrowth.isEmpty() ? 0 : GsonHelper.getAsFloat(pSerializedRecipe, "outgrowthChance");
            return new CuttingBoardSingleRecipe(pRecipeId, tool, input, output, outgrowth, outgrowthChance);
        }

        @Override
        public @Nullable CuttingBoardSingleRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient tool = Ingredient.fromNetwork(pBuffer);
            Ingredient input = Ingredient.fromNetwork(pBuffer);
            ItemStack output = pBuffer.readItem();
            ItemStack outgrowth = pBuffer.readItem();
            float outgrowthChance = pBuffer.readFloat();
            return new CuttingBoardSingleRecipe(pRecipeId, tool, input, output, outgrowth, outgrowthChance);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CuttingBoardSingleRecipe pRecipe) {
            pRecipe.tool.toNetwork(pBuffer);
            pRecipe.input.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.output);
            pBuffer.writeItem(pRecipe.outgrowth);
            pBuffer.writeFloat(pRecipe.outgrowthChance);
        }
    }
}
