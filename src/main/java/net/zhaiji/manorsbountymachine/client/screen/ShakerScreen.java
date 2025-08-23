package net.zhaiji.manorsbountymachine.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.menu.ShakerMenu;

@OnlyIn(Dist.CLIENT)
public class ShakerScreen extends AbstractMachineScreen<ShakerMenu> {
    public static final ResourceLocation SHAKER_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/shaker_gui.png");

    public ShakerScreen(ShakerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, SHAKER_GUI);
    }
}
