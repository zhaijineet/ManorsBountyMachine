package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import org.jetbrains.annotations.Nullable;

import static net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity.*;

public abstract class BaseFermentationRecipe extends BaseRecipe<FermenterBlockEntity> implements CookingTimeRecipe, HasContainerItem, OneInputRecipe {
    public final FermenterBlockEntity.LightState lightState;
    public final int maxCookingTime;
    public final Ingredient container;
    public final NonNullList<Ingredient> input;

    public BaseFermentationRecipe(ResourceLocation id, FermenterBlockEntity.LightState lightState, int maxCookingTime, Ingredient container, NonNullList<Ingredient> input, ItemStack output) {
        super(id, output);
        this.lightState = lightState;
        this.maxCookingTime = maxCookingTime;
        this.container = container;
        this.input = input;
    }

    @Override
    public boolean matches(FermenterBlockEntity pContainer, Level pLevel) {
        if (this.lightState != pContainer.getLightState()) return false;
        if (this.hasContainer() && !this.isContainerMatch(pContainer.getItem(CONTAINER))) return false;
        return this.isInputMatch(pContainer.getInput());
    }

    @Override
    public ItemStack assemble(FermenterBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        ItemStack output = this.output.copy();
        int count = output.getCount();
        int multiple = Math.min(pContainer.getMaxStackSize(), output.getMaxStackSize()) / count;
        for (int slot : INPUT_SLOTS) {
            ItemStack input = pContainer.getItem(slot);
            if (input.isEmpty()) continue;
            if (slot == CONTAINER && this.hasContainer()) {
                multiple = Math.min(multiple, input.getCount() / count);
                continue;
            }
            if (ManorsBountyCompat.isDamageableMaterial(input)) {
                multiple = Math.min(multiple, input.getMaxDamage() - input.getDamageValue());
            } else {
                multiple = Math.min(multiple, input.getCount());
            }
        }
        pContainer.outputMultiple = multiple;
        return this.output.copyWithCount(count * multiple);
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
    public NonNullList<Ingredient> getInput() {
        return this.input;
    }

    public static abstract class BaseFermentationRecipeSerializer<T extends BaseFermentationRecipe> extends BaseRecipeSerializer<T> {
        public abstract T createRecipe(ResourceLocation id, int cookingTime, Ingredient container, NonNullList<Ingredient> input, ItemStack output);

        @Override
        public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return this.createRecipe(
                    pRecipeId,
                    this.getCookingTime(pSerializedRecipe),
                    this.getContainer(pSerializedRecipe),
                    this.getInput(pSerializedRecipe, MATERIAL_SLOTS.length),
                    this.getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return this.createRecipe(
                    pRecipeId,
                    this.getCookingTime(pBuffer),
                    this.getContainer(pBuffer),
                    this.getInput(pBuffer, MATERIAL_SLOTS.length),
                    this.getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
            this.toCookingTime(pBuffer, pRecipe.maxCookingTime);
            this.toContainer(pBuffer, pRecipe.container);
            this.toInput(pBuffer, pRecipe.input);
            this.toItem(pBuffer, pRecipe.output);
        }
    }
}
