package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.data.PackOutput;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.client.screen.IceCreamMachineScreen;
import net.zhaiji.manorsbountymachine.compat.jei.category.*;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitCreativeModeTab;
import net.zhaiji.manorsbountymachine.register.InitItem;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";

    public final String locale;

    public LanguageProvider(PackOutput output, String locale) {
        super(output, ManorsBountyMachine.MOD_ID, locale);
        this.locale = locale;
    }

    public void English_US() {
        add(InitCreativeModeTab.MANORS_BOUNTY_MACHINE_TAB_TRANSLATABLE, "Manor's Bounty Machine");

        add(InitBlock.ICE_CREAM_MACHINE.get(), "Ice Cream Machine");
        add(InitBlock.FRYER.get(), "Fryer");
        add(InitBlock.OVEN.get(), "Oven");
        add(InitItem.SHAKER.get(), "Shaker");

        add(IceCreamMachineScreen.TWO_FLAVOR_SWITCH_TRANSLATABLE, "Two Flavor Switch");

        add(IceCreamRecipeCategory.TRANSLATABLE, "Ice Cream Craft");
        add(FastFryRecipeCategory.TRANSLATABLE, "Fast Frying");
        add(SlowFryRecipeCategory.TRANSLATABLE, "Slow Frying");
        add(OvenRecipeCategory.TRANSLATABLE, "Bake");
        add(ShakerRecipeCategory.TRANSLATABLE, "Mix a Drink");

        add(InitSoundEvent.ICE_CREAM_MACHINE_CLANK_TRANSLATABLE, "Ice Cream Machine Clank");
        add(InitSoundEvent.FRYER_FRYING_TRANSLATABLE, "Fryer frying");
        add(InitSoundEvent.OVEN_DING_TRANSLATABLE, "Oven Ding");
        add(InitSoundEvent.OVEN_RUNNING_TRANSLATABLE, "Oven Running");
    }

    public void Chinese_CN() {
        add(InitCreativeModeTab.MANORS_BOUNTY_MACHINE_TAB_TRANSLATABLE, "庄园馀事：机器");

        add(InitBlock.ICE_CREAM_MACHINE.get(), "冰淇淋机");
        add(InitBlock.FRYER.get(), "炸锅");
        add(InitBlock.OVEN.get(), "烤箱");
        add(InitItem.SHAKER.get(), "雪克壶");

        add(IceCreamMachineScreen.TWO_FLAVOR_SWITCH_TRANSLATABLE, "双拼开关");

        add(IceCreamRecipeCategory.TRANSLATABLE, "冰淇淋制作");
        add(FastFryRecipeCategory.TRANSLATABLE, "快炸");
        add(SlowFryRecipeCategory.TRANSLATABLE, "慢炸");
        add(OvenRecipeCategory.TRANSLATABLE, "烘焙");
        add(ShakerRecipeCategory.TRANSLATABLE, "调制");

        add(InitSoundEvent.ICE_CREAM_MACHINE_CLANK_TRANSLATABLE, "冰淇淋机：哐当");
        add(InitSoundEvent.FRYER_FRYING_TRANSLATABLE, "炸锅：油炸");
        add(InitSoundEvent.OVEN_DING_TRANSLATABLE, "烤箱：叮");
        add(InitSoundEvent.OVEN_RUNNING_TRANSLATABLE, "烤箱：运行中");
    }

    @Override
    protected void addTranslations() {
        switch (this.locale) {
            case EN_US -> this.English_US();
            case ZH_CN -> this.Chinese_CN();
        }
    }
}
