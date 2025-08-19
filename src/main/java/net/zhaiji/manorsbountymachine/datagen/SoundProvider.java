package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.register.InitSoundEvent;

public class SoundProvider extends SoundDefinitionsProvider {
    public SoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ManorsBountyMachine.MOD_ID, helper);
    }

    public static ResourceLocation soundName(String name) {
        return ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, name);
    }

    @Override
    public void registerSounds() {
        add(
                InitSoundEvent.ICE_CREAM_MACHINE_CLANK,
                definition()
                        .subtitle(InitSoundEvent.ICE_CREAM_MACHINE_CLANK_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.ICE_CREAM_MACHINE_CLANK_NAME)))
        );
        add(
                InitSoundEvent.FRYER_FRYING,
                definition()
                        .subtitle(InitSoundEvent.FRYER_FRYING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.FRYER_FRYING_NAME)))
        );
        add(
                InitSoundEvent.OVEN_DING,
                definition()
                        .subtitle(InitSoundEvent.OVEN_DING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.OVEN_DING_NAME)))
        );
        add(
                InitSoundEvent.OVEN_RUNNING,
                definition()
                        .subtitle(InitSoundEvent.OVEN_RUNNING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.OVEN_RUNNING_NAME)))
        );
    }
}
