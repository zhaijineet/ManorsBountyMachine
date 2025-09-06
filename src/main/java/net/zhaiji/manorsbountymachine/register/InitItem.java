package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.item.ShakerItem;

public class InitItem {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(Registries.ITEM, ManorsBountyMachine.MOD_ID);

    public static final RegistryObject<Item> ICE_CREAM_MACHINE = ITEM.register(
            "ice_cream_machine",
            () -> new BlockItem(InitBlock.ICE_CREAM_MACHINE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> FRYER = ITEM.register(
            "fryer",
            () -> new BlockItem(InitBlock.FRYER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> OVEN = ITEM.register(
            "oven",
            () -> new BlockItem(InitBlock.OVEN.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> TEAPOT = ITEM.register(
            "teapot",
            () -> new BlockItem(InitBlock.TEAPOT.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> FERMENTER = ITEM.register(
            "fermenter",
            () -> new BlockItem(InitBlock.FERMENTER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> BLENDER = ITEM.register(
            "blender",
            () -> new BlockItem(InitBlock.BLENDER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> CUTTING_BOARD = ITEM.register(
            "cutting_board",
            () -> new BlockItem(InitBlock.CUTTING_BOARD.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> STOCK_POT = ITEM.register(
            "stock_pot",
            () -> new BlockItem(InitBlock.STOCK_POT.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> SHAKER = ITEM.register(
            "shaker",
            ShakerItem::new
    );
}
