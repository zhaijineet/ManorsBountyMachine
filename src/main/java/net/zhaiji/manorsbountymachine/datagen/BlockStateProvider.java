package net.zhaiji.manorsbountymachine.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;
import net.zhaiji.manorsbountymachine.block.FryerBlock;
import net.zhaiji.manorsbountymachine.block.OvenBlock;
import net.zhaiji.manorsbountymachine.block.TeapotBlock;
import net.zhaiji.manorsbountymachine.register.InitBlock;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ManorsBountyMachine.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.iceCreamMachine();
        this.fryer();
        this.oven();
        this.teapot();
        this.fermenter();
    }

    public void iceCreamMachine() {
        Block block = InitBlock.ICE_CREAM_MACHINE.get();
        ResourceLocation registerKey = ForgeRegistries.BLOCKS.getKey(block);
        String namespace = registerKey.getNamespace();
        String path = registerKey.getPath();
        String texture = namespace + ":block/" + path;
        ModelFile modelFile = modelFile(path, texture);
        this.horizontalBlock(
                block,
                modelFile
        );
        this.itemModels().getBuilder(registerKey.getPath()).parent(modelFile);
    }

    public void fryer() {
        Block block = InitBlock.FRYER.get();
        ResourceLocation registerKey = ForgeRegistries.BLOCKS.getKey(block);
        String namespace = registerKey.getNamespace();
        String path = registerKey.getPath();
        String texture = namespace + ":block/" + path;
        ModelFile modelFile1 = modelFile(path, texture, "cutout_mipped");
        ModelFile modelFile2 = modelFile(path + "_oil", texture, "translucent");
        this.getVariantBuilder(block)
                .forAllStatesExcept(
                        state -> ConfiguredModel.builder()
                                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                                .modelFile(state.getValue(FryerBlock.HAS_OIL) ? modelFile2 : modelFile1)
                                .build()
                );
        this.itemModels().getBuilder(registerKey.getPath()).parent(modelFile1);
    }

    public void oven() {
        Block block = InitBlock.OVEN.get();
        ResourceLocation registerKey = ForgeRegistries.BLOCKS.getKey(block);
        String namespace = registerKey.getNamespace();
        String path = registerKey.getPath();
        String texture1 = namespace + ":block/" + path;
        String texture2 = namespace + ":block/" + path + "_running";
        ModelFile modelFile1 = modelFile(path, texture1, "cutout_mipped");
        ModelFile modelFile2 = modelFile(path + "_running", texture2, "cutout_mipped");
        this.getVariantBuilder(block)
                .forAllStatesExcept(
                        state -> ConfiguredModel.builder()
                                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                                .modelFile(state.getValue(OvenBlock.RUNNING) ? modelFile2 : modelFile1)
                                .build()
                );
        this.itemModels().getBuilder(registerKey.getPath()).parent(modelFile1);
    }

    public void teapot() {
        Block block = InitBlock.TEAPOT.get();
        ResourceLocation registerKey = ForgeRegistries.BLOCKS.getKey(block);
        String namespace = registerKey.getNamespace();
        String path = registerKey.getPath();
        String texture = namespace + ":block/" + path;
        ModelFile modelFile1 = modelFile(path, texture, "cutout_mipped");
        ModelFile modelFile2 = modelFile(path + "_open", texture, "cutout_mipped");
        this.getVariantBuilder(block)
                .forAllStatesExcept(
                        state -> ConfiguredModel.builder()
                                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                                .modelFile(state.getValue(TeapotBlock.OPEN) ? modelFile2 : modelFile1)
                                .build()
                );
    }

    public void fermenter() {
        Block block = InitBlock.FERMENTER.get();
        ResourceLocation registerKey = ForgeRegistries.BLOCKS.getKey(block);
        String namespace = registerKey.getNamespace();
        String path = registerKey.getPath();
        String texture = namespace + ":block/" + path;
        ModelFile modelFile1 = modelFile(path, texture, "cutout_mipped");
        ModelFile modelFile2 = modelFile(path + "_open", texture, "cutout_mipped");
        this.getVariantBuilder(block)
                .forAllStatesExcept(
                        state -> ConfiguredModel.builder()
                                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                                .modelFile(state.getValue(TeapotBlock.OPEN) ? modelFile2 : modelFile1)
                                .build()
                );
    }

    public ModelFile modelFile(String path, String texture) {
        return this.modelFile(path, texture, "cutout_mipped");
    }

    public ModelFile modelFile(String path, String texture, String renderType) {
        return models().withExistingParent(path, modLoc("custom/" + path))
                .texture("particle", texture)
                .texture("0", texture)
                .texture("all", texture)
                .renderType(renderType);
    }
}
