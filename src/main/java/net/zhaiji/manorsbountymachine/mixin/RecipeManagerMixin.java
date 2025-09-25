package net.zhaiji.manorsbountymachine.mixin;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SlotInputLimitManager;
import net.zhaiji.manorsbountymachine.compat.manors_bounty.SmokingRecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("RETURN"))
    public void manorsBountyMachine$apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler, CallbackInfo ci) {
        if (SmokingRecipeManager.needInit) {
            SmokingRecipeManager.init((RecipeManager) (Object) this);
            SmokingRecipeManager.needInit = false;
        }
        if (SlotInputLimitManager.needInit) {
            SlotInputLimitManager.init((RecipeManager) (Object) this);
            SlotInputLimitManager.needInit = false;
        }
    }
}