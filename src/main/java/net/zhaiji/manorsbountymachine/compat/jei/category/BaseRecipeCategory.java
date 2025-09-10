package net.zhaiji.manorsbountymachine.compat.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;

public abstract class BaseRecipeCategory<T> implements IRecipeCategory<T> {
    public IGuiHelper guiHelper;
    public int width;
    public int height;

    public BaseRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        this.width = 154;
        this.height = 74;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
