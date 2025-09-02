package net.zhaiji.manorsbountymachine.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.register.InitBlock;

import java.util.Set;

public class BlockLootTable extends BlockLootSubProvider {
    public BlockLootTable() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(InitBlock.ICE_CREAM_MACHINE.get());
        this.dropSelf(InitBlock.FRYER.get());
        this.dropSelf(InitBlock.OVEN.get());
        this.dropSelf(InitBlock.TEAPOT.get());
        this.dropSelf(InitBlock.FERMENTER.get());
        this.dropSelf(InitBlock.BLENDER.get());
        this.dropSelf(InitBlock.CUTTING_BOARD.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return InitBlock.BLOCK.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
