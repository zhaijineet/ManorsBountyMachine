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
import net.minecraftforge.common.util.RecipeMatcher;
import net.zhaiji.manorsbountymachine.block.entity.TeapotBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.TeapotBlockEntity.*;

public class TeapotRecipe extends BaseRecipe<TeapotBlockEntity> implements OneInputRecipe {
    public final NonNullList<Ingredient> input;

    public TeapotRecipe(ResourceLocation id, NonNullList<Ingredient> input, ItemStack output) {
        super(id, output);
        this.input = input;
    }

    @Override
    public boolean matches(TeapotBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (!this.input.get(0).test(pContainer.getItem(OUTPUT))) return false;
        if (!this.input.get(1).test(pContainer.getItem(DRINK))) return false;
        if (!this.input.get(2).test(pContainer.getItem(MATERIAL))) return false;
        return RecipeMatcher.findMatches(pContainer.items, this.input) != null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.TEAPOT_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.TEAPOT_RECIPE_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getInput() {
        return this.input;
    }

    public static class Serializer extends BaseRecipeSerializer<TeapotRecipe> {
        @Override
        public TeapotRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new TeapotRecipe(
                    pRecipeId,
                    this.getInput(pSerializedRecipe, ITEMS_SIZE),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable TeapotRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new TeapotRecipe(
                    pRecipeId,
                    this.getInput(pBuffer, ITEMS_SIZE),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, TeapotRecipe pRecipe) {
            this.toInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
