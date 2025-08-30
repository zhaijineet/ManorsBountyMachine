package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.*;

public class InitBlock {
    public static final DeferredRegister<Block> BLOCK = DeferredRegister.create(Registries.BLOCK, ManorsBountyMachine.MOD_ID);

    public static final RegistryObject<Block> ICE_CREAM_MACHINE = BLOCK.register(
            "ice_cream_machine",
            () -> new IceCreamMachineBlock(BlockBehaviour.Properties.of().noOcclusion())
    );

    public static final RegistryObject<Block> FRYER = BLOCK.register(
            "fryer",
            () -> new FryerBlock(BlockBehaviour.Properties.of().noOcclusion())
    );

    public static final RegistryObject<Block> OVEN = BLOCK.register(
            "oven",
            () -> new OvenBlock(BlockBehaviour.Properties.of().noOcclusion().lightLevel(blockState -> {
                if (blockState.getValue(OvenBlock.RUNNING)) {
                    return 15;
                } else {
                    return 0;
                }
            }))
    );

    public static final RegistryObject<Block> TEAPOT = BLOCK.register(
            "teapot",
            () -> new TeapotBlock(BlockBehaviour.Properties.of().noOcclusion())
    );

    public static final RegistryObject<Block> FERMENTER = BLOCK.register(
            "fermenter",
            () -> new FermenterBlock(BlockBehaviour.Properties.of().noOcclusion())
    );

    public static final RegistryObject<Block> BLENDER = BLOCK.register(
            "blender",
            () -> new BlenderBlock(BlockBehaviour.Properties.of().noOcclusion())
    );
}
