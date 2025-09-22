package net.zhaiji.manorsbountymachine.compat.jei;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeOrderManager {
    public static int INDEX = 0;
    public static List<ResourceKey<CreativeModeTab>> TAB_KEY = new ArrayList<>();
    public static boolean INITIALIZE = false;
    public static Map<Item, Integer> ITEM_ORDER = new HashMap<>();
}
