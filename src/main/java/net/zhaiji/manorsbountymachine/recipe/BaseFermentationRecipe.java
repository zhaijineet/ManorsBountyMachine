package net.zhaiji.manorsbountymachine.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class BaseFermentationRecipe implements Recipe<FermenterBlockEntity> {
    public final ResourceLocation id;
    public final FermenterBlockEntity.LightState lightState;
    public final int cookingTime;
    public final Ingredient bottle;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;

    public BaseFermentationRecipe(ResourceLocation id, FermenterBlockEntity.LightState lightState, int cookingTime, Ingredient bottle, NonNullList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.lightState = lightState;
        this.cookingTime = cookingTime;
        this.bottle = bottle;
        this.input = input;
        this.output = output;
    }

    public boolean hasBottle() {
        return !this.bottle.isEmpty();
    }

    public boolean isBottleMatch(ItemStack bottle) {
        return this.bottle.test(bottle);
    }

    @Override
    public boolean matches(FermenterBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (this.lightState != pContainer.getLightState()) return false;
        if (this.hasBottle() && !this.isBottleMatch(pContainer.getItem(FermenterBlockEntity.BOTTLE))) return false;
        NonNullList<ItemStack> inputStacks = pContainer.getInput();
        NonNullList<Ingredient> inputIngredients = NonNullList.of(Ingredient.EMPTY, this.input.toArray(Ingredient[]::new));
        return RecipeMatcher.findMatches(inputStacks, inputIngredients) != null;
    }

    @Override
    public ItemStack assemble(FermenterBlockEntity pContainer, RegistryAccess pRegistryAccess) {
        ItemStack output = this.output.copy();
        int count = output.getCount();
        int multiple = Math.min(pContainer.getMaxStackSize(), output.getMaxStackSize()) / count;
        for (int slot : FermenterBlockEntity.INPUT_SLOTS) {
            ItemStack input = pContainer.getItem(slot);
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

    public static abstract class BaseFermentationRecipeSerializer<T extends BaseFermentationRecipe> implements RecipeSerializer<T> {
        public static final int size = FermenterBlockEntity.INPUT_SLOTS.length;

        public static int getCookingTime(JsonObject serializedRecipe) {
            return GsonHelper.getAsInt(serializedRecipe, "cookingTime");
        }

        public static int getCookingTime(FriendlyByteBuf buffer) {
            return buffer.readInt();
        }

        public static Ingredient getBottle(JsonObject serializedRecipe) {
            if (serializedRecipe.has("bottle")) {
                return Ingredient.fromJson(GsonHelper.getAsJsonObject(serializedRecipe, "bottle"));
            }
            return Ingredient.EMPTY;
        }

        public static Ingredient getBottle(FriendlyByteBuf buffer) {
            return Ingredient.fromNetwork(buffer);
        }

        public static NonNullList<Ingredient> getInput(JsonObject serializedRecipe) {
            JsonArray inputsArray = GsonHelper.getAsJsonArray(serializedRecipe, "input");
            NonNullList<Ingredient> input = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < inputsArray.size(); i++) {
                input.set(i, Ingredient.fromJson(inputsArray.get(i)));
            }
            return input;
        }

        public static NonNullList<Ingredient> getInput(FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input = NonNullList.withSize(size, Ingredient.EMPTY);
            input.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
            return input;
        }

        public static ItemStack getOutput(JsonObject serializedRecipe) {
            return ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "output"));
        }

        public static ItemStack getOutput(FriendlyByteBuf buffer) {
            return buffer.readItem();
        }

        public abstract T createRecipe(ResourceLocation id, int cookingTime, Ingredient bottle, NonNullList<Ingredient> input, ItemStack output);

        @Override
        public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return this.createRecipe(
                    pRecipeId,
                    getCookingTime(pSerializedRecipe),
                    getBottle(pSerializedRecipe),
                    getInput(pSerializedRecipe),
                    getOutput(pSerializedRecipe)
            );
        }

        @Override
        public @Nullable T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return this.createRecipe(
                    pRecipeId,
                    getCookingTime(pBuffer),
                    getBottle(pBuffer),
                    getInput(pBuffer),
                    getOutput(pBuffer)
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
            pBuffer.writeInt(pRecipe.cookingTime);
            pRecipe.bottle.toNetwork(pBuffer);
            for (Ingredient ingredient : pRecipe.input) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
