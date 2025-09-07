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
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider.ITEMS_SIZE;
import static net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider.OUTPUT;

public class ShakerRecipe extends BaseRecipe<RecipeWrapper> implements OneInputRecipe {
    public final NonNullList<Ingredient> input;

    public ShakerRecipe(ResourceLocation id, NonNullList<Ingredient> input, ItemStack output) {
        super(id, output);
        this.input = input;
    }

    public boolean matches(RecipeWrapper container) {
        if (container.getItem(OUTPUT).isEmpty()) return false;
        int size = container.getContainerSize();
        NonNullList<ItemStack> input = NonNullList.withSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            input.set(i, container.getItem(i));
        }
        return this.isInputMatch(input);
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        return this.matches(pContainer);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.SHAKER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.SHAKER_RECIPE_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getInput() {
        return this.input;
    }

    public static class Serializer extends BaseRecipeSerializer<ShakerRecipe> {
        @Override
        public ShakerRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new ShakerRecipe(
                    pRecipeId,
                    this.getInput(pSerializedRecipe, ITEMS_SIZE),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable ShakerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new ShakerRecipe(
                    pRecipeId,
                    this.getInput(pBuffer, ITEMS_SIZE),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ShakerRecipe pRecipe) {
            this.toInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
