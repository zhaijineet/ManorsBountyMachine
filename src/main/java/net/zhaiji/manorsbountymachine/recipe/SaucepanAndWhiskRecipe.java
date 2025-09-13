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
import net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity.MAIN_INPUT_SLOTS;
import static net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity.SECONDARY_INPUT_SLOTS;
import static net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity.OUTPUT;

public class SaucepanAndWhiskRecipe extends BaseRecipe<SaucepanAndWhiskBlockEntity> implements HasContainerItem, TwoInputRecipe {
    public final HeatState heatState;
    public final Ingredient container;
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;

    public SaucepanAndWhiskRecipe(ResourceLocation id, String heatState, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, ItemStack output) {
        super(id, output);
        this.heatState = this.getHeatState(heatState);
        this.container = container;
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
    }

    public HeatState getHeatState(String heatState) {
        return switch (heatState) {
            case "no_need", "NO_NEED" -> HeatState.NO_NEED;
            case "no_check", "NO_CHECK" -> HeatState.NO_CHECK;
            default -> HeatState.NEED;
        };
    }

    public boolean checkHeatState(SaucepanAndWhiskBlockEntity blockEntity) {
        if (this.heatState == HeatState.NO_CHECK) {
            return true;
        } else {
            boolean onHeat = blockEntity.onHeatBlock();
            return onHeat && this.heatState == HeatState.NEED
                    || !onHeat && this.heatState == HeatState.NO_NEED;
        }
    }

    public boolean matches(SaucepanAndWhiskBlockEntity container) {
        if (!this.checkHeatState(container)) return false;
        if (!this.isContainerMatch(container.getItem(OUTPUT))) return false;
        return this.isInputMatch(container.getMainInput(), container.getSecondaryInput());
    }


    @Override
    public boolean matches(SaucepanAndWhiskBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        return this.matches(pContainer);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_TYPE.get();
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

    public enum HeatState {
        NEED,
        NO_NEED,
        NO_CHECK
    }

    public static class Serializer extends BaseRecipeSerializer<SaucepanAndWhiskRecipe> {
        @Override
        public SaucepanAndWhiskRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new SaucepanAndWhiskRecipe(
                    pRecipeId,
                    this.getNullableString(pSerializedRecipe, "heatState"),
                    this.getContainer(pSerializedRecipe),
                    this.getMainInput(pSerializedRecipe, MAIN_INPUT_SLOTS.length),
                    this.getSecondaryInput(pSerializedRecipe, SECONDARY_INPUT_SLOTS.length),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable SaucepanAndWhiskRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new SaucepanAndWhiskRecipe(
                    pRecipeId,
                    this.getString(pBuffer),
                    this.getContainer(pBuffer),
                    this.getMainInput(pBuffer, MAIN_INPUT_SLOTS.length),
                    this.getSecondaryInput(pBuffer, SECONDARY_INPUT_SLOTS.length),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SaucepanAndWhiskRecipe pRecipe) {
            this.toString(pBuffer, pRecipe.heatState.toString().toLowerCase());
            this.toContainer(pBuffer, pRecipe.container);
            this.toMainInput(pBuffer, pRecipe.mainInput);
            this.toSecondaryInput(pBuffer, pRecipe.secondaryInput);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
