package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.RecipeMatcher;

public interface TwoInputRecipe {
    NonNullList<Ingredient> getMainInput();

    NonNullList<Ingredient> getSecondaryInput();

    default boolean isMainInputMatch(NonNullList<ItemStack> mainInput) {
        return RecipeMatcher.findMatches(mainInput, this.getMainInput()) != null;
    }

    default boolean isSecondaryInputMatch(NonNullList<ItemStack> secondaryInput) {
        return RecipeMatcher.findMatches(secondaryInput, this.getSecondaryInput()) != null;
    }

    default boolean isInputMatch(NonNullList<ItemStack> mainInput, NonNullList<ItemStack> secondaryInput) {
        return this.isMainInputMatch(mainInput) && this.isSecondaryInputMatch(secondaryInput);
    }
}
