package net.zhaiji.manorsbountymachine.compat.manors_bounty;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManorsBountyCompat {
    public static final String MOD_ID = "manors_bounty";

    public static final TagKey<Item> DAMAGEABLE_MATERIAL = ItemTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, "damageable_material"));

    public static boolean isDamageableMaterial(ItemStack itemStack) {
        return itemStack.is(DAMAGEABLE_MATERIAL);
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
