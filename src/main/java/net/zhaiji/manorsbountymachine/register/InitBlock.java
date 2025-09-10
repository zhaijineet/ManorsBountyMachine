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
            IceCreamMachineBlock::new
    );

    public static final RegistryObject<Block> FRYER = BLOCK.register(
            "fryer",
            FryerBlock::new
    );

    public static final RegistryObject<Block> OVEN = BLOCK.register(
            "oven",
            OvenBlock::new
    );

    public static final RegistryObject<Block> TEAPOT = BLOCK.register(
            "teapot",
            TeapotBlock::new
    );

    public static final RegistryObject<Block> FERMENTER = BLOCK.register(
            "fermenter",
            FermenterBlock::new
    );

    public static final RegistryObject<Block> BLENDER = BLOCK.register(
            "blender",
            BlenderBlock::new
    );

    public static final RegistryObject<Block> CUTTING_BOARD = BLOCK.register(
            "cutting_board",
            CuttingBoardBlock::new
    );

    public static final RegistryObject<Block> STOCK_POT = BLOCK.register(
            "stock_pot",
            StockPotBlock::new
    );

    public static final RegistryObject<Block> SAUCEPAN_AND_WHISK = BLOCK.register(
            "saucepan_and_whisk",
            SaucepanAndWhiskBlock::new
    );

    public static final RegistryObject<Block> COOKTOP = BLOCK.register(
            "cooktop",
            CookTopBlock::new
    );

    public static BlockBehaviour.Properties getBlockProperties() {
        return BlockBehaviour.Properties.of().noOcclusion().strength(1);
    }
}
