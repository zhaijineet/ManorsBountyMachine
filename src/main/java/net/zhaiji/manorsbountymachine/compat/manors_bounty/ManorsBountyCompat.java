package net.zhaiji.manorsbountymachine.compat.manors_bounty;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.CooktopBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManorsBountyCompat {
    public static final String MOD_ID = "manors_bounty";

    public static final TagKey<Item> ICE_CREAM_AND_CONE = ItemTags.create(getManorsBountyResourceLocation("ice_cream_and_cone"));
    public static final TagKey<Item> DAMAGEABLE_MATERIAL = ItemTags.create(getManorsBountyResourceLocation("damageable_material"));
    //    public static final TagKey<Item> TEAPOT_GUI_MUG = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_mug"));
    public static final TagKey<Item> TEAPOT_GUI_MUG_APRICOT_KERNEL = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_mug_apricotkernel"));
    public static final TagKey<Item> TEAPOT_GUI_MUG_BLACK_TEA = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_mug_blacktea"));
    public static final TagKey<Item> TEAPOT_GUI_MUG_COCOA = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_mug_cocoa"));
    public static final TagKey<Item> TEAPOT_GUI_MUG_COFFEE = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_mug_coffee"));
    public static final TagKey<Item> TEAPOT_GUI_MUG_GREEN_TEA = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_mug_greentea"));
    public static final TagKey<Item> TEAPOT_GUI_MUG_MATCHA = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_mug_matcha"));
    //    public static final TagKey<Item> TEAPOT_GUI_GLASS_BOTTLE = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_glass_bottle"));
    public static final TagKey<Item> TEAPOT_GUI_GLASS_BOTTLE_MILK_TEA = ItemTags.create(getManorsBountyResourceLocation("teapot_gui_glass_bottle_milktea"));
    public static final TagKey<Item> CATALYSTS = ItemTags.create(getManorsBountyResourceLocation("catalysts"));
    public static final TagKey<Item> CATALYSTS_A = ItemTags.create(getManorsBountyResourceLocation("catalyst_type_a"));
    public static final TagKey<Item> CATALYSTS_B = ItemTags.create(getManorsBountyResourceLocation("catalyst_type_b"));
    public static final TagKey<Item> CATALYSTS_C = ItemTags.create(getManorsBountyResourceLocation("catalyst_type_c"));
    public static final TagKey<Item> CATALYSTS_X = ItemTags.create(getManorsBountyResourceLocation("catalyst_type_x"));
    public static final TagKey<Item> KNIVES = ItemTags.create(getForgeResourceLocation("tools/knives"));
    public static final TagKey<Item> ROLLING_PINS = ItemTags.create(getForgeResourceLocation("tools/rolling_pins"));

    public static final TagKey<Block> TEAPOT_HEAT_BLOCKS = BlockTags.create(getManorsBountyResourceLocation("teapot_heat_blocks"));
    public static final TagKey<Block> STOCK_POT_HEAT_BLOCKS = BlockTags.create(getManorsBountyResourceLocation("stock_pot_heat_blocks"));
    public static final TagKey<Block> SAUCEPAN_AND_WHISK_HEAT_BLOCKS = BlockTags.create(getManorsBountyResourceLocation("saucepan_and_whisk_heat_blocks"));

    public static final Map<String, String> STRING_MAP = Map.of(
            "olive_oil_bucket", "olive_oil",
            "vanilla_ice_cream_fluid_bucket", "vanilla_ice_cream_fluid",
            "blueberry_ice_cream_fluid_bucket", "blueberry_ice_cream_fluid",
            "cherries_ice_cream_fluid_bucket", "cherries_ice_cream_fluid",
            "starfruit_ice_cream_fluid_bucket", "starfruit_ice_cream_fluid",
            "chocolate_ice_cream_fluid_bucket", "chocolate_ice_cream_fluid",
            "jalapeno_ice_cream_fluid_bucket", "jalapeno_ice_cream_fluid"
    );

    public static final List<Fluid> OIL_FLUIDS = new ArrayList<>();

    public static final Map<Item, Fluid> BUCKET_FLUID_MAP = new HashMap<>();

    static {
        STRING_MAP.forEach((itemName, fluidName) -> {
            Item item = getManorsBountyItem(itemName);
            Fluid fluid = getManorBountyFluid(fluidName);
            if (item != Items.AIR && fluid != Fluids.EMPTY) {
                if (fluidName.equals("olive_oil")) {
                    OIL_FLUIDS.add(fluid);
                }
                BUCKET_FLUID_MAP.put(item, fluid);
            }
        });
    }

    public static ResourceLocation getManorsBountyResourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static ResourceLocation getForgeResourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath("forge", name);
    }

    public static Item getManorsBountyItem(String name) {
        return ForgeRegistries.ITEMS.getValue(getManorsBountyResourceLocation(name));
    }

    public static Fluid getManorBountyFluid(String name) {
        return ForgeRegistries.FLUIDS.getValue(getManorsBountyResourceLocation(name));
    }

    public static boolean isIceCreamAndCone(ItemStack itemStack) {
        return itemStack.is(ICE_CREAM_AND_CONE) || SlotInputLimitManager.ICE_CREAM_MACHINE_INPUT_LIMIT.stream().anyMatch(ingredient -> ingredient.test(itemStack));
    }

    public static boolean isDamageableMaterial(ItemStack itemStack) {
        return itemStack.is(DAMAGEABLE_MATERIAL);
    }

    public static boolean isTeapotGuiGlassBottle(ItemStack itemStack) {
        Item item = getManorsBountyItem("deformed_glass_bottle");
        if (item == Items.AIR) {
            return false;
        }
        return itemStack.is(item) || itemStack.getCraftingRemainingItem().is(item);
//        return itemStack.is(TEAPOT_GUI_GLASS_BOTTLE);
    }

    public static boolean isTeapotGuiMug(ItemStack itemStack) {
        Item item = getManorsBountyItem("reactive_glaze_mug");
        if (item == Items.AIR) {
            return false;
        }
        return itemStack.is(item) || itemStack.getCraftingRemainingItem().is(item);
//        return itemStack.is(TEAPOT_GUI_MUG);
    }

    public static boolean isTeapotGuiGlassBottleMilkTea(ItemStack itemStack) {
        return itemStack.is(TEAPOT_GUI_GLASS_BOTTLE_MILK_TEA);
    }

    public static boolean isTeapotGuiMugApricotKernel(ItemStack itemStack) {
        return itemStack.is(TEAPOT_GUI_MUG_APRICOT_KERNEL);
    }

    public static boolean isTeapotGuiMugBlackTea(ItemStack itemStack) {
        return itemStack.is(TEAPOT_GUI_MUG_BLACK_TEA);
    }

    public static boolean isTeapotGuiMugCocoa(ItemStack itemStack) {
        return itemStack.is(TEAPOT_GUI_MUG_COCOA);
    }

    public static boolean isTeapotGuiMugCoffee(ItemStack itemStack) {
        return itemStack.is(TEAPOT_GUI_MUG_COFFEE);
    }

    public static boolean isTeapotGuiMugGreenTea(ItemStack itemStack) {
        return itemStack.is(TEAPOT_GUI_MUG_GREEN_TEA);
    }

    public static boolean isTeapotGuiMugMatcha(ItemStack itemStack) {
        return itemStack.is(TEAPOT_GUI_MUG_MATCHA);
    }

    public static boolean isCatalysts(ItemStack itemStack) {
        return itemStack.is(CATALYSTS);
    }

    public static boolean isCatalystsA(ItemStack itemStack) {
        return itemStack.is(CATALYSTS_A);
    }

    public static boolean isCatalystsB(ItemStack itemStack) {
        return itemStack.is(CATALYSTS_B);
    }

    public static boolean isCatalystsC(ItemStack itemStack) {
        return itemStack.is(CATALYSTS_C);
    }

    public static boolean isCatalystsX(ItemStack itemStack) {
        return itemStack.is(CATALYSTS_X);
    }

    public static ResourceLocation getTeapotGuiTexture(TagKey<Item> tag) {
        return tag.location().withPrefix("textures/gui/teapot_gui/").withSuffix(".png");
    }

    public static ResourceLocation getTeapotGuiTexture(String modid, TagKey<Item> tag) {
        return ResourceLocation.fromNamespaceAndPath(modid, getTeapotGuiTexture(tag).getPath());
    }

    public static ResourceLocation getTeapotGuiGlassBottleMilkTeaTexture() {
        return getTeapotGuiTexture(ManorsBountyMachine.MOD_ID, TEAPOT_GUI_GLASS_BOTTLE_MILK_TEA);
    }

    public static ResourceLocation getTeapotGuiMugApricotKernelTexture() {
        return getTeapotGuiTexture(ManorsBountyMachine.MOD_ID, TEAPOT_GUI_MUG_APRICOT_KERNEL);
    }

    public static ResourceLocation getTeapotGuiMugBlackTeaTexture() {
        return getTeapotGuiTexture(ManorsBountyMachine.MOD_ID, TEAPOT_GUI_MUG_BLACK_TEA);
    }

    public static ResourceLocation getTeapotGuiMugCocoaTexture() {
        return getTeapotGuiTexture(ManorsBountyMachine.MOD_ID, TEAPOT_GUI_MUG_COCOA);
    }

    public static ResourceLocation getTeapotGuiMugCoffeeTexture() {
        return getTeapotGuiTexture(ManorsBountyMachine.MOD_ID, TEAPOT_GUI_MUG_COFFEE);
    }

    public static ResourceLocation getTeapotGuiMugGreenTeaTexture() {
        return getTeapotGuiTexture(ManorsBountyMachine.MOD_ID, TEAPOT_GUI_MUG_GREEN_TEA);
    }

    public static ResourceLocation getTeapotGuiMugMatchaTexture() {
        return getTeapotGuiTexture(ManorsBountyMachine.MOD_ID, TEAPOT_GUI_MUG_MATCHA);
    }

    public static boolean isIceCreamCone(ItemStack itemStack) {
        Item item = getManorsBountyItem("ice_cream_cone");
        if (item == Items.AIR) {
            return false;
        }
        return itemStack.is(item);
    }

    public static boolean isBottledWater(ItemStack itemStack) {
        Item item = getManorsBountyItem("bottled_water");
        if (item == Items.AIR) {
            return false;
        }
        return itemStack.is(item);
    }

    public static boolean isBoxedMilk(ItemStack itemStack) {
        Item item = getManorsBountyItem("boxed_milk");
        if (item == Items.AIR) {
            return false;
        }
        return itemStack.is(item);
    }

    public static boolean isKnife(ItemStack itemStack) {
        return itemStack.is(KNIVES);
    }

    public static boolean isRollingPin(ItemStack itemStack) {
        return itemStack.is(ROLLING_PINS);
    }

    public static boolean isCookTopRunningOrTrue(BlockState blockState) {
        return blockState.hasProperty(CooktopBlock.RUNNING) ? blockState.getValue(CooktopBlock.RUNNING) : true;
    }

    public static boolean isOnTeapotHeatBlock(BlockState blockState) {
        return blockState.is(TEAPOT_HEAT_BLOCKS) && isCookTopRunningOrTrue(blockState) || blockState.is(Blocks.MAGMA_BLOCK);
    }

    public static boolean isStockPotHeatBlock(BlockState blockState) {
        return blockState.is(STOCK_POT_HEAT_BLOCKS) && isCookTopRunningOrTrue(blockState) || blockState.is(Blocks.MAGMA_BLOCK);
    }

    public static boolean isSaucepanAndWhiskHeatBlock(BlockState blockState) {
        return blockState.is(SAUCEPAN_AND_WHISK_HEAT_BLOCKS) && isCookTopRunningOrTrue(blockState) || blockState.is(Blocks.MAGMA_BLOCK);
    }

    public static boolean isOilFluid(FluidStack fluidStack) {
        return OIL_FLUIDS.contains(fluidStack.getFluid());
    }

    public static boolean isIceCreamFluid(FluidStack fluidStack) {
//        return SlotInputLimitManager.ICE_CREAM_MACHINE_FLUID_LIMIT.stream().anyMatch(fluid -> fluid.isSame(fluidStack.getFluid()));
        return true;
    }

    public static void damageItem(ItemStack stack, Level level) {
        damageItem(1, stack, level);
    }

    public static void damageItem(int amount, ItemStack stack, Level level) {
        if (level.isClientSide()) return;
        if (stack.hurt(amount, level.random, null)) {
            stack.shrink(1);
            stack.setDamageValue(0);
        }
    }
}
