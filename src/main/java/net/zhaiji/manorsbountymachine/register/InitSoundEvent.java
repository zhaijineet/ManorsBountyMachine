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
    public static final String TEAPOT_CUP_PLACE_NAME = "teapot_cup_place";
    public static final String TEAPOT_OPEN_NAME = "teapot_open";
    public static final String TEAPOT_CLOSE_NAME = "teapot_close";
    public static final String TEAPOT_RUNNING_NAME = "teapot_running";
    public static final String TEAPOT_DONE_NAME = "teapot_done";
    public static final String BLENDER_RUNNING_NAME = "blender_running";
    public static final String BLENDER_WATER_DONE_NAME = "blender_water_done";
    public static final String BLENDER_POWDER_DONE_NAME = "blender_powder_done";
    public static final String CUTTING_BOARD_CUTTING_NAME = "cutting_board_cutting";
    public static final String CUTTING_BOARD_ROLL_OUT_NAME = "cutting_board_roll_out";
    public static final String CUTTING_BOARD_CRAFTING_NAME = "cutting_board_crafting";
    public static final String STOCK_POT_COVER_MOVING_NAME = "stock_pot_cover_moving";
    public static final String STOCK_POT_RUNNING_NAME = "stock_pot_running";
    public static final String SHAKER_OPEN_NAME = "shaker_open";
    public static final String SHAKER_SHAKE_NAME = "shaker_shake";

    public static final String ICE_CREAM_MACHINE_CLANK_TRANSLATABLE = "sound.manors_bounty_machine.ice_cream_machine_clank";
    public static final String FRYER_FRYING_TRANSLATABLE = "sound.manors_bounty_machine.fryer_frying";
    public static final String OVEN_DING_TRANSLATABLE = "sound.manors_bounty_machine.oven_ding";
    public static final String OVEN_RUNNING_TRANSLATABLE = "sound.manors_bounty_machine.oven_running";
    public static final String TEAPOT_CUP_PLACE_TRANSLATABLE = "sound.manors_bounty_machine.teapot_cup_place";
    public static final String TEAPOT_OPEN_TRANSLATABLE = "sound.manors_bounty_machine.teapot_open";
    public static final String TEAPOT_CLOSE_TRANSLATABLE = "sound.manors_bounty_machine.teapot_close";
    public static final String TEAPOT_RUNNING_TRANSLATABLE = "sound.manors_bounty_machine.teapot_running";
    public static final String TEAPOT_DONE_TRANSLATABLE = "sound.manors_bounty_machine.teapot_done";
    public static final String BLENDER_RUNNING_TRANSLATABLE = "sound.manors_bounty_machine.blender_running";
    public static final String BLENDER_WATER_DONE_TRANSLATABLE = "sound.manors_bounty_machine.blender_water_done";
    public static final String BLENDER_POWDER_DONE_TRANSLATABLE = "sound.manors_bounty_machine.blender_powder_done";
    public static final String CUTTING_BOARD_CUTTING_TRANSLATABLE = "sound.manors_bounty_machine.cutting_board_cutting";
    public static final String CUTTING_BOARD_ROLL_OUT_TRANSLATABLE = "sound.manors_bounty_machine.cutting_board_roll_out";
    public static final String CUTTING_BOARD_CRAFTING_TRANSLATABLE = "sound.manors_bounty_machine.cutting_board_crafting";
    public static final String STOCK_POT_COVER_MOVING_TRANSLATABLE = "sound.manors_bounty_machine.stock_pot_cover_moving";
    public static final String STOCK_POT_RUNNING_TRANSLATABLE = "sound.manors_bounty_machine.stock_pot_running";
    public static final String SHAKER_OPEN_TRANSLATABLE = "sound.manors_bounty_machine.shaker_open";
    public static final String SHAKER_SHAKE_TRANSLATABLE = "sound.manors_bounty_machine.shaker_shake";

    public static final RegistryObject<SoundEvent> ICE_CREAM_MACHINE_CLANK = register(ICE_CREAM_MACHINE_CLANK_NAME);
    public static final RegistryObject<SoundEvent> FRYER_FRYING = register(FRYER_FRYING_NAME);
    public static final RegistryObject<SoundEvent> OVEN_DING = register(OVEN_DING_NAME);
    public static final RegistryObject<SoundEvent> OVEN_RUNNING = register(OVEN_RUNNING_NAME);
    public static final RegistryObject<SoundEvent> TEAPOT_CUP_PLACE = register(TEAPOT_CUP_PLACE_NAME);
    public static final RegistryObject<SoundEvent> TEAPOT_OPEN = register(TEAPOT_OPEN_NAME);
    public static final RegistryObject<SoundEvent> TEAPOT_CLOSE = register(TEAPOT_CLOSE_NAME);
    public static final RegistryObject<SoundEvent> TEAPOT_DONE = register(TEAPOT_DONE_NAME);
    public static final RegistryObject<SoundEvent> TEAPOT_RUNNING = register(TEAPOT_RUNNING_NAME);
    public static final RegistryObject<SoundEvent> BLENDER_RUNNING = register(BLENDER_RUNNING_NAME);
    public static final RegistryObject<SoundEvent> BLENDER_WATER_DONE = register(BLENDER_WATER_DONE_NAME);
    public static final RegistryObject<SoundEvent> BLENDER_POWDER_DONE = register(BLENDER_POWDER_DONE_NAME);
    public static final RegistryObject<SoundEvent> CUTTING_BOARD_CUTTING = register(CUTTING_BOARD_CUTTING_NAME);
    public static final RegistryObject<SoundEvent> CUTTING_BOARD_ROLL_OUT = register(CUTTING_BOARD_ROLL_OUT_NAME);
    public static final RegistryObject<SoundEvent> CUTTING_BOARD_CRAFTING = register(CUTTING_BOARD_CRAFTING_NAME);
    public static final RegistryObject<SoundEvent> STOCK_POT_COVER_MOVING = register(STOCK_POT_COVER_MOVING_TRANSLATABLE);
    public static final RegistryObject<SoundEvent> STOCK_POT_RUNNING = register(STOCK_POT_RUNNING_TRANSLATABLE);
    public static final RegistryObject<SoundEvent> SHAKER_OPEN = register(SHAKER_OPEN_NAME);
    public static final RegistryObject<SoundEvent> SHAKER_SHAKE = register(SHAKER_SHAKE_NAME);

    public static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENT.register(
                name,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ManorsBountyMachine.MOD_ID, name))
        );
    }
}
