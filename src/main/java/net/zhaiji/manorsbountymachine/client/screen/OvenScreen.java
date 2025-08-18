package net.zhaiji.manorsbountymachine.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;
import net.zhaiji.manorsbountymachine.menu.OvenMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.BakeItemCraftPacket;
import net.zhaiji.manorsbountymachine.network.server.packet.SyncOvenTimeAndTemperaturePacket;

public class OvenScreen extends AbstractMachineScreen<OvenMenu> {
    public static final ResourceLocation OVEN_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/oven_gui.png");
    public static final ResourceLocation OVEN_GUI_WIDGET = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/oven_gui_widget.png");

    public static int BUTTON_X_OFFSET = 145;
    public static int BUTTON_Y_OFFSET = 0;
    public static int BUTTON_WIDTH = 24;
    public static int BUTTON_HEIGHT = 24;

    public static int DOOR_BUTTON_X_OFFSET = 63;
    public static int DOOR_BUTTON_Y_OFFSET = 12;
    public static int DOOR_BUTTON_WIDTH = 56;
    public static int DOOR_BUTTON_HEIGHT = 30;

    public static int DOOR_X_OFFSET = 0;
    public static int DOOR_Y_OFFSET = 0;
    public static int DOOR_WIDTH = 144;
    public static int DOOR_HEIGHT = 108;

    public static int INPUT_X_OFFSET = 0;
    public static int INPUT_Y_OFFSET = 109;
    public static int INPUT_WIDTH = 74;
    public static int INPUT_HEIGHT = 44;

    public static int OUTPUT_X_OFFSET = 0;
    public static int OUTPUT_Y_OFFSET = 154;
    public static int OUTPUT_WIDTH = 26;
    public static int OUTPUT_HEIGHT = 26;

    public OvenBlockEntity blockEntity;

    public OvenScreen(OvenMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.blockEntity = pMenu.blockEntity;
        this.imageWidth = 176;
        this.imageHeight = 227;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 30,
                        this.topPos + 3,
                        BUTTON_WIDTH,
                        BUTTON_HEIGHT,
                        BUTTON_X_OFFSET,
                        BUTTON_Y_OFFSET,
                        OVEN_GUI_WIDGET,
                        pButton -> {
                            if (this.menu.getCookingTime() != 0) return;
                            int temperatureState = OvenScreen.this.menu.getTemperature().state;
                            int syncState = temperatureState >= 3 ? 0 : temperatureState + 1;
                            ManorsBountyMachinePacket.sendToServer(new SyncOvenTimeAndTemperaturePacket(this.blockEntity.getBlockPos(), 0, syncState));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        RenderSystem.enableDepthTest();
                        int temperatureState = OvenScreen.this.menu.getTemperature().state;
                        pGuiGraphics.blit(pTexture, pX, pY, pUOffset, pVOffset + pTextureDifference * temperatureState, pWidth, pHeight, pTextureWidth, pTextureHeight);
                    }
                }
        );
        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 122,
                        this.topPos + 3,
                        BUTTON_WIDTH,
                        BUTTON_HEIGHT,
                        BUTTON_X_OFFSET,
                        BUTTON_Y_OFFSET,
                        OVEN_GUI_WIDGET,
                        pButton -> {
                            if (this.menu.getCookingTime() != 0) return;
                            int cookingTimeState = OvenScreen.this.menu.getMaxCookingTime().state;
                            int syncState = cookingTimeState >= 3 ? 0 : cookingTimeState + 1;
                            ManorsBountyMachinePacket.sendToServer(new SyncOvenTimeAndTemperaturePacket(this.blockEntity.getBlockPos(), 1, syncState));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        RenderSystem.enableDepthTest();
                        int cookingTime = OvenScreen.this.menu.getCookingTime();
                        OvenBlockEntity.MaxCookingTime maxCookingTime = OvenScreen.this.menu.getMaxCookingTime();
                        int i = cookingTime != 0
                                ? maxCookingTime.state - (cookingTime / 100)
                                : maxCookingTime.state;
                        pGuiGraphics.blit(pTexture, pX, pY, pUOffset, pVOffset + pTextureDifference * i, pWidth, pHeight, pTextureWidth, pTextureHeight);
                    }
                }
        );
        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 56,
                        this.topPos + 30,
                        DOOR_BUTTON_X_OFFSET,
                        DOOR_BUTTON_Y_OFFSET,
                        DOOR_BUTTON_WIDTH,
                        DOOR_BUTTON_HEIGHT,
                        OVEN_GUI,
                        pButton -> {
                            ManorsBountyMachinePacket.sendToServer(new BakeItemCraftPacket(this.blockEntity.getBlockPos()));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        if (OvenScreen.this.menu.getCookingTime() == 0) {
                            RenderSystem.enableDepthTest();
                            RenderSystem.enableBlend();
                            // 我受不了了，为什么上面的只能用255，下面的只能用1
                            if (this.isHovered()) {
                                pGuiGraphics.setColor(255, 255, 255, 0.3F);
                            }
                            pGuiGraphics.blit(pTexture, pX, pY, pUOffset, pVOffset, pWidth, pHeight, pTextureWidth, pTextureHeight);
                            if (this.isHovered()) {
                                pGuiGraphics.setColor(1, 1, 1, 1);
                            }
                        }
                    }
                }
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(OVEN_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.renderTemperature(pGuiGraphics);
        this.renderMaxCookingTime(pGuiGraphics);
        int cookingTime = this.menu.getCookingTime();
        if (cookingTime != 0) {
            this.renderRunning(pGuiGraphics);
        } else {
            this.renderInputSlot(pGuiGraphics);
            this.renderOutputSlot(pGuiGraphics);
        }
    }

    public void renderTemperature(GuiGraphics guiGraphics) {
        guiGraphics.drawString(
                this.font,
                this.menu.getTemperature().temperatureName,
                this.leftPos + 70,
                this.topPos + 11,
                -1
        );
    }

    public void renderMaxCookingTime(GuiGraphics guiGraphics) {
        int cookingTime = this.menu.getCookingTime();
        String renderTimeName = cookingTime != 0
                ? this.menu.getMaxCookingTime().second - (cookingTime / 20) + "s"
                : this.menu.getMaxCookingTime().secondName;
        guiGraphics.drawString(
                this.font,
                renderTimeName,
                this.leftPos + 94,
                this.topPos + 11,
                -1
        );
    }

    public void renderRunning(GuiGraphics guiGraphics) {
        guiGraphics.blit(OVEN_GUI_WIDGET, this.leftPos + 16, this.topPos + 29, DOOR_X_OFFSET, DOOR_Y_OFFSET, DOOR_WIDTH, DOOR_HEIGHT);
    }

    public void renderInputSlot(GuiGraphics guiGraphics) {
        guiGraphics.blit(OVEN_GUI_WIDGET, this.leftPos + 51, this.topPos + 51, INPUT_X_OFFSET, INPUT_Y_OFFSET, INPUT_WIDTH, INPUT_HEIGHT);
    }

    public void renderOutputSlot(GuiGraphics guiGraphics) {
        if (this.menu.getSlot(OvenBlockEntity.OUTPUT).hasItem()) {
            guiGraphics.blit(OVEN_GUI_WIDGET, this.leftPos + 75, this.topPos + 60, OUTPUT_X_OFFSET, OUTPUT_Y_OFFSET, OUTPUT_WIDTH, OUTPUT_HEIGHT);
        }
    }
}
