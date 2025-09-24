package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.register.InitParticleType;

public class ParticleProvider extends ParticleDescriptionProvider {
    public ParticleProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(
                InitParticleType.COSY_STEAM.get(),
                ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "steam"),
                12,
                false
        );
        this.spriteSet(
                InitParticleType.OIL_SPLASH.get(),
                ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, "oil_splash"),
                4,
                false
        );
    }
}
