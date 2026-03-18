package misterx.diamondgen;

import misterx.diamondgen.render.Color;
import misterx.diamondgen.render.Cube;
import misterx.diamondgen.render.Renderer;
import net.minecraft.core.BlockPos;  // Changed
import net.minecraft.core.Vec3i;     // Changed
import net.minecraft.core.BlockPos.MutableBlockPos;  // Changed

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimDebrisGen {

    public List<Renderer> renderers = new ArrayList<>();

    public boolean generate(Random random, BlockPos blockPos, int size) {
        int i = random.nextInt(size + 1);
        MutableBlockPos mutable = new MutableBlockPos();  // Changed

        for(int j = 0; j < i; ++j) {
            this.getStartPos(mutable, random, blockPos, Math.min(j, 7));
            renderers.add(new Cube(mutable.immutable(), new Color(255, 255, 0)));  // Make immutable
        }

        return true;
    }

    private void getStartPos(MutableBlockPos mutable, Random random, BlockPos pos, int size) {
        int i = this.randomCoord(random, size);
        int j = this.randomCoord(random, size);
        int k = this.randomCoord(random, size);
        mutable.set(pos.getX() + i, pos.getY() + j, pos.getZ() + k);  // Changed from Vec3i casting
    }

    private int randomCoord(Random random, int size) {
        return Math.round((random.nextFloat() - random.nextFloat()) * (float)size);
    }
}