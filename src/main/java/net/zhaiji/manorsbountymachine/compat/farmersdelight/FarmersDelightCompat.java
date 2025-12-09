package net.zhaiji.manorsbountymachine.compat.farmersdelight;

import net.minecraftforge.fml.ModList;
import net.zhaiji.manorsbountymachine.ManorsBountyMachineConfig;

public class FarmersDelightCompat {
    public static final String MOD_ID = "farmersdelight";

    public static boolean isLoad(){
        return ModList.get().isLoaded(FarmersDelightCompat.MOD_ID);
    }

    public static boolean canCompat(){
        return ManorsBountyMachineConfig.farmers_delight_cutting_recipe_compat;
    }
}
