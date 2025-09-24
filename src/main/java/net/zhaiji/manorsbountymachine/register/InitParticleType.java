package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;

public class InitParticleType {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(Registries.PARTICLE_TYPE, ManorsBountyMachine.MOD_ID);

    public static final RegistryObject<SimpleParticleType> COSY_STEAM = PARTICLE_TYPE.register("cosy_steam", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> OIL_SPLASH = PARTICLE_TYPE.register("oil_splash", () -> new SimpleParticleType(false));
}
