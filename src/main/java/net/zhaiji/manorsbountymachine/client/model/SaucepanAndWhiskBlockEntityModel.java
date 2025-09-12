package net.zhaiji.manorsbountymachine.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.SaucepanAndWhiskBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class SaucepanAndWhiskBlockEntityModel extends GeoModel<SaucepanAndWhiskBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SaucepanAndWhiskBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "geo/saucepan_and_whisk.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SaucepanAndWhiskBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/block/saucepan_and_whisk.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SaucepanAndWhiskBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "animations/saucepan_and_whisk.animation.json");
    }

    @Override
    public RenderType getRenderType(SaucepanAndWhiskBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
