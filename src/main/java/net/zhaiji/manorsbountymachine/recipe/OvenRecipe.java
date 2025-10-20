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
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity.INPUT_SLOTS;

public class OvenRecipe extends BaseRecipe<OvenBlockEntity> implements CookingTimeRecipe, OneInputRecipe {
    public final int temperature;
    public final int maxCookingTime;
    public final NonNullList<Ingredient> input;

    public OvenRecipe(ResourceLocation id, int temperature, int maxCookingTime, NonNullList<Ingredient> input, ItemStack output) {
        super(id, output);
        this.temperature = temperature;
        this.maxCookingTime = maxCookingTime;
        this.input = input;
    }

    public boolean isTemperatureMatch(OvenBlockEntity pContainer) {
        return this.temperature == pContainer.temperature.temperature;
    }

    public boolean isStateMatch(OvenBlockEntity pContainer) {
        return this.isTemperatureMatch(pContainer) && this.isCookingTimeMatch(pContainer.cookingTime);
    }

    @Override
    public boolean matches(OvenBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        return this.isInputMatch(pContainer.getInput());
    }

    @Override
    public ItemStack assemble(OvenBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        ItemStack output = this.output.copy();
        int count = output.getCount();
        int multiple = Math.min(pContainer.getMaxStackSize(), output.getMaxStackSize()) / count;
        for (int slot : INPUT_SLOTS) {
            ItemStack input = pContainer.getItem(slot);
            if (input.isEmpty()) continue;
            multiple = Math.min(multiple, input.getCount());
        }
        pContainer.outputMultiple = multiple;
        return this.output.copyWithCount(count * multiple);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.OVEN_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.OVEN_RECIPE_TYPE.get();
    }

    @Override
    public int getMaxCookingTime() {
        return this.maxCookingTime;
    }

    @Override
    public NonNullList<Ingredient> getInput() {
        return this.input;
    }

    public static class Serializer extends BaseRecipeSerializer<OvenRecipe> {
        @Override
        public OvenRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new OvenRecipe(
                    pRecipeId,
                    this.getInt(pSerializedRecipe, "temperature"),
                    this.getCookingTime(pSerializedRecipe),
                    this.getInput(pSerializedRecipe, INPUT_SLOTS.length),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable OvenRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new OvenRecipe(
                    pRecipeId,
                    this.getInt(pBuffer),
                    this.getCookingTime(pBuffer),
                    this.getInput(pBuffer, INPUT_SLOTS.length),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, OvenRecipe pRecipe) {
            this.toInt(pBuffer, pRecipe.temperature);
            this.toCookingTime(pBuffer, pRecipe.maxCookingTime);
            this.toInput(pBuffer, pRecipe.input);
            this.toOutput(pBuffer, pRecipe.output);
        }
    }
}
