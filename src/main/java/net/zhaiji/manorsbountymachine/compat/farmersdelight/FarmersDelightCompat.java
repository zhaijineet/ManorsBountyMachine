package net.zhaiji.manorsbountymachine.compat.farmersdelight;

import net.minecraftforge.fml.ModList;
import net.zhaiji.manorsbountymachine.ManorsBountyMachineConfig;

public class FarmersDelightCompat {
    public static final String MOD_ID = "farmersdelight";

    public static boolean isLoad(){
        return ModList.get().isLoaded(FarmersDelightCompat.MOD_ID);
    }

    public static boolean canCuttingBoardCompat(){
        return ManorsBountyMachineConfig.farmers_delight_cutting_board_compat;
    }

    public static boolean canCookingPotCompat(){
        return ManorsBountyMachineConfig.farmers_delight_cooking_pot_compat;
    }
}
