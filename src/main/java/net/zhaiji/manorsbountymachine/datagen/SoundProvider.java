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
        add(
                InitSoundEvent.TEAPOT_CUP_PLACE,
                definition()
                        .subtitle(InitSoundEvent.TEAPOT_CUP_PLACE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.TEAPOT_CUP_PLACE_NAME)))
        );
        add(
                InitSoundEvent.TEAPOT_OPEN,
                definition()
                        .subtitle(InitSoundEvent.TEAPOT_OPEN_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.TEAPOT_OPEN_NAME)))
        );
        add(
                InitSoundEvent.TEAPOT_CLOSE,
                definition()
                        .subtitle(InitSoundEvent.TEAPOT_CLOSE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.TEAPOT_CLOSE_NAME)))
        );
        add(
                InitSoundEvent.TEAPOT_DONE,
                definition()
                        .subtitle(InitSoundEvent.TEAPOT_DONE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.TEAPOT_DONE_NAME)))
        );
        add(
                InitSoundEvent.TEAPOT_RUNNING,
                definition()
                        .subtitle(InitSoundEvent.TEAPOT_RUNNING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.TEAPOT_RUNNING_NAME)))
        );
        add(
                InitSoundEvent.SHAKER_OPEN,
                definition()
                        .subtitle(InitSoundEvent.SHAKER_OPEN_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.SHAKER_OPEN_NAME)))
        );
        add(
                InitSoundEvent.SHAKER_SHAKE,
                definition()
                        .subtitle(InitSoundEvent.SHAKER_SHAKE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.SHAKER_SHAKE_NAME)))
        );
    }
}
