package net.zhaiji.manorsbountymachine.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.TeapotBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.menu.TeapotMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.BrewingStartPacket;

public class TeapotScreen extends AbstractMachineScreen<TeapotMenu> {
    public static final ResourceLocation TEAPOT_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/teapot_gui.png");
    public static final ResourceLocation TEAPOT_GUI_WIDGET = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/teapot_gui_widget.png");
    public static final String TRANSLATABLE = "block.manors_bounty_machine.teapot.tooltip";

    public static final int BUTTON_X_OFFSET = 105;
    public static final int BUTTON_Y_OFFSET = 0;
    public static final int BUTTON_WIDTH = 48;
    public static final int BUTTON_HEIGHT = 48;

    public static final int OUTPUT_X_OFFSET = 0;
    public static final int OUTPUT_Y_OFFSET = 111;
    public static final int OUTPUT_WIDTH = 44;
    public static final int OUTPUT_HEIGHT = 44;

    public static final int MUG_X_OFFSET = 111;
    public static final int MUG_Y_OFFSET = 45;
    public static final int MUG_WIDTH = 78;
    public static final int MUG_HEIGHT = 78;

    public static final int BOTTLE_X_OFFSET = 111;
    public static final int BOTTLE_Y_OFFSET = 123;
    public static final int BOTTLE_WIDTH = 78;
    public static final int BOTTLE_HEIGHT = 78;

    public static final int OUTPUT_DRINK_X_OFFSET = 0;
    public static final int OUTPUT_DRINK_Y_OFFSET = 0;
    public static final int OUTPUT_DRINK_WIDTH = 54;
    public static final int OUTPUT_DRINK_HEIGHT = 54;

    public static final int TEAPOT_X_OFFSET = 0;
    public static final int TEAPOT_Y_OFFSET = 0;
    public static final int TEAPOT_WIDTH = 104;
    public static final int TEAPOT_HEIGHT = 110;

    public static final int COVER_X_OFFSET = 201;
    public static final int COVER_Y_OFFSET = 0;
    public static final int COVER_WIDTH = 46;
    public static final int COVER_HEIGHT = 46;

    public static final int WATER_X_OFFSET = 154;
    public static final int WATER_Y_OFFSET = 0;
    public static final int WATER_WIDTH = 46;
    public static final int WATER_HEIGHT = 46;

    public static final int MILK_X_OFFSET = 156;
    public static final int MILK_Y_OFFSET = 47;
    public static final int MILK_WIDTH = 42;
    public static final int MILK_HEIGHT = 42;

    public static final int SLOT_X_OFFSET = 13;
    public static final int SLOT_Y_OFFSET = 168;
    public static final int SLOT_WIDTH = 18;
    public static final int SLOT_HEIGHT = 18;

    public TeapotBlockEntity blockEntity;
    public Player player;

    public TeapotScreen(TeapotMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, TEAPOT_GUI);
        this.blockEntity = pMenu.blockEntity;
        this.player = pPlayerInventory.player;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(
                new ImageButton(
                        this.leftPos + 122,
                        this.topPos + 84,
                        BUTTON_WIDTH,
                        BUTTON_HEIGHT,
                        BUTTON_X_OFFSET,
                        BUTTON_Y_OFFSET,
                        TEAPOT_GUI_WIDGET,
                        pButton -> {
                            Level level = this.blockEntity.getLevel();
                            BlockPos blockPos = this.blockEntity.getBlockPos().below();
                            if (ManorsBountyCompat.isTeapotHeatBlock(level.getBlockState(blockPos))) {
                                ManorsBountyMachinePacket.sendToServer(new BrewingStartPacket(this.blockEntity.getBlockPos()));
                            } else {
                                this.player.sendSystemMessage(Component.translatable(TRANSLATABLE));
                            }
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        if (menu.getCookingTime() != 0) return;
                        RenderSystem.enableDepthTest();
                        pGuiGraphics.blit(pTexture, pX, pY, pUOffset, pVOffset + (this.isHovered() ? pTextureDifference : 0), pWidth, pHeight, pTextureWidth, pTextureHeight);
                    }
                }
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        this.renderOutput(pGuiGraphics);
        this.renderTeapot(pGuiGraphics);
        this.renderDrink(pGuiGraphics);
    }

    public void renderOutput(GuiGraphics guiGraphics) {
        ItemStack output = this.blockEntity.getItem(TeapotBlockEntity.OUTPUT);
        guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 19, this.topPos + 76, OUTPUT_X_OFFSET, OUTPUT_Y_OFFSET, OUTPUT_WIDTH, OUTPUT_HEIGHT);
        this.renderOutputDrink(guiGraphics, output);
    }

    // 畏惧了
    public void renderOutputDrink(GuiGraphics guiGraphics, ItemStack output) {
        if (ManorsBountyCompat.isTeapotGuiGlassBottle(output)) {
            guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 2, this.topPos + 59, BOTTLE_X_OFFSET, BOTTLE_Y_OFFSET, BOTTLE_WIDTH, BOTTLE_HEIGHT);
        } else if (ManorsBountyCompat.isTeapotGuiMug(output)) {
            guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 2, this.topPos + 59, MUG_X_OFFSET, MUG_Y_OFFSET, MUG_WIDTH, MUG_HEIGHT);
        }
        if (ManorsBountyCompat.isTeapotGuiGlassBottleMilkTea(output)) {
            this.renderOutputDrinkTexture(guiGraphics, ManorsBountyCompat.getTeapotGuiGlassBottleMilkTeaTexture());
        } else if (ManorsBountyCompat.isTeapotGuiMugApricotKernel(output)) {
            this.renderOutputDrinkTexture(guiGraphics, ManorsBountyCompat.getTeapotGuiMugApricotKernelTexture());
        } else if (ManorsBountyCompat.isTeapotGuiMugBlackTea(output)) {
            this.renderOutputDrinkTexture(guiGraphics, ManorsBountyCompat.getTeapotGuiMugBlackTeaTexture());
        } else if (ManorsBountyCompat.isTeapotGuiMugCocoa(output)) {
            this.renderOutputDrinkTexture(guiGraphics, ManorsBountyCompat.getTeapotGuiMugCocoaTexture());
        } else if (ManorsBountyCompat.isTeapotGuiMugCoffee(output)) {
            this.renderOutputDrinkTexture(guiGraphics, ManorsBountyCompat.getTeapotGuiMugCoffeeTexture());
        } else if (ManorsBountyCompat.isTeapotGuiMugGreenTea(output)) {
            this.renderOutputDrinkTexture(guiGraphics, ManorsBountyCompat.getTeapotGuiMugGreenTeaTexture());
        } else if (ManorsBountyCompat.isTeapotGuiMugMatcha(output)) {
            this.renderOutputDrinkTexture(guiGraphics, ManorsBountyCompat.getTeapotGuiMugMatchaTexture());
        }
        if (!output.isEmpty()) {
            guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 32, this.topPos + 89, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT);
        }
    }

    public void renderOutputDrinkTexture(GuiGraphics guiGraphics, ResourceLocation texture) {
        guiGraphics.blit(texture, this.leftPos + 14, this.topPos + 71, OUTPUT_DRINK_X_OFFSET, OUTPUT_DRINK_Y_OFFSET, OUTPUT_DRINK_WIDTH, OUTPUT_DRINK_HEIGHT, OUTPUT_DRINK_WIDTH, OUTPUT_DRINK_HEIGHT);
    }

    public void renderTeapot(GuiGraphics guiGraphics) {
        guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 72, this.topPos, TEAPOT_X_OFFSET, TEAPOT_Y_OFFSET, TEAPOT_WIDTH, TEAPOT_HEIGHT);
    }

    public void renderDrink(GuiGraphics guiGraphics) {
        ItemStack drink = this.blockEntity.getItem(TeapotBlockEntity.DRINK);
        if (this.menu.getCookingTime() != 0) {
            guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 106, this.topPos + 26, COVER_X_OFFSET, COVER_Y_OFFSET, COVER_WIDTH, COVER_HEIGHT);
        }
        if (drink.isEmpty()) return;
        if (ManorsBountyCompat.isBottledWater(drink)) {
            guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 106, this.topPos + 26, WATER_X_OFFSET, WATER_Y_OFFSET, WATER_WIDTH, WATER_HEIGHT);
        } else if (ManorsBountyCompat.isBoxedMilk(drink)) {
            guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 108, this.topPos + 28, MILK_X_OFFSET, MILK_Y_OFFSET, MILK_WIDTH, MILK_HEIGHT);
        }
        guiGraphics.blit(TEAPOT_GUI_WIDGET, this.leftPos + 120, this.topPos + 40, SLOT_X_OFFSET, SLOT_Y_OFFSET, SLOT_WIDTH, SLOT_HEIGHT);
    }
}
