package net.zhaiji.manorsbountymachine.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.IceCreamMachineBlockEntity;
import net.zhaiji.manorsbountymachine.menu.IceCreamMachineMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.IceCreamCraftPacket;
import net.zhaiji.manorsbountymachine.network.server.packet.IceCreamTowFlavorSwitchPacket;

@OnlyIn(Dist.CLIENT)
public class IceCreamMachineScreen extends AbstractMachineScreen<IceCreamMachineMenu> {
    public static final ResourceLocation ICE_CREAM_MACHINE_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/ice_cream_machine_gui.png");

    public static final int BAN_SLOT_X_OFFSET = 224;
    public static final int BAN_SLOT_Y_OFFSET = 0;
    public static final int BAN_SLOT_WIDTH = 18;
    public static final int BAN_SLOT_HEIGHT = 18;

    public IceCreamMachineBlockEntity blockEntity;

    public IceCreamMachineScreen(IceCreamMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
                        this.leftPos + 26,
                        this.topPos + 5,
                        40,
                        34,
                        178,
                        0,
                        ICE_CREAM_MACHINE_GUI,
                        pButton -> {
                            ManorsBountyMachinePacket.sendToServer(new IceCreamCraftPacket(this.blockEntity.getBlockPos()));
                        }
                ) {
                    @Override
                    public void renderTexture(GuiGraphics pGuiGraphics, ResourceLocation pTexture, int pX, int pY, int pUOffset, int pVOffset, int pTextureDifference, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
                        int i = pVOffset;
                        if (this.isHovered()) {
                            i = pVOffset + pTextureDifference;
                        }
                        RenderSystem.enableDepthTest();
                        pGuiGraphics.blit(pTexture, pX, pY, pUOffset, i, pWidth, pHeight, pTextureWidth, pTextureHeight);
                    }
                }
        );
        this.addRenderableWidget(
                new Checkbox(
                        this.leftPos + 139,
                        this.topPos + 17,
                        20,
                        20,
                        Component.translatable("test.test.test"),
                        this.blockEntity.isTwoFlavor
                ) {
                    @Override
                    public void onPress() {
                        super.onPress();
                        blockEntity.isTwoFlavor = this.selected();
                        ManorsBountyMachinePacket.sendToServer(new IceCreamTowFlavorSwitchPacket(blockEntity.getBlockPos(), this.selected()));
                    }
                }
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(ICE_CREAM_MACHINE_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (!blockEntity.isTwoFlavor) {
            pGuiGraphics.blit(ICE_CREAM_MACHINE_GUI, this.leftPos + 142, this.topPos + 117, BAN_SLOT_X_OFFSET, BAN_SLOT_Y_OFFSET, BAN_SLOT_WIDTH, BAN_SLOT_HEIGHT);
        }
        this.renderFluid(pGuiGraphics);
        this.renderIceCreamItem(pGuiGraphics);
    }

    public void renderFluid(GuiGraphics pGuiGraphics) {
        FluidTank fluidTank = this.blockEntity.fluidTank;
        if (fluidTank.isEmpty()) return;
        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidTank.getFluid().getFluid());
        ResourceLocation textureId = fluidTypeExtensions.getStillTexture();
        TextureAtlasSprite textureAtlasSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(textureId);
        SpriteContents contents = textureAtlasSprite.contents();
        int width = contents.width();
        int height = contents.height();
        int count = (int) contents.getUniqueFrames().count();
        int x = this.leftPos + 99;
        int y = this.topPos + 14;
        int renderWidth = 34;
        int renderHeight = 47;
        int scale = renderWidth / width;
        int color = fluidTypeExtensions.getTintColor();
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        pGuiGraphics.setColor(r, g, b, alpha);
        pGuiGraphics.enableScissor(x, y + renderHeight - (renderHeight * fluidTank.getFluidAmount() / fluidTank.getCapacity()), x + renderWidth, y + renderHeight);
        pGuiGraphics.blit(textureId.withPrefix("textures/").withSuffix(".png"), x, y, 0, 0, renderWidth, renderHeight, width * scale, height * count * scale);
        pGuiGraphics.disableScissor();
        pGuiGraphics.setColor(1F, 1F, 1F, 1F);
    }

    public void renderIceCreamItem(GuiGraphics pGuiGraphics) {
    }
}
