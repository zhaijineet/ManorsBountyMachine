package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.recipe.FastFryRecipe;
import net.zhaiji.manorsbountymachine.recipe.IceCreamRecipe;
import net.zhaiji.manorsbountymachine.recipe.OvenRecipe;
import net.zhaiji.manorsbountymachine.recipe.SlowFryRecipe;

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

    public static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerRecipeType(String name) {
        return RECIPE_TYPE.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
