package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity.*;

public class StockPotRecipe extends BaseRecipe<StockPotBlockEntity> implements CookingTimeRecipe, HasContainerItem, TwoInputRecipe {
    public final int maxCookingTime;
    public final Ingredient container;
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;

    public StockPotRecipe(ResourceLocation id, int maxCookingTime, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, ItemStack output) {
        super(id, output);
        this.maxCookingTime = maxCookingTime;
        this.container = container;
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
    }

    @Override
    public boolean matches(StockPotBlockEntity pContainer, Level pLevel) {
        if (!this.isContainerMatch(pContainer.getItem(OUTPUT))) return false;
        return this.isInputMatch(pContainer.getMainInput(), pContainer.getSecondaryInput());
    }

    @Override
    public ItemStack assemble(StockPotBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.STOCK_POT_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.STOCK_POT_RECIPE_TYPE.get();
    }

    @Override
    public int getMaxCookingTime() {
        return this.maxCookingTime;
    }

    @Override
    public Ingredient getContainer() {
        return this.container;
    }

    @Override
    public NonNullList<Ingredient> getMainInput() {
        return this.mainInput;
    }

    @Override
    public NonNullList<Ingredient> getSecondaryInput() {
        return this.secondaryInput;
    }

    public static class Serializer extends BaseRecipeSerializer<StockPotRecipe> {
        @Override
        public StockPotRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new StockPotRecipe(
                    pRecipeId,
                    this.getCookingTime(pSerializedRecipe),
                    this.getContainer(pSerializedRecipe),
                    this.getMainInput(pSerializedRecipe, MAIN_INPUT_SLOTS.length),
                    this.getSecondaryInput(pSerializedRecipe, SECONDARY_INPUT_SLOTS.length),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable StockPotRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new StockPotRecipe(
                    pRecipeId,
                    this.getCookingTime(pBuffer),
                    this.getContainer(pBuffer),
                    this.getMainInput(pBuffer, MAIN_INPUT_SLOTS.length),
                    this.getSecondaryInput(pBuffer, SECONDARY_INPUT_SLOTS.length),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, StockPotRecipe pRecipe) {
            this.toCookingTime(pBuffer, pRecipe.maxCookingTime);
            this.toContainer(pBuffer, pRecipe.container);
            this.toMainInput(pBuffer, pRecipe.mainInput);
            this.toSecondaryInput(pBuffer, pRecipe.secondaryInput);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
