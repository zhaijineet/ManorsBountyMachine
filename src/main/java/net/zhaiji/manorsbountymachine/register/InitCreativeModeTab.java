package net.zhaiji.manorsbountymachine.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;

public class InitCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ManorsBountyMachine.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MANORS_BOUNTY_MACHINE_TAB = CREATIVE_MODE_TAB.register(
            "manors_bounty_machine_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> InitItem.ICE_CREAM_MACHINE.get().getDefaultInstance())
                    .title(Component.translatable("itemGroup.manors_bounty_machine.machine"))
                    .displayItems((pParameters, pOutput) -> {
                        InitItem.ITEM.getEntries().forEach(item -> pOutput.accept(item.get()));
                    })
                    .build()
    );
}
