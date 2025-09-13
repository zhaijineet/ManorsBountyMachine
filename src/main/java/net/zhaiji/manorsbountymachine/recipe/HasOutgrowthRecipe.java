package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface HasOutgrowthRecipe {
    ItemStack getOutgrowth();

    float getOutgrowthChance();

    default boolean hasOutgrowth() {
        return !this.getOutgrowth().isEmpty();
    }

    default ItemStack rollForOutgrowth(Level level) {
        return level.random.nextFloat() < this.getOutgrowthChance() ? this.getOutgrowth().copy() : ItemStack.EMPTY;
    }
}
