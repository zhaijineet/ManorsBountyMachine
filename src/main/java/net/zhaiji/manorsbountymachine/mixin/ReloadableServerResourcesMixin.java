package net.zhaiji.manorsbountymachine.mixin;

import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.world.flag.FeatureFlagSet;
import net.zhaiji.manorsbountymachine.compat.kjs.ManorsBountyMachineReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    public void manorsBountyMachine$init(RegistryAccess.Frozen pRegistryAccess, FeatureFlagSet pEnabledFeatures, Commands.CommandSelection pCommandSelection, int pFunctionCompilationLevel, CallbackInfo ci) {
        ManorsBountyMachineReloadListener.serverResources = (ReloadableServerResources) (Object) this;
    }
}
