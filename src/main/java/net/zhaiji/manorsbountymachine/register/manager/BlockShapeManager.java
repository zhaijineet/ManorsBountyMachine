package net.zhaiji.manorsbountymachine.register.manager;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockShapeManager {
    public static final VoxelShape SOUTH_ICE_CREAM_MACHINE_SHAPE = Shapes.or(
            Block.box(1.0, 0.0, 0.0, 15.0, 15.0, 12.0),
            Block.box(1.0, 12.0, 12.0, 15.0, 15.0, 16.0),
            Block.box(1.0, 0.0, 12.0, 3.0, 12.0, 14.0),
            Block.box(3.0, 0.0, 12.0, 13.0, 2.0, 14.0),
            Block.box(13.0, 0.0, 12.0, 15.0, 12.0, 14.0),
            Block.box(6.0, 9.0, 12.0, 10.0, 11.0, 14.0),
            Block.box(5.0, 4.0, 12.0, 11.0, 5.0, 14.0)
    );
    public static final VoxelShape WEST_ICE_CREAM_MACHINE_SHAPE = Shapes.or(
            Block.box(4.0, 0.0, 1.0, 16.0, 15.0, 15.0),
            Block.box(0.0, 12.0, 1.0, 4.0, 15.0, 15.0),
            Block.box(2.0, 0.0, 1.0, 4.0, 12.0, 3.0),
            Block.box(2.0, 0.0, 3.0, 4.0, 2.0, 13.0),
            Block.box(2.0, 0.0, 13.0, 4.0, 12.0, 15.0),
            Block.box(2.0, 9.0, 6.0, 4.0, 11.0, 10.0),
            Block.box(2.0, 4.0, 5.0, 4.0, 5.0, 11.0)
    );
    public static final VoxelShape EAST_ICE_CREAM_MACHINE_SHAPE = Shapes.or(
            Block.box(0.0, 0.0, 1.0, 12.0, 15.0, 15.0),
            Block.box(12.0, 12.0, 1.0, 16.0, 15.0, 15.0),
            Block.box(12.0, 0.0, 13.0, 14.0, 12.0, 15.0),
            Block.box(12.0, 0.0, 3.0, 14.0, 2.0, 13.0),
            Block.box(12.0, 0.0, 1.0, 14.0, 12.0, 3.0),
            Block.box(12.0, 9.0, 6.0, 14.0, 11.0, 10.0),
            Block.box(12.0, 4.0, 5.0, 14.0, 5.0, 11.0)
    );
    public static final VoxelShape NORTH_ICE_CREAM_MACHINE_SHAPE = Shapes.or(
            Block.box(1.0, 0.0, 4.0, 15.0, 15.0, 16.0),
            Block.box(1.0, 12.0, 0.0, 15.0, 15.0, 4.0),
            Block.box(13.0, 0.0, 2.0, 15.0, 12.0, 4.0),
            Block.box(3.0, 0.0, 2.0, 13.0, 2.0, 4.0),
            Block.box(1.0, 0.0, 2.0, 3.0, 12.0, 4.0),
            Block.box(6.0, 9.0, 2.0, 10.0, 11.0, 4.0),
            Block.box(5.0, 4.0, 2.0, 11.0, 5.0, 4.0)
    );

    public static final VoxelShape SOUTH_FRYER_SHAPE = Shapes.or(
            Block.box(15.0, 0.0, 2.5, 16.0, 16.0, 12.5),
            Block.box(0.0, 0.0, -0.5, 16.0, 20.0, 2.5),
            Block.box(0.0, 0.0, 2.5, 1.0, 16.0, 12.5),
            Block.box(0.0, 0.0, 12.5, 16.0, 16.0, 14.5),
            Block.box(1.0, 0.0, 2.5, 15.0, 10.0, 12.5),
            Block.box(7.0, 16.5, 14.0, 9.0, 17.5, 17.0)
    );
    public static final VoxelShape WEST_FRYER_SHAPE = Shapes.or(
            Block.box(3.5, 0.0, 15.0, 13.5, 16.0, 16.0),
            Block.box(13.5, 0.0, 0.0, 16.5, 20.0, 16.0),
            Block.box(3.5, 0.0, 0.0, 13.5, 16.0, 1.0),
            Block.box(1.5, 0.0, 0.0, 3.5, 16.0, 16.0),
            Block.box(3.5, 0.0, 1.0, 13.5, 10.0, 15.0),
            Block.box(-1.0, 16.5, 7.0, 2.0, 17.5, 9.0)
    );
    public static final VoxelShape EAST_FRYER_SHAPE = Shapes.or(
            Block.box(2.5, 0.0, 0.0, 12.5, 16.0, 1.0),
            Block.box(-0.5, 0.0, 0.0, 2.5, 20.0, 16.0),
            Block.box(2.5, 0.0, 15.0, 12.5, 16.0, 16.0),
            Block.box(12.5, 0.0, 0.0, 14.5, 16.0, 16.0),
            Block.box(2.5, 0.0, 1.0, 12.5, 10.0, 15.0),
            Block.box(14.0, 16.5, 7.0, 17.0, 17.5, 9.0)
    );
    public static final VoxelShape NORTH_FRYER_SHAPE = Shapes.or(
            Block.box(0.0, 0.0, 3.5, 1.0, 16.0, 13.5),
            Block.box(0.0, 0.0, 13.5, 16.0, 20.0, 16.5),
            Block.box(15.0, 0.0, 3.5, 16.0, 16.0, 13.5),
            Block.box(0.0, 0.0, 1.5, 16.0, 16.0, 3.5),
            Block.box(1.0, 0.0, 3.5, 15.0, 10.0, 13.5),
            Block.box(7.0, 16.5, -1.0, 9.0, 17.5, 2.0)
    );

    public static final VoxelShape TEAPOT_SHAPE = Block.box(
            4.5, 0, 4.5, 11.5, 8, 11.5
    );

}
