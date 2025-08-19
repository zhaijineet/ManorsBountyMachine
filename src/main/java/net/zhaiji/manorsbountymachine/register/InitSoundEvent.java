package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;

public class InitSoundEvent {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(Registries.SOUND_EVENT, ManorsBountyMachine.MOD_ID);

    public static final String ICE_CREAM_MACHINE_CLANK_NAME = "ice_cream_machine_clank";
    public static final String FRYER_FRYING_NAME = "fryer_frying";
    public static final String OVEN_DING_NAME = "oven_ding";
    public static final String OVEN_RUNNING_NAME = "oven_running";

    public static final String ICE_CREAM_MACHINE_CLANK_TRANSLATABLE = "sound.manors_bounty_machine.ice_cream_machine_clank";
    public static final String FRYER_FRYING_TRANSLATABLE = "sound.manors_bounty_machine.fryer_frying";
    public static final String OVEN_DING_TRANSLATABLE = "sound.manors_bounty_machine.oven_ding";
    public static final String OVEN_RUNNING_TRANSLATABLE = "sound.manors_bounty_machine.oven_running";

    public static final RegistryObject<SoundEvent> ICE_CREAM_MACHINE_CLANK = register(ICE_CREAM_MACHINE_CLANK_NAME);
    public static final RegistryObject<SoundEvent> FRYER_FRYING = register(FRYER_FRYING_NAME);
    public static final RegistryObject<SoundEvent> OVEN_DING = register(OVEN_DING_NAME);
    public static final RegistryObject<SoundEvent> OVEN_RUNNING = register(OVEN_RUNNING_NAME);

    public static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENT.register(
                name,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, name))
        );
    }
}
