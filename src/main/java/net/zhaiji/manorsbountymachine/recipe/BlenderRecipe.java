package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

public class BlenderRecipe implements Recipe<BlenderBlockEntity> {
    public final ResourceLocation id;
    public final Ingredient container;
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;
    public final ItemStack output;
    public final ItemStack outgrowth;
    public final float outgrowthChance;

    public BlenderRecipe(ResourceLocation id, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, ItemStack output, ItemStack outgrowth, float outgrowthChance) {
        this.id = id;
        this.container = container;
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    public boolean hasContainer() {
        return !this.container.isEmpty();
    }

    public boolean isContainerMatch(ItemStack container) {
        return this.container.test(container);
    }

    public ItemStack getOutgrowth(Level level) {
        return level.random.nextFloat() < this.outgrowthChance ? this.outgrowth.copy() : ItemStack.EMPTY;
    }

    @Override
    public boolean matches(BlenderBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        ItemStack container = pContainer.getItem(BlenderBlockEntity.CONTAINER);
        if (this.hasContainer() && !this.isContainerMatch(container)) return false;
        if (container.getCount() < this.output.getCount()) return false;
        if (RecipeMatcher.findMatches(pContainer.getMainInput(), this.mainInput) == null) return false;
        return RecipeMatcher.findMatches(pContainer.getSecondaryInput(), this.secondaryInput) != null;
    }

    @Override
    public ItemStack assemble(BlenderBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        ItemStack output = this.output.copy();
        int count = output.getCount();
        int multiple = Math.min(pContainer.getMaxStackSize(), output.getMaxStackSize()) / count;
        for (int slot : BlenderBlockEntity.INPUT_SLOTS) {
            ItemStack input = pContainer.getItem(slot);
            if (slot == BlenderBlockEntity.CONTAINER) {
                multiple = Math.min(multiple, input.getCount() / count);
                continue;
            }
            if (input.isEmpty()) continue;
            multiple = Math.min(multiple, input.getCount());
        }
        pContainer.outputMultiple = multiple;
        output.setCount(count * multiple);
        return output;
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
        return InitRecipe.BLENDER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.BLENDER_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<BlenderRecipe> {
        @Override
        public BlenderRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient container = pSerializedRecipe.has("container")
                    ? Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "container"))
                    : Ingredient.EMPTY;
            JsonArray mainInputArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "mainInput");
            NonNullList<Ingredient> mainInput = NonNullList.withSize(BlenderBlockEntity.MAIN_INPUT_SLOTS.length, Ingredient.EMPTY);
            for (int i = 0; i < mainInputArray.size(); i++) {
                mainInput.set(i, Ingredient.fromJson(mainInputArray.get(i)));
            }
            JsonArray secondaryInputArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "secondaryInput", new JsonArray());
            NonNullList<Ingredient> secondaryInput = NonNullList.withSize(BlenderBlockEntity.SECONDARY_INPUT_SLOTS.length, Ingredient.EMPTY);
            for (int i = 0; i < secondaryInputArray.size(); i++) {
                secondaryInput.set(i, Ingredient.fromJson(secondaryInputArray.get(i)));
            }
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            ItemStack outgrowth = pSerializedRecipe.has("outgrowth")
                    ? ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "outgrowth"))
                    : ItemStack.EMPTY;
            float outgrowthChance = outgrowth.isEmpty() ? 0 : GsonHelper.getAsFloat(pSerializedRecipe, "outgrowthChance");
            return new BlenderRecipe(pRecipeId, container, mainInput, secondaryInput, output, outgrowth, outgrowthChance);
        }

        @Override
        public @Nullable BlenderRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient container = Ingredient.fromNetwork(pBuffer);
            NonNullList<Ingredient> mainInput = NonNullList.withSize(BlenderBlockEntity.MAIN_INPUT_SLOTS.length, Ingredient.EMPTY);
            mainInput.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            NonNullList<Ingredient> secondaryInput = NonNullList.withSize(BlenderBlockEntity.SECONDARY_INPUT_SLOTS.length, Ingredient.EMPTY);
            secondaryInput.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack output = pBuffer.readItem();
            ItemStack outgrowth = pBuffer.readItem();
            float outgrowthChance = pBuffer.readFloat();
            return new BlenderRecipe(pRecipeId, container, mainInput, secondaryInput, output, outgrowth, outgrowthChance);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BlenderRecipe pRecipe) {
            pRecipe.container.toNetwork(pBuffer);
            for (Ingredient ingredient : pRecipe.mainInput) {
                ingredient.toNetwork(pBuffer);
            }
            for (Ingredient ingredient : pRecipe.secondaryInput) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
            pBuffer.writeItem(pRecipe.outgrowth);
            pBuffer.writeFloat(pRecipe.outgrowthChance);
        }
    }
}
