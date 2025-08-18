package net.zhaiji.manorsbountymachine.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.category.FastFryRecipeCategory;
import net.zhaiji.manorsbountymachine.compat.jei.category.IceCreamRecipeCategory;
import net.zhaiji.manorsbountymachine.compat.jei.category.OvenRecipeCategory;
import net.zhaiji.manorsbountymachine.compat.jei.category.SlowFryRecipeCategory;
import net.zhaiji.manorsbountymachine.recipe.FastFryRecipe;
import net.zhaiji.manorsbountymachine.recipe.IceCreamRecipe;
import net.zhaiji.manorsbountymachine.recipe.OvenRecipe;
import net.zhaiji.manorsbountymachine.recipe.SlowFryRecipe;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

@JeiPlugin
public class ManorsBountyMachineJeiPlugin implements IModPlugin {
    public static final RecipeType<IceCreamRecipe> ICE_CREAM = RecipeType.create(ManorsBountyMachine.MOD_ID, "ice_cream", IceCreamRecipe.class);
    public static final RecipeType<FastFryRecipe> FAST_FRY = RecipeType.create(ManorsBountyMachine.MOD_ID, "fast_fry", FastFryRecipe.class);
    public static final RecipeType<SlowFryRecipe> SLOW_FRY = RecipeType.create(ManorsBountyMachine.MOD_ID, "slow_fry", SlowFryRecipe.class);
    public static final RecipeType<OvenRecipe> OVEN = RecipeType.create(ManorsBountyMachine.MOD_ID, "oven", OvenRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new IceCreamRecipeCategory(guiHelper));
        registration.addRecipeCategories(new FastFryRecipeCategory(guiHelper));
        registration.addRecipeCategories(new SlowFryRecipeCategory(guiHelper));
        registration.addRecipeCategories(new OvenRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(InitBlock.ICE_CREAM_MACHINE.get(), ICE_CREAM);
        registration.addRecipeCatalyst(InitBlock.FRYER.get(), FAST_FRY);
        registration.addRecipeCatalyst(InitBlock.FRYER.get(), SLOW_FRY);
        registration.addRecipeCatalyst(InitBlock.OVEN.get(), OVEN);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = this.getRecipeManager();
        registration.addRecipes(ICE_CREAM, recipeManager.getAllRecipesFor(InitRecipe.ICE_CREAM_RECIPE_TYPE.get()));
        registration.addRecipes(FAST_FRY, recipeManager.getAllRecipesFor(InitRecipe.FAST_FRY_RECIPE_TYPE.get()));
        registration.addRecipes(SLOW_FRY, recipeManager.getAllRecipesFor(InitRecipe.SLOW_FRY_RECIPE_TYPE.get()));
        registration.addRecipes(OVEN, recipeManager.getAllRecipesFor(InitRecipe.OVEN_RECIPE_TYPE.get()));
    }

    public RecipeManager getRecipeManager() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level != null) {
            return level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft level must not be null");
        }
    }
}
