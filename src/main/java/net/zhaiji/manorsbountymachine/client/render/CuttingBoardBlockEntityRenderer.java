package net.zhaiji.manorsbountymachine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.zhaiji.manorsbountymachine.block.CuttingBoardBlock;
import net.zhaiji.manorsbountymachine.block.entity.CuttingBoardBlockEntity;

public class CuttingBoardBlockEntityRenderer extends BaseBlockEntityRenderer<CuttingBoardBlockEntity> {
    public CuttingBoardBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(CuttingBoardBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, (double) 1 / 16, 0.5);
        Direction face = pBlockEntity.getBlockState().getValue(CuttingBoardBlock.FACING);
        float angle = pBlockEntity.getBlockState().getValue(CuttingBoardBlock.FACING).toYRot();
        // 呃呃，为什么会转个向，我不明白
        if (face.getAxis() == Direction.Axis.X) angle = 360 - angle;
        pPoseStack.mulPose(Axis.YP.rotationDegrees(angle));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-90));
        pPoseStack.translate(0, -0.08, 0);
        float scale = 0.5F;
        pPoseStack.scale(scale, scale, scale);
        int[] rotation = {
                -4,
                9,
                -14,
                0,
                -21,
                17
        };
        for (int i = 0; i < CuttingBoardBlockEntity.ITEMS_SIZE; i++) {
            pPoseStack.translate(0, 0, (double) 1 / 16);
            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(rotation[i]));
            this.renderItem(pBlockEntity.getItem(i), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
            pPoseStack.popPose();
        }
        pPoseStack.popPose();
    }
}
