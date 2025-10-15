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
        return this.rollForOutgrowth(level, 1);
    }

    default ItemStack rollForOutgrowth(Level level, int count) {
        int outgrowthCount = 0;
        for (int i = 0; i < count; i++) {
            if (level.random.nextFloat() < this.getOutgrowthChance()) {
                outgrowthCount += this.getOutgrowth().getCount();
            }
        }
        return outgrowthCount != 0 ? this.getOutgrowth().copyWithCount(outgrowthCount) : ItemStack.EMPTY;
    }
}
