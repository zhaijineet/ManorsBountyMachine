package net.zhaiji.manorsbountymachine.client.resource;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import net.zhaiji.manorsbountymachine.ManorsBountyMachine;

import java.nio.file.Path;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ModBlockPackRepositorySource implements RepositorySource {
    private static final String DIR_NAME = "manors_bounty-mod_blocks_add_item_textures";
    private static final String PACK_NAME = "mod_blocks_add_item_textures";
    public static final String TITLE = "pack." + PACK_NAME + ".title";
    public static final String DESC = "pack." + PACK_NAME + ".desc";
    private final Pack pack;

    public ModBlockPackRepositorySource() {
        Pack.ResourcesSupplier supplier = name -> {
            IModFile file = ModList.get().getModFileById(ManorsBountyMachine.MOD_ID).getFile();
            return new PathPackResources(file.getFileName(), true, file.getFilePath()) {
                @Override
                protected Path resolve(String... paths) {
                    String[] newPaths = new String[paths.length + 1];
                    newPaths[0] = DIR_NAME;
                    System.arraycopy(paths, 0, newPaths, 1, paths.length);
                    return file.findResource(newPaths);
                }
            };
        };
        this.pack = Pack.create(
                PACK_NAME,
                Component.literal("Manor's Bounty-Mod Block Add Item Textures"),
                false,
                supplier,
                Pack.readPackInfo(DIR_NAME, supplier),
                PackType.CLIENT_RESOURCES,
                Pack.Position.TOP,
                false,
                PackSource.BUILT_IN
        );
    }

    @Override
    public void loadPacks(Consumer<Pack> pOnLoad) {
        pOnLoad.accept(this.pack);
    }
}
