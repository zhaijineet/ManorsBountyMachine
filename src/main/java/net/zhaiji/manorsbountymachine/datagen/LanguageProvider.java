package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.client.screen.IceCreamMachineScreen;
import net.zhaiji.manorsbountymachine.client.screen.TeapotScreen;
import net.zhaiji.manorsbountymachine.compat.jei.category.*;
import net.zhaiji.manorsbountymachine.register.*;

import java.util.function.Supplier;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";

    public final String locale;

    public LanguageProvider(PackOutput output, String locale) {
        super(output, ManorsBountyMachine.MOD_ID, locale);
        this.locale = locale;
    }

    public void add(Supplier<VillagerProfession> villagerProfession, String value) {
        this.add("entity.minecraft.villager." + ManorsBountyMachine.MOD_ID + "." + villagerProfession.get().name(), value);
    }

    public void English_US() {
        add(InitCreativeModeTab.MANORS_BOUNTY_MACHINE_TAB_TRANSLATABLE, "Manor's Bounty Machine");

        add(InitBlock.ICE_CREAM_MACHINE.get(), "Ice Cream Machine");
        add(InitBlock.FRYER.get(), "Fryer");
        add(InitBlock.OVEN.get(), "Oven");
        add(InitBlock.TEAPOT.get(), "Teapot");
        add(InitBlock.FERMENTER.get(), "Fermenter");
        add(InitBlock.BLENDER.get(), "Blender");
        add(InitBlock.CUTTING_BOARD.get(), "Cutting Board");
        add(InitBlock.STOCK_POT.get(), "Stock Pot");
        add(InitBlock.COOKTOP.get(), "Cooktop");
        add(InitBlock.SAUCEPAN_AND_WHISK.get(), "Saucepan and Whisk");
        add(InitItem.SHAKER.get(), "Shaker");

        add(IceCreamMachineScreen.TWO_FLAVOR_SWITCH_TRANSLATABLE, "Two Flavor Switch");
        add(TeapotScreen.TRANSLATABLE, "Teapot need heat source (#manors_bounty:teapot_heat_blocks or minecraft:magma_block) to start working");

        add("gui.jei.category.recipe.chance", "%s%% Chance");

        add(IceCreamRecipeCategory.TRANSLATABLE, "Ice Cream Craft");
        add(FastFryRecipeCategory.TRANSLATABLE, "Fast Frying");
        add(SlowFryRecipeCategory.TRANSLATABLE, "Slow Frying");
        add(OvenRecipeCategory.TRANSLATABLE, "Bake");
        add(TeapotRecipeCategory.TRANSLATABLE, "Brewing");
        add(DimFermentationRecipeCategory.TRANSLATABLE, "Fermentation: Dim Environment");
        add(NormalFermentationRecipeCategory.TRANSLATABLE, "Fermentation: Normal Environment");
        add(BrightFermentationRecipeCategory.TRANSLATABLE, "Fermentation: Bright Environment");
        add(BlenderRecipeCategory.TRANSLATABLE, "Blender");
        add(CuttingBoardRecipeCategory.TRANSLATABLE, "Cutting Board");
        add(StockPotRecipeCategory.TRANSLATABLE, "Stewing");
        add(SaucepanAndWhiskRecipeCategory.TRANSLATABLE, "Stirs");
        add("gui.jei.category.recipe.saucepan_and_whisk.heat_check_0", "Need Heat");
        add("gui.jei.category.recipe.saucepan_and_whisk.heat_check_1", "No Need Heat");
        add("gui.jei.category.recipe.saucepan_and_whisk.heat_check_2", "No Check Heat");
        add(ShakerRecipeCategory.TRANSLATABLE, "Mix a Drink");

        add(InitSoundEvent.ICE_CREAM_MACHINE_CLANK_TRANSLATABLE, "Ice Cream Machine Clank");
        add(InitSoundEvent.FRYER_FRYING_TRANSLATABLE, "Fryer Frying");
        add(InitSoundEvent.OVEN_DING_TRANSLATABLE, "Oven Ding");
        add(InitSoundEvent.OVEN_RUNNING_TRANSLATABLE, "Oven Running");
        add(InitSoundEvent.TEAPOT_CUP_PLACE_TRANSLATABLE, "Teapot Cup Place");
        add(InitSoundEvent.TEAPOT_OPEN_TRANSLATABLE, "Teapot Open");
        add(InitSoundEvent.TEAPOT_CLOSE_TRANSLATABLE, "Teapot Close");
        add(InitSoundEvent.TEAPOT_RUNNING_TRANSLATABLE, "Teapot Running");
        add(InitSoundEvent.TEAPOT_DONE_TRANSLATABLE, "Teapot Done");
        add(InitSoundEvent.BLENDER_OPEN_TRANSLATABLE, "Blender Open");
        add(InitSoundEvent.BLENDER_RUNNING_TRANSLATABLE, "Blender：Running");
        add(InitSoundEvent.BLENDER_WATER_DONE_TRANSLATABLE, "Blender：Done");
        add(InitSoundEvent.BLENDER_POWDER_DONE_TRANSLATABLE, "Blender：Done");
        add(InitSoundEvent.CUTTING_BOARD_CUTTING_TRANSLATABLE, "Cutting Board Cutting");
        add(InitSoundEvent.CUTTING_BOARD_ROLL_OUT_TRANSLATABLE, "Cutting Board Roll Out");
        add(InitSoundEvent.CUTTING_BOARD_CRAFTING_TRANSLATABLE, "Cutting Board Crafting");
        add(InitSoundEvent.STOCK_POT_COVER_MOVING_TRANSLATABLE, "Pot Cover Moving");
        add(InitSoundEvent.STOCK_POT_RUNNING_TRANSLATABLE, "Stock Pot Running");
        add(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_1_TRANSLATABLE, "Whisk Stirs");
        add(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_2_TRANSLATABLE, "Whisk Stirs");
        add(InitSoundEvent.SHAKER_OPEN_TRANSLATABLE, "Shaker Open");
        add(InitSoundEvent.SHAKER_SHAKE_TRANSLATABLE, "Shaker Shake");

        add(InitVillager.CHEF, "Chef");
        add(InitVillager.BARTENDER, "Bartender");
        add(InitVillager.ORCHARDIST, "Orchardist");
    }

    public void Chinese_CN() {
        add(InitCreativeModeTab.MANORS_BOUNTY_MACHINE_TAB_TRANSLATABLE, "庄园馀事：机器");

        add(InitBlock.ICE_CREAM_MACHINE.get(), "冰淇淋机");
        add(InitBlock.FRYER.get(), "炸锅");
        add(InitBlock.OVEN.get(), "烤箱");
        add(InitBlock.TEAPOT.get(), "茶壶");
        add(InitBlock.FERMENTER.get(), "发酵桶");
        add(InitBlock.BLENDER.get(), "破壁机");
        add(InitBlock.CUTTING_BOARD.get(), "砧板");
        add(InitBlock.STOCK_POT.get(), "煮锅");
        add(InitBlock.SAUCEPAN_AND_WHISK.get(), "小奶锅套组");
        add(InitBlock.COOKTOP.get(), "炉灶");
        add(InitItem.SHAKER.get(), "雪克壶");

        add(IceCreamMachineScreen.TWO_FLAVOR_SWITCH_TRANSLATABLE, "双拼开关");
        add(TeapotScreen.TRANSLATABLE, "茶壶需要热源(#manors_bounty:teapot_heat_blocks 或者 minecraft:magma_block)才能工作");

        add("gui.jei.category.recipe.chance", "%s%%的概率");

        add(IceCreamRecipeCategory.TRANSLATABLE, "冰淇淋制作");
        add(FastFryRecipeCategory.TRANSLATABLE, "快炸");
        add(SlowFryRecipeCategory.TRANSLATABLE, "慢炸");
        add(OvenRecipeCategory.TRANSLATABLE, "烘焙");
        add(TeapotRecipeCategory.TRANSLATABLE, "冲泡");
        add(DimFermentationRecipeCategory.TRANSLATABLE, "发酵：黑暗环境");
        add(NormalFermentationRecipeCategory.TRANSLATABLE, "发酵：阴凉环境");
        add(BrightFermentationRecipeCategory.TRANSLATABLE, "发酵：明亮环境");
        add(BlenderRecipeCategory.TRANSLATABLE, "破壁机");
        add(CuttingBoardRecipeCategory.TRANSLATABLE, "砧板");
        add(StockPotRecipeCategory.TRANSLATABLE, "炖煮");
        add(SaucepanAndWhiskRecipeCategory.TRANSLATABLE, "搅拌");
        add("gui.jei.category.recipe.saucepan_and_whisk.heat_check_0", "需要热源");
        add("gui.jei.category.recipe.saucepan_and_whisk.heat_check_1", "不需要热源");
        add("gui.jei.category.recipe.saucepan_and_whisk.heat_check_2", "不检测热源");
        add(ShakerRecipeCategory.TRANSLATABLE, "调制");

        add(InitSoundEvent.ICE_CREAM_MACHINE_CLANK_TRANSLATABLE, "冰淇淋机：哐当");
        add(InitSoundEvent.FRYER_FRYING_TRANSLATABLE, "炸锅：油炸");
        add(InitSoundEvent.OVEN_DING_TRANSLATABLE, "烤箱：叮");
        add(InitSoundEvent.OVEN_RUNNING_TRANSLATABLE, "烤箱：运行");
        add(InitSoundEvent.TEAPOT_CUP_PLACE_TRANSLATABLE, "茶壶：杯子放置");
        add(InitSoundEvent.TEAPOT_OPEN_TRANSLATABLE, "茶壶：揭盖");
        add(InitSoundEvent.TEAPOT_CLOSE_TRANSLATABLE, "茶壶：盖上");
        add(InitSoundEvent.TEAPOT_RUNNING_TRANSLATABLE, "茶壶：冲泡中");
        add(InitSoundEvent.TEAPOT_DONE_TRANSLATABLE, "茶壶：倒出");
        add(InitSoundEvent.BLENDER_OPEN_TRANSLATABLE, "破壁机：打开");
        add(InitSoundEvent.BLENDER_RUNNING_TRANSLATABLE, "破壁机：运行");
        add(InitSoundEvent.BLENDER_WATER_DONE_TRANSLATABLE, "破壁机：倒出");
        add(InitSoundEvent.BLENDER_POWDER_DONE_TRANSLATABLE, "破壁机：倒出");
        add(InitSoundEvent.CUTTING_BOARD_CUTTING_TRANSLATABLE, "砧板：切");
        add(InitSoundEvent.CUTTING_BOARD_ROLL_OUT_TRANSLATABLE, "砧板：碾平");
        add(InitSoundEvent.CUTTING_BOARD_CRAFTING_TRANSLATABLE, "砧板：制作");
        add(InitSoundEvent.STOCK_POT_COVER_MOVING_TRANSLATABLE, "锅盖：挪动");
        add(InitSoundEvent.STOCK_POT_RUNNING_TRANSLATABLE, "煮锅：烹饪");
        add(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_1_TRANSLATABLE, "小奶锅套组：搅拌");
        add(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_2_TRANSLATABLE, "小奶锅套组：搅拌");
        add(InitSoundEvent.SHAKER_OPEN_TRANSLATABLE, "雪克壶：打开");
        add(InitSoundEvent.SHAKER_SHAKE_TRANSLATABLE, "雪克壶：摇晃");

        add(InitVillager.CHEF, "厨师");
        add(InitVillager.BARTENDER, "调酒师");
        add(InitVillager.ORCHARDIST, "果农");
    }

    @Override
    protected void addTranslations() {
        switch (this.locale) {
            case EN_US -> this.English_US();
            case ZH_CN -> this.Chinese_CN();
        }
    }
}
