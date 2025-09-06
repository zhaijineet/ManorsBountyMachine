package net.zhaiji.manorsbountymachine.recipe;

public interface CookingTimeRecipe {
    int getMaxCookingTime();

    default boolean isCookingTimeMatch(int cookingTime) {
        return cookingTime >= this.getMaxCookingTime();
    }
}
