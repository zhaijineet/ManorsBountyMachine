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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.compat.jei.category.*;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;
import net.zhaiji.manorsbountymachine.recipe.*;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitItem;
import net.zhaiji.manorsbountymachine.register.InitRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static final RecipeType<BlenderRecipe> BLENDER = RecipeType.create(ManorsBountyMachine.MOD_ID, "blender", BlenderRecipe.class);
    public static final RecipeType<CuttingBoardRecipeWrapper> CUTTING_BOARD = RecipeType.create(ManorsBountyMachine.MOD_ID, "cutting_board", CuttingBoardRecipeWrapper.class);
    public static final RecipeType<StockPotRecipe> STOCK_POT = RecipeType.create(ManorsBountyMachine.MOD_ID, "stock_pot", StockPotRecipe.class);
    public static final RecipeType<SaucepanAndWhiskRecipe> SAUCEPAN_AND_WHISK = RecipeType.create(ManorsBountyMachine.MOD_ID, "saucepan_and_whisk", SaucepanAndWhiskRecipe.class);
    public static final RecipeType<ShakerRecipe> SHAKER = RecipeType.create(ManorsBountyMachine.MOD_ID, "shaker", ShakerRecipe.class);

    public static final RecipeType<FastFryRecipe> FRYER_SMOKING = RecipeType.create(ManorsBountyMachine.MOD_ID, "fryer_smoking", FastFryRecipe.class);
    public static final RecipeType<OvenRecipe> OVEN_SMOKING = RecipeType.create(ManorsBountyMachine.MOD_ID, "oven_smoking", OvenRecipe.class);

    public static int INDEX = 0;
    public static List<ResourceKey<CreativeModeTab>> TAB_KEY = new ArrayList<>();
    public static boolean INITIALIZE = false;
    public static Map<Item, Integer> ITEM_ORDER = new HashMap<>();

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
        registration.addRecipeCategories(new BlenderRecipeCategory(guiHelper));
        registration.addRecipeCategories(new CuttingBoardRecipeCategory(guiHelper));
        registration.addRecipeCategories(new StockPotRecipeCategory(guiHelper));
        registration.addRecipeCategories(new SaucepanAndWhiskRecipeCategory(guiHelper));
        registration.addRecipeCategories(new ShakerRecipeCategory(guiHelper));

        registration.addRecipeCategories(new FryerSmokingRecipeCategory(guiHelper));
        registration.addRecipeCategories(new OvenSmokingRecipeCategory(guiHelper));
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
        registration.addRecipeCatalyst(InitBlock.BLENDER.get(), BLENDER);
        registration.addRecipeCatalyst(InitBlock.CUTTING_BOARD.get(), CUTTING_BOARD);
        registration.addRecipeCatalyst(InitBlock.STOCK_POT.get(), STOCK_POT);
        registration.addRecipeCatalyst(InitBlock.SAUCEPAN_AND_WHISK.get(), SAUCEPAN_AND_WHISK);
        registration.addRecipeCatalyst(InitItem.SHAKER.get(), SHAKER);

        registration.addRecipeCatalyst(InitItem.FRYER.get(), FRYER_SMOKING);
        registration.addRecipeCatalyst(InitItem.OVEN.get(), OVEN_SMOKING);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ICE_CREAM, this.getAllRecipesFor(InitRecipe.ICE_CREAM_RECIPE_TYPE.get()));
        registration.addRecipes(FAST_FRY, this.getAllRecipesFor(InitRecipe.FAST_FRY_RECIPE_TYPE.get()));
        registration.addRecipes(SLOW_FRY, this.getAllRecipesFor(InitRecipe.SLOW_FRY_RECIPE_TYPE.get()));
        registration.addRecipes(OVEN, this.getAllRecipesFor(InitRecipe.OVEN_RECIPE_TYPE.get()));
        registration.addRecipes(TEAPOT, this.getAllRecipesFor(InitRecipe.TEAPOT_RECIPE_TYPE.get()));
        registration.addRecipes(DIM_FERMENTATION, this.getAllRecipesFor(InitRecipe.DIM_FERMENTATION_RECIPE_TYPE.get()));
        registration.addRecipes(NORMAL_FERMENTATION, this.getAllRecipesFor(InitRecipe.NORMAL_FERMENTATION_RECIPE_TYPE.get()));
        registration.addRecipes(BRIGHT_FERMENTATION, this.getAllRecipesFor(InitRecipe.BRIGHT_FERMENTATION_RECIPE_TYPE.get()));
        registration.addRecipes(BLENDER, this.getAllRecipesFor(InitRecipe.BLENDER_RECIPE_TYPE.get()));
        List<CuttingBoardRecipeWrapper> cuttingBoardRecipes = Stream.concat(
                this.getAllRecipesFor(InitRecipe.CUTTING_BOARD_SINGLE_RECIPE_TYPE.get())
                        .stream()
                        .map(CuttingBoardRecipeWrapper::new),
                this.getAllRecipesFor(InitRecipe.CUTTING_BOARD_MULTIPLE_RECIPE_TYPE.get())
                        .stream()
                        .map(CuttingBoardRecipeWrapper::new)
        ).collect(Collectors.toList());
        registration.addRecipes(CUTTING_BOARD, cuttingBoardRecipes);
        registration.addRecipes(STOCK_POT, this.getAllRecipesFor(InitRecipe.STOCK_POT_RECIPE_TYPE.get()));
        registration.addRecipes(SAUCEPAN_AND_WHISK, this.getAllRecipesFor(InitRecipe.SAUCEPAN_AND_WHISK_RECIPE_TYPE.get()));
        registration.addRecipes(SHAKER, this.getAllRecipesFor(InitRecipe.SHAKER_RECIPE_TYPE.get()));

        registration.addRecipes(FRYER_SMOKING, SmokingRecipeManager.fastFryRecipes);
        registration.addRecipes(OVEN_SMOKING, SmokingRecipeManager.ovenRecipes);
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

    public <T extends BaseRecipe<?>> List<T> sort(List<T> recipes) {
        List<T> newRecipes = new ArrayList<>(recipes);
        newRecipes.sort((a, b) -> {
            boolean x = ITEM_ORDER.containsKey(a.output.getItem());
            boolean y = ITEM_ORDER.containsKey(b.output.getItem());
            if (x && y) {
                return Integer.compare(ITEM_ORDER.get(a.output.getItem()), ITEM_ORDER.get(b.output.getItem()));
            } else if (x) {
                return -1;
            } else if (y) {
                return 1;
            } else {
                return 0;
            }
        });
        return newRecipes;
    }

    public <C extends Container, T extends BaseRecipe<C>> List<T> getAllRecipesFor(net.minecraft.world.item.crafting.RecipeType<T> recipeType) {
        RecipeManager recipeManager = this.getRecipeManager();
        return this.sort(recipeManager.getAllRecipesFor(recipeType));
    }
}
