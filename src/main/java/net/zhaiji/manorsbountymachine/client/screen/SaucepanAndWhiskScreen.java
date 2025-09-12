package net.zhaiji.manorsbountymachine.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity;
import net.zhaiji.manorsbountymachine.menu.SaucepanAndWhiskMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.TrySaucepanAndWhiskCraftPacket;
import net.zhaiji.manorsbountymachine.recipe.SaucepanAndWhiskRecipe;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

public class SaucepanAndWhiskScreen extends BaseMachineScreen<SaucepanAndWhiskMenu> {
    public static final ResourceLocation SAUCEPAN_AND_WHISK_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/saucepan_and_whisk_gui.png");
    public static final ResourceLocation SAUCEPAN_AND_WHISK_GUI_WIDGET = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/saucepan_and_whisk_gui_widget.png");

    public static final int BUTTON_X_OFFSET = 0;
    public static final int BUTTON_Y_OFFSET = 0;
    public static final int BUTTON_WIDTH = 126;
    public static final int BUTTON_HEIGHT = 107;

    public static final int WHISK_X_OFFSET = 139;
    public static final int WHISK_Y_OFFSET = 0;
    public static final int WHISK_WIDTH = 78;
    public static final int WHISK_HEIGHT = 106;

    public static final int SLOT_X_OFFSET = 0;
    public static final int SLOT_Y_OFFSET = 0;
    public static final int SLOT_WIDTH = 112;
    public static final int SLOT_HEIGHT = 101;

    public static final int HAS_HEAT_X_OFFSET = 0;
    public static final int HAS_HEAT_Y_OFFSET = 115;
    public static final int HAS_HEAT_WIDTH = 16;
    public static final int HAS_HEAT_HEIGHT = 11;

    public static final int NO_HEAT_X_OFFSET = 0;
    public static final int NO_HEAT_Y_OFFSET = 102;
    public static final int NO_HEAT_WIDTH = 12;
    public static final int NO_HEAT_HEIGHT = 12;

    public static final int STIRS_X_OFFSET = 113;
    public static final int STIRS_Y_OFFSET = 0;
    public static final int STIRS_WIDTH = 14;
    public static final int STIRS_HEIGHT = 73;

    public static final int STIRS_0_X_OFFSET = 128;
    public static final int STIRS_0_Y_OFFSET = 48;
    public static final int STIRS_0_WIDTH = 10;
    public static final int STIRS_0_HEIGHT = 17;

    public static final int STIRS_1_X_OFFSET = 128;
    public static final int STIRS_1_Y_OFFSET = 28;
    public static final int STIRS_1_WIDTH = 10;
    public static final int STIRS_1_HEIGHT = 19;

    public static final int STIRS_2_X_OFFSET = 128;
    public static final int STIRS_2_Y_OFFSET = 0;
    public static final int STIRS_2_WIDTH = 10;
    public static final int STIRS_2_HEIGHT = 27;

    public SaucepanAndWhiskBlockEntity blockEntity;
    public ImageButton whiskButton;
    public Rect2i mainInputRect;
    public Rect2i secondaryInputRect;
    public Rect2i outputRect;
    public boolean whiskStir = false;

    public SaucepanAndWhiskScreen(SaucepanAndWhiskMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, SAUCEPAN_AND_WHISK_GUI);
        this.blockEntity = pMenu.blockEntity;
        this.imageHeight = 236;
    }

    @Override
    protected void init() {
        super.init();
        this.mainInputRect = new Rect2i(
                this.leftPos + 60,
                this.topPos + 66,
                56,
                38
        );
        this.secondaryInputRect = new Rect2i(
                this.leftPos + 4,
                this.topPos + 3,
                42,
                62
        );
        this.outputRect = new Rect2i(
                this.leftPos + 79,
                this.topPos + 46,
                18,
                20
        );
        this.whiskButton = this.addWidget(
                new ImageButton(
                        this.leftPos + 25,
                        this.topPos + 3,
                        BUTTON_WIDTH,
                        BUTTON_HEIGHT,
                        BUTTON_X_OFFSET,
                        BUTTON_Y_OFFSET,
                        SAUCEPAN_AND_WHISK_GUI_WIDGET,
                        pButton -> {
                            boolean flag = false;
                            for (SaucepanAndWhiskRecipe recipe : blockEntity.getAllRecipe()) {
                                if (recipe.matches(blockEntity)) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                BlockPos blockPos = this.blockEntity.getBlockPos();
                                ManorsBountyMachinePacket.sendToServer(new TrySaucepanAndWhiskCraftPacket(blockPos));
                                whiskStir = !whiskStir;
                                blockEntity.getLevel().playLocalSound(
                                        blockPos,
                                        whiskStir
                                                ? InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_1.get()
                                                : InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_2.get(),
                                        SoundSource.BLOCKS,
                                        1,
                                        1,
                                        false
                                );
                            } else {
                                this.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                            }
                        }
                ) {
                    private boolean onWhisk(double pMouseX, double pMouseY) {
                        return !mainInputRect.contains((int) pMouseX, (int) pMouseY)
                                && !secondaryInputRect.contains((int) pMouseX, (int) pMouseY)
                                && !outputRect.contains((int) pMouseX, (int) pMouseY);
                    }

                    private void setColor(GuiGraphics guiGraphics) {
                        if (this.isHovered()) {
                            guiGraphics.setColor(255, 255, 255, 0.3F);
                        }
                    }

                    private void resetColor(GuiGraphics guiGraphics) {
                        if (this.isHovered()) {
                            guiGraphics.setColor(1, 1, 1, 1);
                        }
                    }

                    @Override
                    public void playDownSound(SoundManager pHandler) {
                    }

                    @Override
                    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
                        this.isHovered = this.isHovered && this.onWhisk(pMouseX, pMouseY);
                        super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                    }

                    @Override
                    protected boolean clicked(double pMouseX, double pMouseY) {
                        return super.clicked(pMouseX, pMouseY) && this.onWhisk(pMouseX, pMouseY);
                    }

                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        RenderSystem.enableDepthTest();
                        RenderSystem.enableBlend();
                        if (whiskStir) {
                            pGuiGraphics.blit(pTexture, SaucepanAndWhiskScreen.this.leftPos + 81, SaucepanAndWhiskScreen.this.topPos, WHISK_X_OFFSET, WHISK_Y_OFFSET, WHISK_WIDTH, WHISK_HEIGHT);
                            this.setColor(pGuiGraphics);
                            pGuiGraphics.blit(pTexture, SaucepanAndWhiskScreen.this.leftPos + 81, SaucepanAndWhiskScreen.this.topPos, WHISK_X_OFFSET, WHISK_Y_OFFSET, WHISK_WIDTH, WHISK_HEIGHT);
                            this.resetColor(pGuiGraphics);
                        } else {
                            int yOffset = WHISK_Y_OFFSET + WHISK_HEIGHT;
                            pGuiGraphics.blit(pTexture, SaucepanAndWhiskScreen.this.leftPos + 17, SaucepanAndWhiskScreen.this.topPos, WHISK_X_OFFSET, yOffset, WHISK_WIDTH, WHISK_HEIGHT);
                            this.setColor(pGuiGraphics);
                            pGuiGraphics.blit(pTexture, SaucepanAndWhiskScreen.this.leftPos + 17, SaucepanAndWhiskScreen.this.topPos, WHISK_X_OFFSET, yOffset, WHISK_WIDTH, WHISK_HEIGHT);
                            this.resetColor(pGuiGraphics);
                        }
                    }
                }
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        this.whiskButton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderSlot(pGuiGraphics);
        this.renderHeat(pGuiGraphics);
        this.renderStirsCount(pGuiGraphics);
    }

    public void renderSlot(GuiGraphics pGuiGraphics) {
        pGuiGraphics.blit(SAUCEPAN_AND_WHISK_GUI_WIDGET, this.leftPos + 4, this.topPos + 3, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT);
    }

    public void renderHeat(GuiGraphics pGuiGraphics) {
        if (this.blockEntity.onHeatBlock()) {
            pGuiGraphics.blit(SAUCEPAN_AND_WHISK_GUI_WIDGET, this.leftPos + 26, this.topPos + 13, HAS_HEAT_X_OFFSET, HAS_HEAT_Y_OFFSET, HAS_HEAT_WIDTH, HAS_HEAT_HEIGHT);
        } else {
            pGuiGraphics.blit(SAUCEPAN_AND_WHISK_GUI_WIDGET, this.leftPos + 27, this.topPos + 13, NO_HEAT_X_OFFSET, NO_HEAT_Y_OFFSET, NO_HEAT_WIDTH, NO_HEAT_HEIGHT);
        }
    }

    public void renderStirsCount(GuiGraphics pGuiGraphics) {
        pGuiGraphics.blit(SAUCEPAN_AND_WHISK_GUI_WIDGET, this.leftPos + 158, this.topPos + 64, STIRS_X_OFFSET, STIRS_Y_OFFSET, STIRS_WIDTH, STIRS_HEIGHT);
        int stirsCount = this.menu.getStirsCount();
        int[][] stirsLevel = {
                {160, 116, STIRS_0_X_OFFSET, STIRS_0_Y_OFFSET, STIRS_0_WIDTH, STIRS_0_HEIGHT},
                {160, 95, STIRS_1_X_OFFSET, STIRS_1_Y_OFFSET, STIRS_1_WIDTH, STIRS_1_HEIGHT},
                {160, 66, STIRS_2_X_OFFSET, STIRS_2_Y_OFFSET, STIRS_2_WIDTH, STIRS_2_HEIGHT}
        };
        for (int i = 0; i < stirsCount && i < stirsLevel.length; i++) {
            pGuiGraphics.blit(
                    SAUCEPAN_AND_WHISK_GUI_WIDGET,
                    this.leftPos + stirsLevel[i][0],
                    this.topPos + stirsLevel[i][1],
                    stirsLevel[i][2],
                    stirsLevel[i][3],
                    stirsLevel[i][4],
                    stirsLevel[i][5]
            );
        }
    }
}
