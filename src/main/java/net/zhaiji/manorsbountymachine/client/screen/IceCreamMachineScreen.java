package net.zhaiji.manorsbountymachine.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.registries.ForgeRegistries;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.IceCreamMachineBlockEntity;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.ManorsBountyCompat;
import net.zhaiji.manorsbountymachine.menu.IceCreamMachineMenu;
import net.zhaiji.manorsbountymachine.network.ManorsBountyMachinePacket;
import net.zhaiji.manorsbountymachine.network.server.packet.IceCreamCraftPacket;
import net.zhaiji.manorsbountymachine.network.server.packet.IceCreamTowFlavorSwitchPacket;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

@OnlyIn(Dist.CLIENT)
public class IceCreamMachineScreen extends BaseMachineScreen<IceCreamMachineMenu> {
    public static final ResourceLocation ICE_CREAM_MACHINE_GUI = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/ice_cream_machine_gui.png");
    public static final ResourceLocation CONE_TEXTURE = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/gui/ice_cream_machine_gui/ice_cream_cone.png");
    public static final String TWO_FLAVOR_SWITCH_TRANSLATABLE = "gui.manors_bounty_machine.two_flavor_switch";

    public static final int BAN_SLOT_X_OFFSET = 224;
    public static final int BAN_SLOT_Y_OFFSET = 0;
    public static final int BAN_SLOT_WIDTH = 18;
    public static final int BAN_SLOT_HEIGHT = 18;

    public static final int ICE_CREAM_X_OFFSET = 0;
    public static final int ICE_CREAM_Y_OFFSET = 0;
    public static final int ICE_CREAM_WIDTH = 100;
    public static final int ICE_CREAM_HEIGHT = 100;

    public IceCreamMachineBlockEntity blockEntity;
    public Rect2i twoFlavorSwitchRect;

    public IceCreamMachineScreen(IceCreamMachineMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle, ICE_CREAM_MACHINE_GUI);
        this.blockEntity = pMenu.blockEntity;
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
                        Component.empty(),
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
        this.twoFlavorSwitchRect = new Rect2i(
                this.leftPos + 139,
                this.topPos + 17,
                20,
                20
        );
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTowFlavorTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        if (!blockEntity.isTwoFlavor) {
            pGuiGraphics.blit(ICE_CREAM_MACHINE_GUI, this.leftPos + 142, this.topPos + 117, BAN_SLOT_X_OFFSET, BAN_SLOT_Y_OFFSET, BAN_SLOT_WIDTH, BAN_SLOT_HEIGHT);
        }
        this.renderFluid(pGuiGraphics);
        this.renderIceCreamItem(pGuiGraphics);
    }

    @Override
    protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
        if (pSlot == null || pType == ClickType.PICKUP_ALL) return;
        Level level = this.blockEntity.getLevel();
        BlockPos blockPos = this.blockEntity.getBlockPos();
        switch (pSlot.index) {
            case IceCreamMachineBlockEntity.OUTPUT_SLOT ->
                    level.playLocalSound(blockPos, SoundEvents.BAMBOO_SAPLING_PLACE, SoundSource.BLOCKS, 1F, 1.5F, false);
            case IceCreamMachineBlockEntity.LEFT_INPUT_SLOT, IceCreamMachineBlockEntity.RIGHT_INPUT_SLOT ->
                    level.playLocalSound(blockPos, InitSoundEvent.ICE_CREAM_MACHINE_CLANK.get(), SoundSource.BLOCKS, 0.3F, 1F, false);
        }
    }

    public void renderTowFlavorTooltip(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        if (!this.twoFlavorSwitchRect.contains(pMouseX, pMouseY)) return;
        pGuiGraphics.renderTooltip(this.font, Component.translatable(TWO_FLAVOR_SWITCH_TRANSLATABLE), pMouseX, pMouseY);
    }

    // 嘎巴一下死在电脑前了
    // mcr不知道干了啥，把blit的渲染搞坏了
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
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.setColor(r, g, b, alpha);
        pGuiGraphics.enableScissor(x, y + renderHeight - (renderHeight * fluidTank.getFluidAmount() / fluidTank.getCapacity()), x + renderWidth, y + renderHeight);
        pGuiGraphics.blit(textureId.withPrefix("textures/").withSuffix(".png"), x, y, 0, 0, renderWidth, renderHeight, width * scale, height * count * scale);
//        pGuiGraphics.blit(x, y, 0, renderWidth, renderHeight, textureAtlasSprite);
        pGuiGraphics.disableScissor();
        pGuiGraphics.setColor(1F, 1F, 1F, 1F);
        pGuiGraphics.pose().popPose();
    }

    public void renderConeAndIceCream(GuiGraphics guiGraphics, ItemStack output, ResourceLocation texture) {
        guiGraphics.blit(CONE_TEXTURE, this.leftPos - 4, this.topPos + 43, ICE_CREAM_X_OFFSET, ICE_CREAM_Y_OFFSET, ICE_CREAM_WIDTH, ICE_CREAM_HEIGHT, ICE_CREAM_WIDTH, ICE_CREAM_HEIGHT);
        if (!ManorsBountyCompat.isIceCreamCone(output)) {
            guiGraphics.blit(texture, this.leftPos - 4, this.topPos + 17, ICE_CREAM_X_OFFSET, ICE_CREAM_Y_OFFSET, ICE_CREAM_WIDTH, ICE_CREAM_HEIGHT, ICE_CREAM_WIDTH, ICE_CREAM_HEIGHT);
        }
    }

    public void renderIceCreamItem(GuiGraphics pGuiGraphics) {
        ItemStack output = this.blockEntity.getItem(IceCreamMachineBlockEntity.OUTPUT_SLOT);
        if (output.isEmpty()) return;
        ResourceLocation outputKey = ForgeRegistries.ITEMS.getKey(output.getItem());
        String path = outputKey.withPrefix("textures/gui/ice_cream_machine_gui/").getPath() + ".png";
        ResourceLocation resource1 = ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, path);
        ResourceLocation resource2 = ResourceLocation.fromNamespaceAndPath(outputKey.getNamespace(), path);
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager.getResource(resource1).isPresent()) {
            this.renderConeAndIceCream(pGuiGraphics, output, resource1);
        } else if (resourceManager.getResource(resource2).isPresent()) {
            this.renderConeAndIceCream(pGuiGraphics, output, resource2);
        }
    }
}
