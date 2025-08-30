package net.zhaiji.manorsbountymachine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class BaseBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public BlockEntityRendererProvider.Context context;

    public BaseBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        this.context = pContext;
    }

    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public Player getPlayer() {
        return this.getMinecraft().player;
    }

    public Level getLevel() {
        return this.getPlayer().level();
    }

    public ItemRenderer getItemRenderer() {
        return this.context.getItemRenderer();
    }

    public BakedModel getItemModel(ItemStack itemStack) {
        return this.getItemRenderer().getModel(itemStack, this.getLevel(), this.getPlayer(), 0);
    }

    public void renderItem(ItemStack itemStack, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        this.getItemRenderer().render(
                itemStack,
                ItemDisplayContext.FIXED,
                false,
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay,
                this.getItemModel(itemStack)
        );
    }
}
