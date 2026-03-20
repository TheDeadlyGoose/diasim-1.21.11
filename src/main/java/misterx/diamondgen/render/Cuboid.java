package misterx.diamondgen.render;

import net.minecraft.core.BlockPos;  // Changed
import net.minecraft.core.Vec3i;     // Changed
import org.joml.Matrix4f;  // Added for render method
import com.mojang.blaze3d.systems.RenderSystem;

public class Cuboid extends Renderer {

    public BlockPos start;
    public Vec3i size;

    private Line[] edges = new Line[12];

    public Cuboid() {
        this(BlockPos.ZERO, BlockPos.ZERO, Color.WHITE);  // Changed
    }

    public Cuboid(BlockPos pos) {
        this(pos, new BlockPos(1, 1, 1), Color.WHITE);
    }

    public Cuboid(BlockPos start, BlockPos end, Color color) {
        this(start, new Vec3i(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ()), color);
    }

    // Removed BlockBox constructor as it's not in 1.21.11 core package

    public Cuboid(BlockPos start, Vec3i size, Color color) {
        this.start = start;
        this.size = size;
        this.edges[0] = new Line(toVec3d(this.start), toVec3d(this.start.offset(size.getX(), 0, 0)), color);  // Changed
        this.edges[1] = new Line(toVec3d(this.start), toVec3d(this.start.offset(0, size.getY(), 0)), color);  // Changed
        this.edges[2] = new Line(toVec3d(this.start), toVec3d(this.start.offset(0, 0, size.getZ())), color);  // Changed
        this.edges[3] = new Line(toVec3d(this.start.offset(size.getX(), 0, size.getZ())), toVec3d(this.start.offset(size.getX(), 0, 0)), color);  // Changed
        this.edges[4] = new Line(toVec3d(this.start.offset(size.getX(), 0, size.getZ())), toVec3d(this.start.offset(size.getX(), size.getY(), size.getZ())), color);  // Changed
        this.edges[5] = new Line(toVec3d(this.start.offset(size.getX(), 0, size.getZ())), toVec3d(this.start.offset(0, 0, size.getZ())), color);  // Changed
        this.edges[6] = new Line(toVec3d(this.start.offset(size.getX(), size.getY(), 0)), toVec3d(this.start.offset(size.getX(), 0, 0)), color);  // Changed
        this.edges[7] = new Line(toVec3d(this.start.offset(size.getX(), size.getY(), 0)), toVec3d(this.start.offset(0, size.getY(), 0)), color);  // Changed
        this.edges[8] = new Line(toVec3d(this.start.offset(size.getX(), size.getY(), 0)), toVec3d(this.start.offset(size.getX(), size.getY(), size.getZ())), color);  // Changed
        this.edges[9] = new Line(toVec3d(this.start.offset(0, size.getY(), size.getZ())), toVec3d(this.start.offset(0, 0, size.getZ())), color);  // Changed
        this.edges[10] = new Line(toVec3d(this.start.offset(0, size.getY(), size.getZ())), toVec3d(this.start.offset(0, size.getY(), 0)), color);  // Changed
        this.edges[11] = new Line(toVec3d(this.start.offset(0, size.getY(), size.getZ())), toVec3d(this.start.offset(size.getX(), size.getY(), size.getZ())), color);  // Changed
    }

    @Override
    public void render(Matrix4f matrix, float tickDelta) {  // Added parameters
        if(this.start == null || this.size == null || this.edges == null) return;

        for(Line edge: this.edges) {
            if(edge == null) continue;
            edge.render(matrix, tickDelta);  // Pass parameters
        }
    }

    @Override
    public BlockPos getPos() {
        return this.start.offset(size.getX() / 2, size.getY() / 2, size.getZ() / 2);  // Changed
    }
}
