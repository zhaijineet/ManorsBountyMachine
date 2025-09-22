package net.zhaiji.manorsbountymachine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.manorsbountymachine.block.FryerBlock;
import net.zhaiji.manorsbountymachine.block.entity.FryerBlockEntity;

@OnlyIn(Dist.CLIENT)
public class FryerBlockEntityRenderer extends BaseBlockEntityRenderer<FryerBlockEntity> {
    public FryerBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
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
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(180));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-90));
        float scale = 0.23F;
        pPoseStack.scale(scale, scale, scale);
        // 太呃呃了，一点点手动调的
        double[][] renderPos = {
                {0.55, 0.7, 0},
                {-1.1, 0, 0},
                {1.1, -1, 0},
                {-1.1, 0, 0}
        };
        for (int i = 0; i < renderPos.length; i++) {
            pPoseStack.translate(renderPos[i][0], renderPos[i][1], renderPos[i][2]);
            this.renderItem(pBlockEntity.getItem(FryerBlockEntity.INPUT_SLOTS[i]), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
        pPoseStack.popPose();
    }
}
