package net.zhaiji.manorsbountymachine.compat.jei.category;

import net.zhaiji.manorsbountymachine.recipe.BaseRecipe;
import net.zhaiji.manorsbountymachine.recipe.CuttingBoardMultipleRecipe;
import net.zhaiji.manorsbountymachine.recipe.CuttingBoardSingleRecipe;

import java.util.Optional;

import static net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity.CuttingBoardCraftContainer;

public class CuttingBoardRecipeWrapper {
    public BaseRecipe<CuttingBoardCraftContainer> recipe;

    public CuttingBoardRecipeWrapper(BaseRecipe<CuttingBoardCraftContainer> recipe) {
        this.recipe = recipe;
    }

    public boolean isSingleRecipe() {
        return this.recipe instanceof CuttingBoardSingleRecipe;
    }

    public Optional<CuttingBoardSingleRecipe> getSingleRecipe() {
        return this.isSingleRecipe() ? Optional.of((CuttingBoardSingleRecipe) this.recipe) : Optional.empty();
    }

    public boolean isMultipleRecipe() {
        return this.recipe instanceof CuttingBoardMultipleRecipe;
    }

    public Optional<CuttingBoardMultipleRecipe> getMultipleRecipe() {
        return this.isMultipleRecipe() ? Optional.of((CuttingBoardMultipleRecipe) this.recipe) : Optional.empty();
    }
}
