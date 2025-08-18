package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.FryerBlock;
import net.zhaiji.manorsbountymachine.block.IceCreamMachineBlock;
import net.zhaiji.manorsbountymachine.block.OvenBlock;

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
            () -> new OvenBlock(BlockBehaviour.Properties.of().noOcclusion())
    );
}
