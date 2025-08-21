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

    public static final RegistryObject<Item> SHAKER = ITEM.register(
            "shaker",
            ShakerItem::new
    );
}
