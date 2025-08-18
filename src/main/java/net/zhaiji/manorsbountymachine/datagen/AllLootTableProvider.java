package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.zhaiji.manorsbountymachine.datagen.loot.BlockLootTable;

import java.util.List;
import java.util.Set;

public class AllLootTableProvider {
    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(
                packOutput,
                Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(BlockLootTable::new, LootContextParamSets.BLOCK)
                )
        );
    }
}
