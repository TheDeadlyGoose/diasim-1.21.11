package misterx.diamondgen.render;

import net.minecraft.client.Minecraft;  // Changed
import net.minecraft.core.BlockPos;     // Changed
import net.minecraft.world.phys.Vec3;   // Changed

public abstract class Renderer {

    protected Minecraft mc = Minecraft.getInstance();  // Changed

    public abstract void render();

    public abstract BlockPos getPos();

    protected Vec3 toVec3d(BlockPos pos) {  // Changed return type
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());  // Changed constructor
    }
}