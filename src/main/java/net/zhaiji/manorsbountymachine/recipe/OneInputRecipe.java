package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.RecipeMatcher;

public interface OneInputRecipe {
    NonNullList<Ingredient> getInput();

    default boolean isInputMatch(NonNullList<ItemStack> input) {
        return RecipeMatcher.findMatches(input, this.getInput()) != null;
    }
}
