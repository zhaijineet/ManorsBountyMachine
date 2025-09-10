package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.recipe.*;

public class InitRecipe {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE = DeferredRegister.create(Registries.RECIPE_TYPE, ManorsBountyMachine.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ManorsBountyMachine.MOD_ID);

    public static final RegistryObject<RecipeType<IceCreamRecipe>> ICE_CREAM_RECIPE_TYPE = registerRecipeType("ice_cream");
    public static final RegistryObject<RecipeSerializer<IceCreamRecipe>> ICE_CREAM_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("ice_cream", IceCreamRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<FastFryRecipe>> FAST_FRY_RECIPE_TYPE = registerRecipeType("fast_fry");
    public static final RegistryObject<RecipeSerializer<FastFryRecipe>> FAST_FRY_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("fast_fry", FastFryRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<SlowFryRecipe>> SLOW_FRY_RECIPE_TYPE = registerRecipeType("slow_fry");
    public static final RegistryObject<RecipeSerializer<SlowFryRecipe>> SLOW_FRY_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("slow_fry", SlowFryRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<OvenRecipe>> OVEN_RECIPE_TYPE = registerRecipeType("oven");
    public static final RegistryObject<RecipeSerializer<OvenRecipe>> OVEN_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("oven", OvenRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TeapotRecipe>> TEAPOT_RECIPE_TYPE = registerRecipeType("teapot");
    public static final RegistryObject<RecipeSerializer<TeapotRecipe>> TEAPOT_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("teapot", TeapotRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<DimFermentationRecipe>> DIM_FERMENTATION_RECIPE_TYPE = registerRecipeType("dim_fermentation");
    public static final RegistryObject<RecipeSerializer<DimFermentationRecipe>> DIM_FERMENTATION_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("dim_fermentation", DimFermentationRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<NormalFermentationRecipe>> NORMAL_FERMENTATION_RECIPE_TYPE = registerRecipeType("normal_fermentation");
    public static final RegistryObject<RecipeSerializer<NormalFermentationRecipe>> NORMAL_FERMENTATION_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("normal_fermentation", NormalFermentationRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<BrightFermentationRecipe>> BRIGHT_FERMENTATION_RECIPE_TYPE = registerRecipeType("bright_fermentation");
    public static final RegistryObject<RecipeSerializer<BrightFermentationRecipe>> BRIGHT_FERMENTATION_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("bright_fermentation", BrightFermentationRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<BlenderRecipe>> BLENDER_RECIPE_TYPE = registerRecipeType("blender");
    public static final RegistryObject<RecipeSerializer<BlenderRecipe>> BLENDER_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("blender", BlenderRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<CuttingBoardSingleRecipe>> CUTTING_BOARD_SINGLE_RECIPE_TYPE = registerRecipeType("cutting_board_single");
    public static final RegistryObject<RecipeSerializer<CuttingBoardSingleRecipe>> CUTTING_BOARD_SINGLE_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("cutting_board_single", CuttingBoardSingleRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<CuttingBoardMultipleRecipe>> CUTTING_BOARD_MULTIPLE_RECIPE_TYPE = registerRecipeType("cutting_board_multiple");
    public static final RegistryObject<RecipeSerializer<CuttingBoardMultipleRecipe>> CUTTING_BOARD_MULTIPLE_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("cutting_board_multiple", CuttingBoardMultipleRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<StockPotRecipe>> STOCK_POT_RECIPE_TYPE = registerRecipeType("stock_pot");
    public static final RegistryObject<RecipeSerializer<StockPotRecipe>> STOCK_POT_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("stock_pot", StockPotRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<SaucepanAndWhiskRecipe>> SAUCEPAN_AND_WHISK_RECIPE_TYPE = registerRecipeType("saucepan_and_whisk");
    public static final RegistryObject<RecipeSerializer<SaucepanAndWhiskRecipe>> SAUCEPAN_AND_WHISK_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("saucepan_and_whisk", SaucepanAndWhiskRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<ShakerRecipe>> SHAKER_RECIPE_TYPE = registerRecipeType("shaker");
    public static final RegistryObject<RecipeSerializer<ShakerRecipe>> SHAKER_RECIPE_SERIALIZER = RECIPE_SERIALIZER.register("shaker", ShakerRecipe.Serializer::new);

    public static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerRecipeType(String name) {
        return RECIPE_TYPE.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
