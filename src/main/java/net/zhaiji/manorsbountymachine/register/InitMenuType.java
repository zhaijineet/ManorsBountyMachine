package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.menu.*;

public class InitMenuType {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, ManorsBountyMachine.MOD_ID);

    public static final RegistryObject<MenuType<IceCreamMachineMenu>> ICE_CREAM_MACHINE_MENU = MENU_TYPE.register(
            "ice_cream_machine_menu",
            () -> IForgeMenuType.create((IceCreamMachineMenu::new))
    );

    public static final RegistryObject<MenuType<FryerMenu>> FRYER_MENU = MENU_TYPE.register(
            "fryer",
            () -> IForgeMenuType.create((FryerMenu::new))
    );

    public static final RegistryObject<MenuType<OvenMenu>> OVEN_MENU = MENU_TYPE.register(
            "oven",
            () -> IForgeMenuType.create((OvenMenu::new))
    );

    public static final RegistryObject<MenuType<TeapotMenu>> TEAPOT_MENU = MENU_TYPE.register(
            "teapot",
            () -> IForgeMenuType.create((TeapotMenu::new))
    );

    public static final RegistryObject<MenuType<FermenterMenu>> FERMENTER_MENU = MENU_TYPE.register(
            "fermenter",
            () -> IForgeMenuType.create((FermenterMenu::new))
    );

    public static final RegistryObject<MenuType<BlenderMenu>> BLENDER = MENU_TYPE.register(
            "blender",
            () -> IForgeMenuType.create((BlenderMenu::new))
    );

    public static final RegistryObject<MenuType<ShakerMenu>> SHAKER_MENU = MENU_TYPE.register(
            "shaker",
            () -> new MenuType<>(ShakerMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );
}
