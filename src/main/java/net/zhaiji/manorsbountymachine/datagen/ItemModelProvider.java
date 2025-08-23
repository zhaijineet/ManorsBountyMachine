package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.register.InitItem;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output,ExistingFileHelper existingFileHelper) {
        super(output, ManorsBountyMachine.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(InitItem.SHAKER.get());
        this.basicItem(InitItem.TEAPOT.get());
        this.basicItem(InitItem.FERMENTER.get());
    }
}
