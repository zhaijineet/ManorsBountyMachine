package net.zhaiji.manorsbountymachine.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.menu.FermenterMenu;

public class FermenterScreen extends AbstractMachineScreen<FermenterMenu> {
    public static final ResourceLocation FERMENTER_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/fermenter_gui.png");

    public FermenterScreen(FermenterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, FERMENTER_GUI);
    }
}
