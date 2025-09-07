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
import net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity;
import net.zhaiji.manorsbountymachine.register.InitRecipe;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity.MAIN_INPUT_SLOTS;
import static net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity.SECONDARY_INPUT_SLOTS;

public class BlenderRecipe extends BaseRecipe<BlenderBlockEntity> implements HasContainerItem, TwoInputRecipe, HasOutgrowthRecipe {
    public final Ingredient container;
    public final NonNullList<Ingredient> mainInput;
    public final NonNullList<Ingredient> secondaryInput;
    public final ItemStack outgrowth;
    public final float outgrowthChance;

    public BlenderRecipe(ResourceLocation id, Ingredient container, NonNullList<Ingredient> mainInput, NonNullList<Ingredient> secondaryInput, ItemStack output, ItemStack outgrowth, float outgrowthChance) {
        super(id, output);
        this.container = container;
        this.mainInput = mainInput;
        this.secondaryInput = secondaryInput;
        this.outgrowth = outgrowth;
        this.outgrowthChance = outgrowthChance;
    }

    @Override
    public boolean matches(BlenderBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        ItemStack container = pContainer.getItem(BlenderBlockEntity.CONTAINER);
        if (this.hasContainer() && !this.isContainerMatch(container)) return false;
        if (container.getCount() < this.output.getCount()) return false;
        return this.isInputMatch(pContainer.getMainInput(), pContainer.getSecondaryInput());
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
    public RecipeSerializer<?> getSerializer() {
        return InitRecipe.BLENDER_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return InitRecipe.BLENDER_RECIPE_TYPE.get();
    }

    @Override
    public Ingredient getContainer() {
        return this.container;
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
    public NonNullList<Ingredient> getMainInput() {
        return this.mainInput;
    }

    @Override
    public NonNullList<Ingredient> getSecondaryInput() {
        return this.secondaryInput;
    }

    public static class Serializer extends BaseRecipeSerializer<BlenderRecipe> {
        @Override
        public BlenderRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new BlenderRecipe(
                    pRecipeId,
                    this.getContainer(pSerializedRecipe),
                    this.getMainInput(pSerializedRecipe, MAIN_INPUT_SLOTS.length),
                    this.getSecondaryInput(pSerializedRecipe, SECONDARY_INPUT_SLOTS.length),
                    this.getOutput(pSerializedRecipe),
                    this.getOutgrowth(pSerializedRecipe),
                    this.getOutgrowthChance(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable BlenderRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new BlenderRecipe(
                    pRecipeId,
                    this.getContainer(pBuffer),
                    this.getMainInput(pBuffer, MAIN_INPUT_SLOTS.length),
                    this.getSecondaryInput(pBuffer, SECONDARY_INPUT_SLOTS.length),
                    this.getOutput(pBuffer),
                    this.getOutgrowth(pBuffer),
                    this.getOutgrowthChance(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, BlenderRecipe pRecipe) {
            this.toContainer(pBuffer, pRecipe.container);
            this.toMainInput(pBuffer, pRecipe.mainInput);
            this.toSecondaryInput(pBuffer, pRecipe.secondaryInput);
            this.toOutput(pBuffer, pRecipe.output);
            this.toOutgrowth(pBuffer, pRecipe.outgrowth);
            this.toOutgrowthChance(pBuffer, pRecipe.outgrowthChance);
        }
    }
}
