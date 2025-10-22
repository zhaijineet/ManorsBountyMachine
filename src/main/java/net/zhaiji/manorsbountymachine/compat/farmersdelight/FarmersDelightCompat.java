package net.zhaiji.manorsbountymachine.compat.farmersdelight;

import net.minecraftforge.fml.ModList;

public class FarmersDelightCompat {
    public static final String MOD_ID = "farmersdelight";

    public static boolean isLoad(){
        return ModList.get().isLoaded(FarmersDelightCompat.MOD_ID);
    }
}
