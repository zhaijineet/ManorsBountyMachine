package net.zhaiji.manorsbountymachine.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zhaiji.manorsbountymachine.client.render.CuttingBoardBlockEntityRenderer;
import net.zhaiji.manorsbountymachine.client.render.FryerBlockEntityRenderer;
import net.zhaiji.manorsbountymachine.client.render.IceCreamMachineBlockEntityRenderer;
import net.zhaiji.manorsbountymachine.client.render.OvenBlockEntityRenderer;
import net.zhaiji.manorsbountymachine.client.screen.*;
import net.zhaiji.manorsbountymachine.register.InitBlockEntityType;
import net.zhaiji.manorsbountymachine.register.InitMenuType;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    public static void handlerFMLClientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(InitMenuType.ICE_CREAM_MACHINE_MENU.get(), IceCreamMachineScreen::new);
            MenuScreens.register(InitMenuType.FRYER_MENU.get(), FryerScreen::new);
            MenuScreens.register(InitMenuType.OVEN_MENU.get(), OvenScreen::new);
            MenuScreens.register(InitMenuType.TEAPOT_MENU.get(), TeapotScreen::new);
            MenuScreens.register(InitMenuType.FERMENTER_MENU.get(), FermenterScreen::new);
            MenuScreens.register(InitMenuType.BLENDER.get(), BlenderScreen::new);
            MenuScreens.register(InitMenuType.SHAKER_MENU.get(), ShakerScreen::new);
        });
    }

    public static void handlerEntityRenderersEvent$RegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(InitBlockEntityType.ICE_CREAM_MACHINE.get(), IceCreamMachineBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.FRYER.get(), FryerBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.OVEN.get(), OvenBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(InitBlockEntityType.CUTTING_BOARD.get(), CuttingBoardBlockEntityRenderer::new);
    }
}
