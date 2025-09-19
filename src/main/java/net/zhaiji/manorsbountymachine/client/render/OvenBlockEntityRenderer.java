package net.zhaiji.manorsbountymachine.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.manorsbountymachine.block.OvenBlock;
import net.zhaiji.manorsbountymachine.block.entity.OvenBlockEntity;

@OnlyIn(Dist.CLIENT)
public class OvenBlockEntityRenderer extends BaseBlockEntityRenderer<OvenBlockEntity> {
    public OvenBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(OvenBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.4, 0.5);
        Direction face = pBlockEntity.getBlockState().getValue(OvenBlock.FACING);
        float angle = pBlockEntity.getBlockState().getValue(OvenBlock.FACING).toYRot();
        // 呃呃，为什么会转个向，我不明白
        if (face.getAxis() == Direction.Axis.X) angle = 360 - angle;
        pPoseStack.mulPose(Axis.YP.rotationDegrees(angle));
        float scale = 0.2F;
        pPoseStack.scale(scale, scale, scale);
        double[][] renderPos = {
                {-1.1, 0, 0},
                {1.1, 0, 0},
                {1.1, 0, 0},
                {-2.2, 0, 0.9},
                {1.1, 0, 0},
                {1.1, 0, 0},
        };
        ItemStack output = pBlockEntity.getItem(OvenBlockEntity.OUTPUT);
        for (int i = 0; i < renderPos.length; i++) {
            ItemStack stack = output.isEmpty() ? pBlockEntity.getItem(OvenBlockEntity.INPUT_SLOTS[i]) : output;
            pPoseStack.translate(renderPos[i][0], renderPos[i][1], renderPos[i][2]);
            this.renderItem(stack, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
        pPoseStack.popPose();
    }
}
