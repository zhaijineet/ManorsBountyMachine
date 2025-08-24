package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;

public abstract class BaseFermentationRecipe implements Recipe<FermenterBlockEntity> {
    public final ResourceLocation id;
    public final FermenterBlockEntity.LightState lightState;
    public final int cookingTime;
    public final NonNullList<Ingredient> input;
    public final ItemStack output;

    public BaseFermentationRecipe(ResourceLocation id, FermenterBlockEntity.LightState lightState, int cookingTime, NonNullList<Ingredient> input, ItemStack output) {
        this.id = id;
        this.lightState = lightState;
        this.cookingTime = cookingTime;
        this.input = input;
        this.output = output;
    }

    public Ingredient getBottle() {
        return this.input.get(0);
    }

    public boolean hasBottle() {
        return !this.getBottle().isEmpty();
    }

    public boolean isBottleMatch(ItemStack bottle) {
        return this.getBottle().test(bottle);
    }

    @Override
    public boolean matches(FermenterBlockEntity pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        if (this.lightState != pContainer.getLightState()) return false;
        if (this.hasBottle() && !this.isBottleMatch(pContainer.getItem(FermenterBlockEntity.BOTTLE))) return false;
        NonNullList<ItemStack> inputStacks = pContainer.getInput();
        NonNullList<Ingredient> inputIngredients = NonNullList.withSize(FermenterBlockEntity.INPUT_SLOTS.length, Ingredient.EMPTY);
        for (int i = 0; i < inputIngredients.size(); i++) {
            inputIngredients.set(i, this.input.get(i + 1));
        }
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
}
