package net.zhaiji.manorsbountymachine.register;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;

import java.util.function.Supplier;

public class InitVillager {
    public static final DeferredRegister<PoiType> POT_TYPE = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, ManorsBountyMachine.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION = DeferredRegister.create(Registries.VILLAGER_PROFESSION, ManorsBountyMachine.MOD_ID);


    public static final RegistryObject<PoiType> CHEF_POI = poiRegister("chef_poi", InitBlock.OVEN);
    public static final RegistryObject<VillagerProfession> CHEF = professionRegister("chef", CHEF_POI);

    public static final RegistryObject<PoiType> BARTENDER_POI = poiRegister("bartender_poi", InitBlock.FERMENTER);
    public static final RegistryObject<VillagerProfession> BARTENDER = professionRegister("bartender", BARTENDER_POI);

    public static final RegistryObject<PoiType> ORCHARDIST_POI = poiRegister("orchardist_poi", InitBlock.TEAPOT);
    public static final RegistryObject<VillagerProfession> ORCHARDIST = professionRegister("orchardist", ORCHARDIST_POI);

    public static RegistryObject<PoiType> poiRegister(String name, Supplier<Block> block) {
        return POT_TYPE.register(
                name,
                () -> new PoiType(
                        ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates()),
                        1,
                        1
                )
        );
    }


    public static RegistryObject<VillagerProfession> professionRegister(String name, Supplier<PoiType> poiType) {
        return VILLAGER_PROFESSION.register(
                name,
                () -> new VillagerProfession(
                        name,
                        poiTypeHolder -> poiTypeHolder.value() == poiType.get(),
                        poiTypeHolder -> poiTypeHolder.value() == poiType.get(),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        null
                )
        );
    }
}
