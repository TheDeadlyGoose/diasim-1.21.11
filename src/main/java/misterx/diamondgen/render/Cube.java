package misterx.diamondgen.render;

import net.minecraft.core.BlockPos;  // Changed
import net.minecraft.core.Vec3i;     // Changed

public class Cube extends Cuboid {

    public Cube() {
        this(BlockPos.ZERO, Color.WHITE);  // Changed from ORIGIN
    }

    public Cube(BlockPos pos) {
        this(pos, Color.WHITE);
    }

    public Cube(BlockPos pos, Color color) {
        super(pos, new Vec3i(1, 1, 1), color);
    }

    @Override
    public BlockPos getPos() {
        return this.start;
    }
}