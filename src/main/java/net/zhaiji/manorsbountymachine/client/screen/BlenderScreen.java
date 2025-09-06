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
import net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity;
import net.zhaiji.manorsbountymachine.menu.BlenderMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.BlenderStartPacket;

@OnlyIn(Dist.CLIENT)
public class BlenderScreen extends BaseMachineScreen<BlenderMenu> {
    public static final ResourceLocation BLENDER_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/blender_gui.png");
    public static final ResourceLocation BLENDER_GUI_WIDGET_1 = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/blender_gui_widget_1.png");
    public static final ResourceLocation BLENDER_GUI_WIDGET_2 = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/blender_gui_widget_2.png");

    public static final int BUTTON_X_OFFSET = 0;
    public static final int BUTTON_Y_OFFSET = 128;
    public static final int BUTTON_WIDTH = 20;
    public static final int BUTTON_HEIGHT = 21;

    public static final int BLENDER_X_OFFSET = 0;
    public static final int BLENDER_Y_OFFSET = 0;
    public static final int BLENDER_WIDTH = 100;
    public static final int BLENDER_HEIGHT = 128;

    public static final int MAIN_INPUT_X_OFFSET = 101;
    public static final int MAIN_INPUT_Y_OFFSET = 0;
    public static final int MAIN_INPUT_WIDTH = 44;
    public static final int MAIN_INPUT_HEIGHT = 58;

    public static final int BLENDER_RUNNING_X_OFFSET = 0;
    public static final int BLENDER_RUNNING_Y_OFFSET = 0;
    public static final int BLENDER_RUNNING_WIDTH = 100;
    public static final int BLENDER_RUNNING_HEIGHT = 142;

    public static final int CONTAINER_X_OFFSET = 195;
    public static final int CONTAINER_Y_OFFSET = 0;
    public static final int CONTAINER_WIDTH = 26;
    public static final int CONTAINER_HEIGHT = 35;

    public static final int OUTPUT_X_OFFSET = 146;
    public static final int OUTPUT_Y_OFFSET = 0;
    public static final int OUTPUT_WIDTH = 48;
    public static final int OUTPUT_HEIGHT = 57;

    public BlenderBlockEntity blockEntity;

    public BlenderScreen(BlenderMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, BLENDER_GUI);
        this.blockEntity = pMenu.blockEntity;
        this.imageHeight = 232;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 114,
                        this.topPos + 117,
                        BUTTON_WIDTH,
                        BUTTON_HEIGHT,
                        BUTTON_X_OFFSET,
                        BUTTON_Y_OFFSET,
                        BLENDER_GUI_WIDGET_1,
                        pButton -> {
                            ManorsBountyMachinePacket.sendToServer(new BlenderStartPacket(this.blockEntity.getBlockPos()));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
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
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        this.renderBlender(pGuiGraphics);
        this.renderInput(pGuiGraphics);
        this.renderBottle(pGuiGraphics);
        this.renderOutput(pGuiGraphics);
    }

    public void renderBlender(GuiGraphics guiGraphics) {
        int cookingTime = this.menu.getCookingTime();
        if (cookingTime == 0) {
            guiGraphics.blit(BLENDER_GUI_WIDGET_1, this.leftPos + 74, this.topPos + 14, BLENDER_X_OFFSET, BLENDER_Y_OFFSET, BLENDER_WIDTH, BLENDER_HEIGHT);
        } else {
            guiGraphics.blit(BLENDER_GUI_WIDGET_2, this.leftPos + 74, this.topPos, BLENDER_RUNNING_X_OFFSET + BLENDER_RUNNING_WIDTH * ((cookingTime / 2) % 2), BLENDER_RUNNING_Y_OFFSET, BLENDER_RUNNING_WIDTH, BLENDER_RUNNING_HEIGHT);
        }
    }

    public void renderInput(GuiGraphics guiGraphics) {
        if (this.menu.getCookingTime() != 0) return;
        guiGraphics.blit(BLENDER_GUI_WIDGET_1, this.leftPos + 102, this.topPos + 53, MAIN_INPUT_X_OFFSET, MAIN_INPUT_Y_OFFSET, MAIN_INPUT_WIDTH, MAIN_INPUT_HEIGHT);
    }

    public void renderBottle(GuiGraphics guiGraphics) {
        if (this.blockEntity.getItem(BlenderBlockEntity.CONTAINER).isEmpty()) return;
        guiGraphics.blit(BLENDER_GUI_WIDGET_1, this.leftPos + 23, this.topPos + 101, CONTAINER_X_OFFSET, CONTAINER_Y_OFFSET, CONTAINER_WIDTH, CONTAINER_HEIGHT);
    }

    public void renderOutput(GuiGraphics guiGraphics) {
        if (this.blockEntity.getItem(BlenderBlockEntity.MAIN_OUTPUT).isEmpty() && this.blockEntity.getItem(BlenderBlockEntity.SECONDARY_OUTPUT).isEmpty())
            return;
        guiGraphics.blit(BLENDER_GUI_WIDGET_1, this.leftPos + 12, this.topPos + 84, OUTPUT_X_OFFSET, OUTPUT_Y_OFFSET, OUTPUT_WIDTH, OUTPUT_HEIGHT);
    }
}
