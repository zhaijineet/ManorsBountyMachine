package net.zhaiji.manorsbountymachine.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.StockPotBlockEntity;
import net.zhaiji.manorsbountymachine.menu.StockPotMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.StockPotStartPacket;

@OnlyIn(Dist.CLIENT)
public class StockPotScreen extends BaseMachineScreen<StockPotMenu> {
    public static final ResourceLocation STOCK_POT_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/stock_pot_gui.png");
    public static final ResourceLocation STOCK_POT_GUI_WIDGET = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/stock_pot_gui_widget.png");

    public static final int BUTTON_X_OFFSET = 129;
    public static final int BUTTON_Y_OFFSET = 0;
    public static final int BUTTON_WIDTH = 72;
    public static final int BUTTON_HEIGHT = 51;

    public static final int MAIN_INPUT_X_OFFSET = 26;
    public static final int MAIN_INPUT_Y_OFFSET = 0;
    public static final int MAIN_INPUT_WIDTH = 76;
    public static final int MAIN_INPUT_HEIGHT = 58;

    public static final int SECONDARY_INPUT_LEFT_X_OFFSET = 0;
    public static final int SECONDARY_INPUT_LEFT_Y_OFFSET = 0;
    public static final int SECONDARY_INPUT_LEFT_WIDTH = 25;
    public static final int SECONDARY_INPUT_LEFT_HEIGHT = 40;

    public static final int SECONDARY_INPUT_RIGHT_X_OFFSET = 103;
    public static final int SECONDARY_INPUT_RIGHT_Y_OFFSET = 0;
    public static final int SECONDARY_INPUT_RIGHT_WIDTH = 25;
    public static final int SECONDARY_INPUT_RIGHT_HEIGHT = 40;

    public static final int RUNNING_X_OFFSET = 0;
    public static final int RUNNING_Y_OFFSET = 59;
    public static final int RUNNING_WIDTH = 172;
    public static final int RUNNING_HEIGHT = 108;

    public static final int RUNNING_BAR_X_OFFSET = 0;
    public static final int RUNNING_BAR_Y_OFFSET = 168;
    public static final int RUNNING_BAR_WIDTH = 136;
    public static final int RUNNING_BAR_HEIGHT = 38;

    public StockPotBlockEntity blockEntity;

    public StockPotScreen(StockPotMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, STOCK_POT_GUI);
        this.blockEntity = pMenu.blockEntity;
        this.imageHeight = 232;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 52,
                        this.topPos + 13,
                        BUTTON_WIDTH,
                        BUTTON_HEIGHT,
                        BUTTON_X_OFFSET,
                        BUTTON_Y_OFFSET,
                        STOCK_POT_GUI_WIDGET,
                        pButton -> {
                            ManorsBountyMachinePacket.sendToServer(new StockPotStartPacket(this.blockEntity.getBlockPos()));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        if (menu.getCookingTime() == 0) {
                            RenderSystem.enableDepthTest();
                            RenderSystem.enableBlend();
                            // WTF，为什么这里就是必须用1
                            // 我搞不懂，我搞不懂啊！
                            if (this.isHovered()) {
                                pGuiGraphics.setColor(1, 1, 1, 0.7F);
                            }
                            pGuiGraphics.blit(pTexture, pX, pY, pUOffset, pVOffset, pWidth, pHeight, pTextureWidth, pTextureHeight);
                            if (this.isHovered()) {
                                pGuiGraphics.setColor(1, 1, 1, 1);
                            }
                        }
                    }

                    @Override
                    public boolean isActive() {
                        return super.isActive() && menu.getCookingTime() == 0;
                    }
                }
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        this.renderInput(pGuiGraphics);
        this.renderRunning(pGuiGraphics);
    }

    public void renderInput(GuiGraphics pGuiGraphics) {
        if (this.menu.getCookingTime() != 0) return;
        pGuiGraphics.blit(STOCK_POT_GUI_WIDGET, this.leftPos + 50, this.topPos + 80, MAIN_INPUT_X_OFFSET, MAIN_INPUT_Y_OFFSET, MAIN_INPUT_WIDTH, MAIN_INPUT_HEIGHT);
        pGuiGraphics.blit(STOCK_POT_GUI_WIDGET, this.leftPos + 23, this.topPos + 26, SECONDARY_INPUT_LEFT_X_OFFSET, SECONDARY_INPUT_LEFT_Y_OFFSET, SECONDARY_INPUT_LEFT_WIDTH, SECONDARY_INPUT_LEFT_HEIGHT);
        pGuiGraphics.blit(STOCK_POT_GUI_WIDGET, this.leftPos + 128, this.topPos + 26, SECONDARY_INPUT_RIGHT_X_OFFSET, SECONDARY_INPUT_RIGHT_Y_OFFSET, SECONDARY_INPUT_RIGHT_WIDTH, SECONDARY_INPUT_RIGHT_HEIGHT);
    }

    public void renderRunning(GuiGraphics pGuiGraphics) {
        int cookingTime = this.menu.getCookingTime();
        if (cookingTime == 0) return;
        pGuiGraphics.blit(STOCK_POT_GUI_WIDGET, this.leftPos + 2, this.topPos + 34, RUNNING_X_OFFSET, RUNNING_Y_OFFSET, RUNNING_WIDTH, RUNNING_HEIGHT);
        int x = this.leftPos + 20;
        int y = this.topPos + 40;
        int maxCookingTime = this.menu.getMaxCookingTime();
        pGuiGraphics.enableScissor(x, y, x + RUNNING_BAR_WIDTH * cookingTime / maxCookingTime, y + RUNNING_BAR_HEIGHT);
        pGuiGraphics.blit(STOCK_POT_GUI_WIDGET, this.leftPos + 20, this.topPos + 40, RUNNING_BAR_X_OFFSET, RUNNING_BAR_Y_OFFSET, RUNNING_BAR_WIDTH, RUNNING_BAR_HEIGHT);
        pGuiGraphics.disableScissor();
    }
}