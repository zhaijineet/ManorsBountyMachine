package net.zhaiji.manorsbountymachine.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;
import net.zhaiji.manorsbountymachine.register.InitBlock;
import net.zhaiji.manorsbountymachine.register.InitItem;
import net.zhaiji.manorsbountymachine.register.InitVillager;

import java.util.List;
import java.util.Map;

public class CommonEventHandler {
    public static void handlerAddReloadListenerEvent(AddReloadListenerEvent event) {
        SmokingRecipeManager.needInit = true;
        SlotInputLimitManager.needInit = true;
    }

    public static void handlerMissingMappingsEvent(MissingMappingsEvent event) {
        Map<ResourceLocation, Item> items = Map.of(
                ManorsBountyCompat.getManorsBountyResourceLocation("ice_cream_machine"), InitItem.ICE_CREAM_MACHINE.get(),
                ManorsBountyCompat.getManorsBountyResourceLocation("fryer"), InitItem.FRYER.get(),
                ManorsBountyCompat.getManorsBountyResourceLocation("oven"), InitItem.OVEN.get()
        );
        Map<ResourceLocation, Block> blocks = Map.of(
                ManorsBountyCompat.getManorsBountyResourceLocation("ice_cream_machine"), InitBlock.ICE_CREAM_MACHINE.get(),
                ManorsBountyCompat.getManorsBountyResourceLocation("fryer"), InitBlock.FRYER.get(),
                ManorsBountyCompat.getManorsBountyResourceLocation("oven"), InitBlock.OVEN.get()
        );
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getAllMappings(ForgeRegistries.ITEMS.getRegistryKey())) {
            ResourceLocation key = mapping.getKey();
            if (items.containsKey(key)) {
                mapping.remap(items.get(key));
            }
        }
        for (MissingMappingsEvent.Mapping<Block> mapping : event.getAllMappings(ForgeRegistries.BLOCKS.getRegistryKey())) {
            ResourceLocation key = mapping.getKey();
            if (blocks.containsKey(key)) {
                mapping.remap(blocks.get(key));
            }
        }
    }

    public static void handlerVillagerTradesEvent(VillagerTradesEvent event) {
        if (event.getType() == InitVillager.ORCHARDIST.get()) {
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("orange_seed"),
                    emerald(5),
                    ManorsBountyCompat.getManorsBountyItemStack("lemon_seed")
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("banana_frond"),
                    emerald()
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("strawberry", 12),
                    emerald()
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("dragon_fruit_cactus_flower"),
                    emerald()
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("purple_sugar_cane", 16),
                    emerald()
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("lemon", 20),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal", 5),
                    ManorsBountyCompat.getManorsBountyItemStack("lime", 1)
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("fig", 12),
                    emerald()
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("pineapple", 3),
                    emerald()
            );
            journeymanTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet", 5),
                    InitItem.BLENDER.get().getDefaultInstance()
            );
            journeymanTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("golden_apple_slice", 6),
                    ManorsBountyCompat.getManorsBountyItemStack("topaz", 8),
                    ManorsBountyCompat.getManorsBountyItemStack("enchanted_golden_apple_slice")
            );
            expertTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("dragon_fruit", 2),
                    emerald()
            );
            expertTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("peach", 24),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet", 5)
            );
            masterTrades(
                    event.getTrades(),
                    Items.APPLE.getDefaultInstance(),
                    emerald(24),
                    ManorsBountyCompat.getManorsBountyItemStack("green_apple", 5)
            );
            masterTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("orange"),
                    ManorsBountyCompat.getManorsBountyItemStack("topaz", 18),
                    ManorsBountyCompat.getManorsBountyItemStack("grapefruit")
            );
            masterTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("strawberry"),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal", 5),
                    ManorsBountyCompat.getManorsBountyItemStack("pineberry")
            );
        }
        if (event.getType() == InitVillager.CHEF.get()) {
            noviceTrades(
                    event.getTrades(),
                    emerald(5),
                    ManorsBountyCompat.getManorsBountyItemStack("miso_soup")
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet", 5),
                    ManorsBountyCompat.getManorsBountyItemStack("cherries_pie")
            );
            noviceTrades(
                    event.getTrades(),
                    emerald(5),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet"),
                    ManorsBountyCompat.getManorsBountyItemStack("corn", 5)
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("fried_chicken", 3),
                    emerald(1)
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("fried_steak", 3),
                    emerald(1)
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal", 5),
                    InitItem.ICE_CREAM_MACHINE.get().getDefaultInstance()
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("topaz", 18),
                    InitItem.OVEN.get().getDefaultInstance()
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet", 12),
                    InitItem.FRYER.get().getDefaultInstance()
            );
            journeymanTrades(
                    event.getTrades(),
                    emerald(4),
                    ManorsBountyCompat.getManorsBountyItemStack("croissant")
            );
            journeymanTrades(
                    event.getTrades(),
                    emerald(5),
                    ManorsBountyCompat.getManorsBountyItemStack("yogurt")
            );
            journeymanTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("vanilla_chocolate_ice_cream", 3),
                    emerald(10)
            );
            journeymanTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("baguette", 3),
                    emerald(7)
            );
            journeymanTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("baguette", 3),
                    emerald(7)
            );
            expertTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("topaz", 5),
                    Items.GOLDEN_APPLE.getDefaultInstance()
            );
            expertTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("topaz", 5),
                    ManorsBountyCompat.getManorsBountyItemStack("mozzarella_cheese_slice")
            );
            masterTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("colorful_fruit_parfait"),
                    emerald(20)
            );
            masterTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("pudding_a_la_mode"),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal")
            );
        }
        if (event.getType() == InitVillager.BARTENDER.get()) {
            noviceTrades(
                    event.getTrades(),
                    emerald(5),
                    InitItem.FERMENTER.get().getDefaultInstance()
            );
            noviceTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("red_wine"),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet")
            );
            noviceTrades(
                    event.getTrades(),
                    emerald(10),
                    ManorsBountyCompat.getManorsBountyItemStack("glass_of_whiskey")
            );
            noviceTrades(
                    event.getTrades(),
                    emerald(10),
                    ManorsBountyCompat.getManorsBountyItemStack("glass_of_rum")
            );
            noviceTrades(
                    event.getTrades(),
                    emerald(10),
                    ManorsBountyCompat.getManorsBountyItemStack("glass_of_vodka")
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("hurricane_cocktail"),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet", 5)
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("garnet", 3),
                    ManorsBountyCompat.getManorsBountyItemStack("grenadine")
            );
            apprenticeTrades(
                    event.getTrades(),
                    emerald(15),
                    ManorsBountyCompat.getManorsBountyItemStack("daiquiri")
            );
            apprenticeTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal", 5),
                    ManorsBountyCompat.getManorsBountyItemStack("gin")
            );
            journeymanTrades(
                    event.getTrades(),
                    emerald(32),
                    ManorsBountyCompat.getManorsBountyItemStack("topaz", 16),
                    ManorsBountyCompat.getManorsBountyItemStack("champagne")
            );
            journeymanTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("beillni_base"),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal", 12)
            );
            journeymanTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("irish_coffee"),
                    ManorsBountyCompat.getManorsBountyItemStack("topaz", 16)
            );
            expertTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("creme_de_cassis"),
                    emerald(24)
            );
            expertTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("umeshu"),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal", 12)
            );
            expertTrades(
                    event.getTrades(),
                    emerald(32),
                    ManorsBountyCompat.getManorsBountyItemStack("sake")
            );
            masterTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("starfruit_wine"),
                    ManorsBountyCompat.getManorsBountyItemStack("brilliant_diamond")
            );
            masterTrades(
                    event.getTrades(),
                    ManorsBountyCompat.getManorsBountyItemStack("kir_royal"),
                    ManorsBountyCompat.getManorsBountyItemStack("pink_crystal", 16)
            );
            masterTrades(
                    event.getTrades(),
                    emerald(40),
                    ManorsBountyCompat.getManorsBountyItemStack("white_wine")
            );
        }
    }

    public static ItemStack emerald() {
        return emerald(1);
    }

    public static ItemStack emerald(int count) {
        return Items.EMERALD.getDefaultInstance().copyWithCount(count);
    }

    public static void noviceTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack costB, ItemStack result) {
        addTrades(trades, 1, costA, costB, result, 16, 2, 0.05F);
    }

    public static void noviceTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack result) {
        addTrades(trades, 1, costA, ItemStack.EMPTY, result, 16, 2, 0.05F);
    }

    public static void apprenticeTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack costB, ItemStack result) {
        addTrades(trades, 2, costA, costB, result, 16, 5, 0.05F);
    }

    public static void apprenticeTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack result) {
        addTrades(trades, 2, costA, ItemStack.EMPTY, result, 16, 5, 0.05F);
    }

    public static void journeymanTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack costB, ItemStack result) {
        addTrades(trades, 3, costA, costB, result, 12, 20, 0.05F);
    }

    public static void journeymanTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack result) {
        addTrades(trades, 3, costA, ItemStack.EMPTY, result, 12, 20, 0.05F);
    }

    public static void expertTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack costB, ItemStack result) {
        addTrades(trades, 4, costA, costB, result, 12, 20, 0.05F);
    }

    public static void expertTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack result) {
        addTrades(trades, 4, costA, ItemStack.EMPTY, result, 12, 20, 0.05F);
    }

    public static void masterTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack costB, ItemStack result) {
        addTrades(trades, 5, costA, costB, result, 12, 20, 0.05F);
    }

    public static void masterTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, ItemStack costA, ItemStack result) {
        addTrades(trades, 5, costA, ItemStack.EMPTY, result, 12, 20, 0.05F);
    }

    public static void addTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level, ItemStack costA, ItemStack costB, ItemStack result, int maxUses, int xp, float priceMultiplier) {
        trades.get(level).add((villager, randomSource) -> {
            if (!costB.isEmpty()) {
                return new MerchantOffer(costA, costB, result, maxUses, xp, priceMultiplier);
            } else {
                return new MerchantOffer(costA, result, maxUses, xp, priceMultiplier);
            }
        });
    }
}
