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
import net.zhaiji.manorsbountymachine.compat.jei.category.*;
import net.zhaiji.manorsbountymachine.recipe.*;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitItem;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

@JeiPlugin
public class ManorsBountyMachineJeiPlugin implements IModPlugin {
    public static final RecipeType<IceCreamRecipe> ICE_CREAM = RecipeType.create(ManorsBountyMachine.MOD_ID, "ice_cream", IceCreamRecipe.class);
    public static final RecipeType<FastFryRecipe> FAST_FRY = RecipeType.create(ManorsBountyMachine.MOD_ID, "fast_fry", FastFryRecipe.class);
    public static final RecipeType<SlowFryRecipe> SLOW_FRY = RecipeType.create(ManorsBountyMachine.MOD_ID, "slow_fry", SlowFryRecipe.class);
    public static final RecipeType<OvenRecipe> OVEN = RecipeType.create(ManorsBountyMachine.MOD_ID, "oven", OvenRecipe.class);
    public static final RecipeType<TeapotRecipe> TEAPOT = RecipeType.create(ManorsBountyMachine.MOD_ID, "teapot", TeapotRecipe.class);
    public static final RecipeType<DimFermentationRecipe> DIM_FERMENTATION = RecipeType.create(ManorsBountyMachine.MOD_ID, "dim_fermentation", DimFermentationRecipe.class);
    public static final RecipeType<NormalFermentationRecipe> NORMAL_FERMENTATION = RecipeType.create(ManorsBountyMachine.MOD_ID, "normal_fermentation", NormalFermentationRecipe.class);
    public static final RecipeType<BrightFermentationRecipe> BRIGHT_FERMENTATION = RecipeType.create(ManorsBountyMachine.MOD_ID, "bright_fermentation", BrightFermentationRecipe.class);
    public static final RecipeType<ShakerRecipe> SHAKER = RecipeType.create(ManorsBountyMachine.MOD_ID, "shaker", ShakerRecipe.class);

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
        registration.addRecipeCategories(new TeapotRecipeCategory(guiHelper));
        registration.addRecipeCategories(new DimFermentationRecipeCategory(guiHelper));
        registration.addRecipeCategories(new NormalFermentationRecipeCategory(guiHelper));
        registration.addRecipeCategories(new BrightFermentationRecipeCategory(guiHelper));
        registration.addRecipeCategories(new ShakerRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(InitBlock.ICE_CREAM_MACHINE.get(), ICE_CREAM);
        registration.addRecipeCatalyst(InitBlock.FRYER.get(), FAST_FRY);
        registration.addRecipeCatalyst(InitBlock.FRYER.get(), SLOW_FRY);
        registration.addRecipeCatalyst(InitBlock.OVEN.get(), OVEN);
        registration.addRecipeCatalyst(InitBlock.TEAPOT.get(), TEAPOT);
        registration.addRecipeCatalyst(InitBlock.FERMENTER.get(), DIM_FERMENTATION);
        registration.addRecipeCatalyst(InitBlock.FERMENTER.get(), NORMAL_FERMENTATION);
        registration.addRecipeCatalyst(InitBlock.FERMENTER.get(), BRIGHT_FERMENTATION);
        registration.addRecipeCatalyst(InitItem.SHAKER.get(), SHAKER);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = this.getRecipeManager();
        registration.addRecipes(ICE_CREAM, recipeManager.getAllRecipesFor(InitRecipe.ICE_CREAM_RECIPE_TYPE.get()));
        registration.addRecipes(FAST_FRY, recipeManager.getAllRecipesFor(InitRecipe.FAST_FRY_RECIPE_TYPE.get()));
        registration.addRecipes(SLOW_FRY, recipeManager.getAllRecipesFor(InitRecipe.SLOW_FRY_RECIPE_TYPE.get()));
        registration.addRecipes(OVEN, recipeManager.getAllRecipesFor(InitRecipe.OVEN_RECIPE_TYPE.get()));
        registration.addRecipes(TEAPOT, recipeManager.getAllRecipesFor(InitRecipe.TEAPOT_RECIPE_TYPE.get()));
        registration.addRecipes(DIM_FERMENTATION, recipeManager.getAllRecipesFor(InitRecipe.DIM_FERMENTATION_RECIPE_TYPE.get()));
        registration.addRecipes(NORMAL_FERMENTATION, recipeManager.getAllRecipesFor(InitRecipe.NORMAL_FERMENTATION_RECIPE_TYPE.get()));
        registration.addRecipes(BRIGHT_FERMENTATION, recipeManager.getAllRecipesFor(InitRecipe.BRIGHT_FERMENTATION_RECIPE_TYPE.get()));
        registration.addRecipes(SHAKER, recipeManager.getAllRecipesFor(InitRecipe.SHAKER_RECIPE_TYPE.get()));
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
