package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.entity.*;

public class InitBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ManorsBountyMachine.MOD_ID);

    public static final RegistryObject<BlockEntityType<IceCreamMachineBlockEntity>> ICE_CREAM_MACHINE = BLOCK_ENTITY_TYPE.register(
            "ice_cream_machine",
            () -> BlockEntityType.Builder.of(IceCreamMachineBlockEntity::new, InitBlock.ICE_CREAM_MACHINE.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<FryerBlockEntity>> FRYER = BLOCK_ENTITY_TYPE.register(
            "fryer",
            () -> BlockEntityType.Builder.of(FryerBlockEntity::new, InitBlock.FRYER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<OvenBlockEntity>> OVEN = BLOCK_ENTITY_TYPE.register(
            "oven",
            () -> BlockEntityType.Builder.of(OvenBlockEntity::new, InitBlock.OVEN.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<TeapotBlockEntity>> TEAPOT = BLOCK_ENTITY_TYPE.register(
            "teapot",
            () -> BlockEntityType.Builder.of(TeapotBlockEntity::new, InitBlock.TEAPOT.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<FermenterBlockEntity>> FERMENTER = BLOCK_ENTITY_TYPE.register(
            "fermenter",
            () -> BlockEntityType.Builder.of(FermenterBlockEntity::new, InitBlock.FERMENTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<BlenderBlockEntity>> BLENDER = BLOCK_ENTITY_TYPE.register(
            "blender",
            () -> BlockEntityType.Builder.of(BlenderBlockEntity::new, InitBlock.BLENDER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<CuttingBoardBlockEntity>> CUTTING_BOARD = BLOCK_ENTITY_TYPE.register(
            "cutting_board",
            () -> BlockEntityType.Builder.of(CuttingBoardBlockEntity::new, InitBlock.CUTTING_BOARD.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<StockPotBlockEntity>> STOCK_POT = BLOCK_ENTITY_TYPE.register(
            "stock_pot",
            () -> BlockEntityType.Builder.of(StockPotBlockEntity::new, InitBlock.STOCK_POT.get()).build(null)
    );
}
