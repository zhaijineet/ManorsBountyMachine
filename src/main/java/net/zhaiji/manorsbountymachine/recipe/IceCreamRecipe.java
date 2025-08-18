package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.zhaiji.manorsbountymachine.block.entity.IceCreamMachineBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class IceCreamRecipe implements Recipe<IceCreamMachineBlockEntity> {
    public final ResourceLocation id;
    public final boolean isTowFlavor;
    public final FluidStack fluidStack;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;

    public IceCreamRecipe(ResourceLocation id, boolean isTowFlavor, FluidStack fluidStack, NonNullList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.isTowFlavor = isTowFlavor;
        this.fluidStack = fluidStack;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(IceCreamMachineBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (this.isTowFlavor != pContainer.isTwoFlavor) return false;
        FluidStack fluidStack = pContainer.fluidTank.getFluid();
        if (!fluidStack.equals(this.fluidStack)) return false;
        if (fluidStack.getAmount() < this.fluidStack.getAmount()) return false;
        boolean match = this.input.get(0).test(pContainer.getItem(IceCreamMachineBlockEntity.OUTPUT_SLOT));
        match = match && this.input.get(1).test(pContainer.getItem(IceCreamMachineBlockEntity.LEFT_INPUT_SLOT));
        if (pContainer.isTwoFlavor) {
            match = match && this.input.get(2).test(pContainer.getItem(IceCreamMachineBlockEntity.RIGHT_INPUT_SLOT));
        }
        return match;
    }

    @Override
    public ItemStack assemble(IceCreamMachineBlockEntity pContainer, RegistryAccess pRegistryAccess) {
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
        return InitRecipe.ICE_CREAM_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.ICE_CREAM_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<IceCreamRecipe> {
        @Override
        public IceCreamRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            FluidStack fluidStack =
                    FluidStack.CODEC.parse(
                            JsonOps.INSTANCE,
                            GsonHelper.getAsJsonObject(pSerializedRecipe, "fluid")
                    ).get().left().orElse(FluidStack.EMPTY);
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "input");
            boolean isTowFlavor = ingredients.size() > 2;
            NonNullList<Ingredient> input = NonNullList.withSize(IceCreamMachineBlockEntity.INPUT_SLOTS.length, Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                input.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            return new IceCreamRecipe(pRecipeId, isTowFlavor, fluidStack, input, output);
        }

        @Override
        public @Nullable IceCreamRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            boolean isTowFlavor = pBuffer.readBoolean();
            FluidStack fluidStack = pBuffer.readFluidStack();
            NonNullList<Ingredient> input = NonNullList.withSize(IceCreamMachineBlockEntity.INPUT_SLOTS.length, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            return new IceCreamRecipe(pRecipeId, isTowFlavor, fluidStack, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, IceCreamRecipe pRecipe) {
            pBuffer.writeBoolean(pRecipe.isTowFlavor);
            pBuffer.writeFluidStack(pRecipe.fluidStack);
            for (Ingredient ingredient : pRecipe.input) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
