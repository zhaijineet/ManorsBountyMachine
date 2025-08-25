package net.zhaiji.manorsbountymachine.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.FermenterBlockEntity;
import net.zhaiji.manorsbountymachine.menu.FermenterMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.FermentationStartPacket;
import net.zhaiji.manorsbountymachine.network.server.packet.FermentationStopPacket;

public class FermenterScreen extends AbstractMachineScreen<FermenterMenu> {
    public static final ResourceLocation FERMENTER_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/fermenter_gui.png");
    public static final ResourceLocation FERMENTER_GUI_WIDGET = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/fermenter_gui_widget.png");

    public static final int START_BUTTON_X_OFFSET = 50;
    public static final int START_BUTTON_Y_OFFSET = 133;
    public static final int START_BUTTON_WIDTH = 30;
    public static final int START_BUTTON_HEIGHT = 18;

    public static final int STOP_BUTTON_X_OFFSET = 81;
    public static final int STOP_BUTTON_Y_OFFSET = 133;
    public static final int STOP_BUTTON_WIDTH = 35;
    public static final int STOP_BUTTON_HEIGHT = 20;

    public static final int DIM_CHECK_X_OFFSET = 118;
    public static final int DIM_CHECK_Y_OFFSET = 133;
    public static final int DIM_CHECK_WIDTH = 16;
    public static final int DIM_CHECK_HEIGHT = 15;

    public static final int NORMAL_CHECK_X_OFFSET = 118;
    public static final int NORMAL_CHECK_Y_OFFSET = 148;
    public static final int NORMAL_CHECK_WIDTH = 16;
    public static final int NORMAL_CHECK_HEIGHT = 15;

    public static final int BRIGHT_CHECK_X_OFFSET = 118;
    public static final int BRIGHT_CHECK_Y_OFFSET = 163;
    public static final int BRIGHT_CHECK_WIDTH = 16;
    public static final int BRIGHT_CHECK_HEIGHT = 15;

    public static final int RUNNING_X_OFFSET = 0;
    public static final int RUNNING_Y_OFFSET = 0;
    public static final int RUNNING_WIDTH = 124;
    public static final int RUNNING_HEIGHT = 132;

    public static final int DONE_X_OFFSET = 125;
    public static final int DONE_Y_OFFSET = 0;
    public static final int DONE_WIDTH = 124;
    public static final int DONE_HEIGHT = 132;

    public static final int RUNNING_TIPS_X_OFFSET = 0;
    public static final int RUNNING_TIPS_Y_OFFSET = 133;
    public static final int RUNNING_TIPS_WIDTH = 49;
    public static final int RUNNING_TIPS_HEIGHT = 58;

    public static final int DONE_TIPS_X_OFFSET = 0;
    public static final int DONE_TIPS_Y_OFFSET = 191;
    public static final int DONE_TIPS_WIDTH = 49;
    public static final int DONE_TIPS_HEIGHT = 58;

    public FermenterBlockEntity blockEntity;

    public FermenterScreen(FermenterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, FERMENTER_GUI);
        this.blockEntity = pMenu.blockEntity;
    }
    // 2 5

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 142,
                        this.topPos + 115,
                        START_BUTTON_WIDTH,
                        START_BUTTON_HEIGHT,
                        START_BUTTON_X_OFFSET,
                        START_BUTTON_Y_OFFSET,
                        FERMENTER_GUI_WIDGET,
                        pButton -> {
                            ManorsBountyMachinePacket.sendToServer(new FermentationStartPacket(this.blockEntity.getBlockPos()));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        if (!this.isActive()) return;
                        RenderSystem.enableDepthTest();
                        pGuiGraphics.blit(pTexture, pX, pY, pUOffset, pVOffset + (this.isHovered() ? pTextureDifference : 0), pWidth, pHeight, pTextureWidth, pTextureHeight);
                    }

                    @Override
                    protected boolean isValidClickButton(int pButton) {
                        return super.isValidClickButton(pButton) && menu.getCookingTime() == 0;
                    }

                    @Override
                    public boolean isActive() {
                        return super.isActive() && menu.getCookingTime() == 0;
                    }
                }
        );

        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 139,
                        this.topPos + 114,
                        STOP_BUTTON_WIDTH,
                        STOP_BUTTON_HEIGHT,
                        STOP_BUTTON_X_OFFSET,
                        STOP_BUTTON_Y_OFFSET,
                        FERMENTER_GUI_WIDGET,
                        pButton -> {
                            ManorsBountyMachinePacket.sendToServer(new FermentationStopPacket(this.blockEntity.getBlockPos()));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        if (!this.isActive()) return;
                        RenderSystem.enableDepthTest();
                        pGuiGraphics.blit(pTexture, pX, pY, pUOffset, pVOffset + (this.isHovered() ? pTextureDifference : 0), pWidth, pHeight, pTextureWidth, pTextureHeight);
                    }

                    @Override
                    protected boolean isValidClickButton(int pButton) {
                        return super.isValidClickButton(pButton) && menu.getCookingTime() != 0;
                    }

                    @Override
                    public boolean isActive() {
                        return super.isActive() && menu.getCookingTime() != 0;
                    }
                }
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        this.renderLightState(pGuiGraphics);
        this.renderRunning(pGuiGraphics);
        this.renderRunningTip(pGuiGraphics);
        this.renderCookingTime(pGuiGraphics);
    }

    public void renderLightState(GuiGraphics guiGraphics) {
        switch (this.blockEntity.getLightState()) {
            case DIM -> this.renderDimLightState(guiGraphics);
            case NORMAL -> this.renderNormalLightState(guiGraphics);
            case BRIGHT -> this.renderBrightLightState(guiGraphics);
        }
    }

    public void renderDimLightState(GuiGraphics guiGraphics) {
        guiGraphics.blit(FERMENTER_GUI_WIDGET, this.leftPos + 117, this.topPos + 24, DIM_CHECK_X_OFFSET, DIM_CHECK_Y_OFFSET, DIM_CHECK_WIDTH, DIM_CHECK_HEIGHT);
    }

    public void renderNormalLightState(GuiGraphics guiGraphics) {
        guiGraphics.blit(FERMENTER_GUI_WIDGET, this.leftPos + 137, this.topPos + 24, NORMAL_CHECK_X_OFFSET, NORMAL_CHECK_Y_OFFSET, NORMAL_CHECK_WIDTH, NORMAL_CHECK_HEIGHT);
    }

    public void renderBrightLightState(GuiGraphics guiGraphics) {
        guiGraphics.blit(FERMENTER_GUI_WIDGET, this.leftPos + 157, this.topPos + 24, BRIGHT_CHECK_X_OFFSET, BRIGHT_CHECK_Y_OFFSET, BRIGHT_CHECK_WIDTH, BRIGHT_CHECK_HEIGHT);
    }

    public void renderRunning(GuiGraphics guiGraphics) {
        if (this.menu.getCookingTime() != 0) {
            guiGraphics.blit(FERMENTER_GUI_WIDGET, this.leftPos + 2, this.topPos + 5, RUNNING_X_OFFSET, RUNNING_Y_OFFSET, RUNNING_WIDTH, RUNNING_HEIGHT);
        } else if (!this.blockEntity.getItem(FermenterBlockEntity.OUTPUT).isEmpty()) {
            guiGraphics.blit(FERMENTER_GUI_WIDGET, this.leftPos + 2, this.topPos + 5, DONE_X_OFFSET, DONE_Y_OFFSET, DONE_WIDTH, DONE_HEIGHT);
        }
    }

    public void renderRunningTip(GuiGraphics guiGraphics) {
        if (this.menu.getCookingTime() != 0) {
            guiGraphics.blit(FERMENTER_GUI_WIDGET, this.leftPos + 126, this.topPos + 40, RUNNING_TIPS_X_OFFSET, RUNNING_TIPS_Y_OFFSET, RUNNING_TIPS_WIDTH, RUNNING_TIPS_HEIGHT);
        } else if (!this.blockEntity.getItem(FermenterBlockEntity.OUTPUT).isEmpty()) {
            guiGraphics.blit(FERMENTER_GUI_WIDGET, this.leftPos + 126, this.topPos + 40, DONE_TIPS_X_OFFSET, DONE_TIPS_Y_OFFSET, DONE_TIPS_WIDTH, DONE_TIPS_HEIGHT);
        }
    }

    public void renderCookingTime(GuiGraphics guiGraphics) {
        int cookingTime = this.menu.getCookingTime();
        int maxCookingTime = this.menu.getMaxCookingTime();
        if (cookingTime != 0) {
            String text = (maxCookingTime - cookingTime) / 20 + "s";
            guiGraphics.drawString(
                    this.font,
                    text,
                    this.leftPos + 153 - this.font.width(text) / 2,
                    this.topPos + 77,
                    -12840447,
                    false
            );
        }
    }
}
