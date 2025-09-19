package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HasListOutgrowthRecipe {
    Map<ItemStack, Float> getOutgrowths();

    default boolean hasOutgrowth() {
        return !this.getOutgrowths().keySet().stream().allMatch(ItemStack::isEmpty);
    }

    default List<ItemStack> rollForOutgrowths(Level level) {
        List<ItemStack> outgrowths = new ArrayList<>();
        this.getOutgrowths().forEach((itemStack, chance) -> {
            if (level.random.nextFloat() < chance) {
                outgrowths.add(itemStack.copy());
            }
        });
        return outgrowths;
    }
}
