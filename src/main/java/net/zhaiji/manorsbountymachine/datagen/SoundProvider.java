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
                InitSoundEvent.TEAPOT_RUNNING,
                definition()
                        .subtitle(InitSoundEvent.TEAPOT_RUNNING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.TEAPOT_RUNNING_NAME)))
        );
        add(
                InitSoundEvent.TEAPOT_DONE,
                definition()
                        .subtitle(InitSoundEvent.TEAPOT_DONE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.TEAPOT_DONE_NAME)))
        );
        add(
                InitSoundEvent.BLENDER_CUP_PLACE,
                definition()
                        .subtitle(InitSoundEvent.BLENDER_CUP_PLACE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.BLENDER_CUP_PLACE_NAME)))
        );
        add(
                InitSoundEvent.BLENDER_OPEN,
                definition()
                        .subtitle(InitSoundEvent.BLENDER_OPEN_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.BLENDER_OPEN_NAME)))
        );
        add(
                InitSoundEvent.BLENDER_RUNNING,
                definition()
                        .subtitle(InitSoundEvent.BLENDER_RUNNING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.BLENDER_RUNNING_NAME)))
        );
        add(
                InitSoundEvent.BLENDER_WATER_DONE,
                definition()
                        .subtitle(InitSoundEvent.BLENDER_WATER_DONE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.BLENDER_WATER_DONE_NAME)))
        );
        add(
                InitSoundEvent.BLENDER_POWDER_DONE,
                definition()
                        .subtitle(InitSoundEvent.BLENDER_POWDER_DONE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.BLENDER_POWDER_DONE_NAME)))
        );
        add(
                InitSoundEvent.CUTTING_BOARD_CUTTING,
                definition()
                        .subtitle(InitSoundEvent.CUTTING_BOARD_CUTTING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.CUTTING_BOARD_CUTTING_NAME)))
        );

        add(
                InitSoundEvent.CUTTING_BOARD_ROLL_OUT,
                definition()
                        .subtitle(InitSoundEvent.CUTTING_BOARD_ROLL_OUT_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.CUTTING_BOARD_ROLL_OUT_NAME)))
        );

        add(
                InitSoundEvent.CUTTING_BOARD_CRAFTING,
                definition()
                        .subtitle(InitSoundEvent.CUTTING_BOARD_CRAFTING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.CUTTING_BOARD_CRAFTING_NAME)))
        );
        add(
                InitSoundEvent.STOCK_POT_COVER_MOVING,
                definition()
                        .subtitle(InitSoundEvent.STOCK_POT_COVER_MOVING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.STOCK_POT_COVER_MOVING_NAME)))
        );
        add(
                InitSoundEvent.STOCK_POT_RUNNING,
                definition()
                        .subtitle(InitSoundEvent.STOCK_POT_RUNNING_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.STOCK_POT_RUNNING_NAME)))
        );
        add(
                InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_1,
                definition()
                        .subtitle(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_1_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_1_NAME)))
        );
        add(
                InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_2,
                definition()
                        .subtitle(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_2_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.SAUCEPAN_AND_WHISK_STIRS_2_NAME)))
        );
        add(
                InitSoundEvent.SAUCEPAN_AND_WHISK_DONE,
                definition()
                        .subtitle(InitSoundEvent.SAUCEPAN_AND_WHISK_DONE_TRANSLATABLE)
                        .with(sound(soundName(InitSoundEvent.SAUCEPAN_AND_WHISK_DONE_NAME)))
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
