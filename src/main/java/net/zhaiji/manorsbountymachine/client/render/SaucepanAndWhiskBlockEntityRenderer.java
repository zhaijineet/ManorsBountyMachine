package net.zhaiji.manorsbountymachine.client.render;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity;
import net.zhaiji.manorsbountymachine.client.model.SaucepanAndWhiskBlockEntityModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SaucepanAndWhiskBlockEntityRenderer extends GeoBlockRenderer<SaucepanAndWhiskBlockEntity> {
    public SaucepanAndWhiskBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        super(new SaucepanAndWhiskBlockEntityModel());
    }
}
