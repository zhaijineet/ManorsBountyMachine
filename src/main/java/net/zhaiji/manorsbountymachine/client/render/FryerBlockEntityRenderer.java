package net.zhaiji.manorsbountymachine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zhaiji.manorsbountymachine.block.FryerBlock;
import net.zhaiji.manorsbountymachine.block.entity.FryerBlockEntity;

public class FryerBlockEntityRenderer implements BlockEntityRenderer<FryerBlockEntity> {
    public FryerBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    public void renderItem(FryerBlockEntity blockEntity, int slot, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        ItemStack itemStack = blockEntity.getItem(slot);
        Level level = blockEntity.getLevel();
        BakedModel bakedmodel = itemRenderer.getModel(itemStack, level, minecraft.player, 0);
        itemRenderer.render(
                itemStack,
                ItemDisplayContext.FIXED,
                false,
                poseStack,
                bufferSource,
                packedLight,
                packedOverlay,
                bakedmodel
        );
    }

    @Override
    public void render(FryerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.7, 0.5);
        Direction face = pBlockEntity.getBlockState().getValue(FryerBlock.FACING);
        float angle = pBlockEntity.getBlockState().getValue(FryerBlock.FACING).toYRot();
        // 呃呃，为什么会转个向，我不明白
        if (face.getAxis() == Direction.Axis.X) angle = 360 - angle;
        pPoseStack.mulPose(Axis.YP.rotationDegrees(angle));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-90));
        float scale = 0.23F;
        pPoseStack.scale(scale, scale, scale);

        // 太呃呃了，一点点手动调的
        pPoseStack.translate(-0.55, 0.7, 0);
        this.renderItem(pBlockEntity, 1, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        pPoseStack.translate(1.1, 0, 0);
        this.renderItem(pBlockEntity, 2, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        pPoseStack.translate(-1.1, -1, 0);
        this.renderItem(pBlockEntity, 3, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        pPoseStack.translate(1.1, 0, 0);
        this.renderItem(pBlockEntity, 4, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        pPoseStack.popPose();
    }
}
