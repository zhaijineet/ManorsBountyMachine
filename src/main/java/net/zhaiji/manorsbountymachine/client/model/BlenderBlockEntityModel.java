package net.zhaiji.manorsbountymachine.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.BlenderBlock;
import net.zhaiji.manorsbountymachine.block.entity.BlenderBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class BlenderBlockEntityModel extends GeoModel<BlenderBlockEntity> {
    @Override
    public ResourceLocation getModelResource(BlenderBlockEntity animatable) {
        if (animatable.getBlockState().getValue(BlenderBlock.OPEN)) {
            return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "geo/blender_open.geo.json");
        } else {
            return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "geo/blender.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(BlenderBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "textures/block/blender.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlenderBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "animations/blender.animation.json");
    }

    @Override
    public RenderType getRenderType(BlenderBlockEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
