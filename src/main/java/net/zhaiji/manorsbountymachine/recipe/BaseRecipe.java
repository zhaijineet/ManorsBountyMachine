package net.zhaiji.manorsbountymachine.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class BaseRecipe<T extends Container> implements Recipe<T> {
    public final ResourceLocation id;
    public final ItemStack output;

    public BaseRecipe(ResourceLocation id, ItemStack output) {
        this.id = id;
        this.output = output;
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
