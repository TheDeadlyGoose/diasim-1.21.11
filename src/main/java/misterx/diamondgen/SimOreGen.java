package misterx.diamondgen;

import misterx.diamondgen.render.Color;
import misterx.diamondgen.render.Cube;
import misterx.diamondgen.render.Renderer;
import net.minecraft.client.Minecraft;  // Changed
import net.minecraft.client.multiplayer.ClientLevel;  // Changed
import net.minecraft.core.BlockPos;  // Changed
import net.minecraft.util.Mth;  // Changed from MathHelper
import net.minecraft.world.level.Level;  // Changed from World
import net.minecraft.world.phys.Vec3;  // Changed from Vec3d
import net.minecraft.core.BlockPos.MutableBlockPos;  // Changed

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4f;  // New for 1.21.11 rendering

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class SimOreGen {

    public final List<Renderer> renderers = new ArrayList<>();

    /* ================= RENDER ================= */

    public void render(Matrix4f matrix, float tickDelta) {  // Changed parameter
        if (!DiamondGen.active) return;

        ClientLevel world = Minecraft.getInstance().level;  // Changed
        if (world == null) return;

        try {
            if (isOverworld(world)) {
                for (Renderer renderer : this.renderers) {
                    if (Util.distanceToPlayer(renderer.getPos()) < DiamondGen.range) {
                        if (!DiamondGen.isOpaque() || Util.isOpaque(renderer.getPos())) {
                            // Pass matrix to renderer
                            renderer.render(matrix, tickDelta);  // You'll need to update Renderer interface
                        }
                    }
                }
            } else if (isNether(world)) {
                for (Renderer renderer : DiamondGen.gen.simDebrisGen.renderers) {
                    if (Util.distanceToPlayer(renderer.getPos()) < DiamondGen.range) {
                        if (!DiamondGen.isOpaque() || Util.isOpaque(renderer.getPos())) {
                            renderer.render(matrix, tickDelta);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= GENERATION ================= */

    public boolean generate(Random random, BlockPos blockPos, ClientLevel world, int size) {  // Changed
        float f = random.nextFloat() * (float) Math.PI;
        float g = size / 8.0F;
        int i = Mth.ceil((size / 16.0F * 2.0F + 1.0F) / 2.0F);  // Changed

        double d = blockPos.getX() + Math.sin(f) * g;
        double e = blockPos.getX() - Math.sin(f) * g;
        double h = blockPos.getZ() + Math.cos(f) * g;
        double j = blockPos.getZ() - Math.cos(f) * g;

        double l = blockPos.getY() + random.nextInt(3) - 2;
        double m = blockPos.getY() + random.nextInt(3) - 2;

        int n = blockPos.getX() - Mth.ceil(g) - i;  // Changed
        int o = blockPos.getY() - 2 - i;
        int p = blockPos.getZ() - Mth.ceil(g) - i;  // Changed

        int q = 2 * (Mth.ceil(g) + i);  // Changed
        int r = 2 * (2 + i);

        return generateVeinPart(
                random, d, e, h, j, l, m,
                n, o, p, q, r, world, size
        );
    }

    protected boolean generateVeinPart(
            Random random,
            double startX, double endX,
            double startZ, double endZ,
            double startY, double endY,
            int x, int y, int z,
            int size, int height,
            ClientLevel world,  // Changed
            int veinSize
    ) {
        int placed = 0;
        BitSet bitSet = new BitSet(size * height * size);
        MutableBlockPos mutable = new MutableBlockPos();  // Changed

        double[] points = new double[veinSize * 4];

        for (int i = 0; i < veinSize; i++) {
            float t = (float) i / veinSize;
            double px = Mth.lerp(t, startX, endX);  // Changed
            double py = Mth.lerp(t, startY, endY);  // Changed
            double pz = Mth.lerp(t, startZ, endZ);  // Changed

            double radius = random.nextDouble() * veinSize / 16.0D;
            double sizeFactor = ((Mth.sin((float) Math.PI * t) + 1.0F) * radius + 1.0D) / 2.0D;  // Changed

            int idx = i * 4;
            points[idx]     = px;
            points[idx + 1] = py;
            points[idx + 2] = pz;
            points[idx + 3] = sizeFactor;
        }

        for (int i = 0; i < veinSize; i++) {
            double radius = points[i * 4 + 3];
            if (radius < 0) continue;

            double cx = points[i * 4];
            double cy = points[i * 4 + 1];
            double cz = points[i * 4 + 2];

            int minX = Mth.floor(cx - radius);  // Changed
            int minY = Mth.floor(cy - radius);  // Changed
            int minZ = Mth.floor(cz - radius);  // Changed
            int maxX = Mth.floor(cx + radius);  // Changed
            int maxY = Mth.floor(cy + radius);  // Changed
            int maxZ = Mth.floor(cz + radius);  // Changed

            for (int bx = minX; bx <= maxX; bx++) {
                double dx = (bx + 0.5D - cx) / radius;
                if (dx * dx >= 1.0D) continue;

                for (int by = minY; by <= maxY; by++) {
                    double dy = (by + 0.5D - cy) / radius;
                    if (dx * dx + dy * dy >= 1.0D) continue;

                    for (int bz = minZ; bz <= maxZ; bz++) {
                        double dz = (bz + 0.5D - cz) / radius;
                        if (dx * dx + dy * dy + dz * dz >= 1.0D) continue;

                        int index = (bx - x) + (by - y) * size + (bz - z) * size * height;
                        if (bitSet.get(index)) continue;

                        bitSet.set(index);
                        mutable.set(bx, by, bz);  // Works the same

                        if (by > world.getMinY() && by < world.getMaxY()) {  // Check world bounds
                            this.renderers.add(
                                    new Cube(mutable.immutable(), new Color(255, 0, 0))  // Make immutable copy
                            );
                            placed++;
                        }
                    }
                }
            }
        }

        return placed > 0;
    }

    /* ================= DIMENSION HELPERS ================= */

    private boolean isOverworld(Level world) {  // Changed
        return world.dimension() == Level.OVERWORLD;  // Changed
    }

    private boolean isNether(Level world) {  // Changed
        return world.dimension() == Level.NETHER;  // Changed
    }

    // Interface for DimensionType mixin
    public interface DimensionTypeCaller {
        // This will need updating based on your DimensionTypeMixin
    }
}