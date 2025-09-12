package net.zhaiji.manorsbountymachine.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zhaiji.manorsbountymachine.client.render.*;
import net.zhaiji.manorsbountymachine.client.screen.*;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void handlerFMLClientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(InitMenuType.ICE_CREAM_MACHINE.get(), IceCreamMachineScreen::new);
            MenuScreens.register(InitMenuType.FRYER.get(), FryerScreen::new);
            MenuScreens.register(InitMenuType.OVEN.get(), OvenScreen::new);
            MenuScreens.register(InitMenuType.TEAPOT.get(), TeapotScreen::new);
            MenuScreens.register(InitMenuType.FERMENTER.get(), FermenterScreen::new);
            MenuScreens.register(InitMenuType.BLENDER.get(), BlenderScreen::new);
            MenuScreens.register(InitMenuType.STOCK_POT.get(), StockPotScreen::new);
            MenuScreens.register(InitMenuType.SAUCEPAN_AND_WHISK.get(), SaucepanAndWhiskScreen::new);
            MenuScreens.register(InitMenuType.SHAKER.get(), ShakerScreen::new);
        });
    }

    public static void handlerEntityRenderersEvent$RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(InitBlockEntityType.ICE_CREAM_MACHINE.get(), IceCreamMachineBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.FRYER.get(), FryerBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.OVEN.get(), OvenBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.BLENDER.get(), BlenderBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.CUTTING_BOARD.get(), CuttingBoardBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.SAUCEPAN_AND_WHISK.get(), SaucepanAndWhiskBlockEntityRenderer::new);
    }
}
