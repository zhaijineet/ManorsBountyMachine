package net.zhaiji.manorsbountymachine.client.render;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity;
import net.zhaiji.manorsbountymachine.client.model.BlenderBlockEntityModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

@OnlyIn(Dist.CLIENT)
public class BlenderBlockEntityRenderer extends GeoBlockRenderer<BlenderBlockEntity> {
    public BlenderBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        super(new BlenderBlockEntityModel());
    }
}
