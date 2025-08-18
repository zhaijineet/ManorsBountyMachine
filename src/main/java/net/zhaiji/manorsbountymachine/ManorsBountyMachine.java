package net.zhaiji.manorsbountymachine;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zhaiji.manorsbountymachine.event.CommonEventManager;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.register.*;

@Mod(ManorsBountyMachine.MOD_ID)
public class ManorsBountyMachine {
    public static final String MOD_ID = "manors_bounty_machine";

    public ManorsBountyMachine(FMLJavaModLoadingContext context) {
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        IEventBus modEventBus = context.getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        CommonEventManager.init(modEventBus, forgeEventBus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ManorsBountyMachineClient.init(modEventBus, forgeEventBus));
        InitItem.ITEM.register(modEventBus);
        InitBlock.BLOCK.register(modEventBus);
        InitCreativeModeTab.CREATIVE_MODE_TAB.register(modEventBus);
        InitBlockEntityType.BLOCK_ENTITY_TYPE.register(modEventBus);
        InitMenuType.MENU_TYPE.register(modEventBus);
        InitRecipe.RECIPE_TYPE.register(modEventBus);
        InitRecipe.RECIPE_SERIALIZER.register(modEventBus);

        ManorsBountyMachinePacket.register();
    }
}
