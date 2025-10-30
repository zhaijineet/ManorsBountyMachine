package net.zhaiji.manorsbountymachine.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OilSplashParticle extends WaterDropParticle {
    public OilSplashParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ);
        this.gravity = 0.04F;
        if (pYSpeed == 0.0D && (pXSpeed != 0.0D || pZSpeed != 0.0D)) {
            this.xd = pXSpeed;
            this.yd = 0.1D;
            this.zd = pZSpeed;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            OilSplashParticle oilSplashParticle = new OilSplashParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            oilSplashParticle.pickSprite(this.sprite);
            return oilSplashParticle;
        }
    }
}
