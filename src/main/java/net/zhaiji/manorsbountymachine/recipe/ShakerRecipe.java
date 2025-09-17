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

import static net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider.INPUT_SLOTS;
import static net.zhaiji.manorsbountymachine.capability.ShakerCapabilityProvider.OUTPUT;

public class ShakerRecipe extends BaseRecipe<RecipeWrapper> implements HasContainerItem, OneInputRecipe {
    public final Ingredient container;
    public final NonNullList<Ingredient> input;

    public ShakerRecipe(ResourceLocation id, Ingredient container, NonNullList<Ingredient> input, ItemStack output) {
        super(id, output);
        this.container = container;
        this.input = input;
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if (this.isContainerMatch(pContainer.getItem(OUTPUT))) return false;
        NonNullList<ItemStack> input = NonNullList.withSize(INPUT_SLOTS.length, ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            input.set(i, pContainer.getItem(INPUT_SLOTS[i]));
        }
        return this.isInputMatch(input);
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
    public Ingredient getContainer() {
        return this.container;
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
                    this.getContainer(pSerializedRecipe),
                    this.getInput(pSerializedRecipe, INPUT_SLOTS.length),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable ShakerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new ShakerRecipe(
                    pRecipeId,
                    this.getContainer(pBuffer),
                    this.getInput(pBuffer, INPUT_SLOTS.length),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ShakerRecipe pRecipe) {
            this.toContainer(pBuffer, pRecipe.container);
            this.toInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
