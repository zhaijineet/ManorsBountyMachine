package net.zhaiji.manorsbountymachine.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zhaiji.manorsbountymachine.client.render.*;
import net.zhaiji.manorsbountymachine.client.screen.*;
import net.zhaiji.manorsbountymachine.compat.jei.ManorsBountyMachineJeiPlugin;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;
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

    public static void handlerRecipesUpdatedEvent(RecipesUpdatedEvent event) {
        RecipeManager recipeManager = event.getRecipeManager();
        SmokingRecipeManager.init(recipeManager);
        SlotInputLimitManager.init(recipeManager);
    }

    public static void handlerBuildCreativeModeTabContentsEvent(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if (!ManorsBountyMachineJeiPlugin.TAB_KEY.contains(tabKey)) {
            ManorsBountyMachineJeiPlugin.TAB_KEY.add(tabKey);
        } else {
            ManorsBountyMachineJeiPlugin.INITIALIZE = true;
        }
        if (!ManorsBountyMachineJeiPlugin.INITIALIZE) {
            event.getEntries().forEach(entry -> {
                ItemStack itemStack = entry.getKey();
                if (!ManorsBountyMachineJeiPlugin.ITEM_ORDER.containsKey(itemStack.getItem())) {
                    ManorsBountyMachineJeiPlugin.ITEM_ORDER.put(itemStack.getItem(), ManorsBountyMachineJeiPlugin.INDEX);
                    ManorsBountyMachineJeiPlugin.INDEX++;
                }
            });
        }
    }
}
