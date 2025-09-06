package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface HasContainerItem {
    Ingredient getContainer();

    default boolean hasContainer() {
        return !this.getContainer().isEmpty();
    }

    default boolean isContainerMatch(ItemStack container) {
        return this.getContainer().test(container);
    }
}
