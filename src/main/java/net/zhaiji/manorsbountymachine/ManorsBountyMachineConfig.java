package net.zhaiji.manorsbountymachine;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ManorsBountyMachineConfig {
    public static boolean farmers_delight_cutting_board_compat;
    public static boolean farmers_delight_cooking_pot_compat;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Boolean> FARMERS_DELIGHT_CUTTING_BOARD_COMPAT = BUILDER
        .comment("Switch Farmers Delight Cutting Board Recipe Compat")
        .define("cutting_board_compat", true);

    private static final ForgeConfigSpec.ConfigValue<Boolean> FARMERS_DELIGHT_COOKING_POT_COMPAT = BUILDER
        .comment("Switch Farmers Delight Cooking Pot Recipe Compat")
        .define("cooking_pot_compat", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static void handlerModConfigEvent(ModConfigEvent event) {
        farmers_delight_cutting_board_compat = FARMERS_DELIGHT_CUTTING_BOARD_COMPAT.get();
        farmers_delight_cooking_pot_compat = FARMERS_DELIGHT_COOKING_POT_COMPAT.get();
    }
}
