package net.zhaiji.manorsbountymachine;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ManorsBountyMachine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ManorsBountyMachineConfig {
    public static boolean farmers_delight_cutting_recipe_compat;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Boolean> FARMERS_DELIGHT_CUTTING_RECIPE_COMPAT = BUILDER
            .comment("Switch Farmers Delight Cutting Recipe Compat")
            .define("compat", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec().equals(SPEC)) {
            farmers_delight_cutting_recipe_compat = FARMERS_DELIGHT_CUTTING_RECIPE_COMPAT.get();
        }
    }
}
