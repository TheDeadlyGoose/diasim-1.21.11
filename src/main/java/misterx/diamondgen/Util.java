package misterx.diamondgen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;  // Changed
import net.minecraft.core.BlockPos;     // Changed
import net.minecraft.world.level.block.state.BlockState;  // Added

@Environment(EnvType.CLIENT)
public class Util {
    private static final Minecraft client = Minecraft.getInstance();  // Changed

    public static int distanceToPlayer(BlockPos posOre) {
        if(client.player != null) {
            BlockPos posPlayer = client.player.blockPosition();  // Changed

            int x = Math.abs(posPlayer.getX() - posOre.getX());
            int z = Math.abs(posPlayer.getZ() - posOre.getZ());

            return x + z;
        }
        return 1001;
    }
    
    public static void reload() {
        if (client.player == null || client.level == null) return;  // Added null check
        
        int renderDistance = client.options.renderDistance().get();  // Changed
        
        int playerChunkX = (int) (Math.round(client.player.getX()) >> 4);
        int playerChunkZ = (int) (Math.round(client.player.getZ()) >> 4);
        
        for(int i = playerChunkX - renderDistance; i < playerChunkX + renderDistance; i++) {
            for(int j = playerChunkZ - renderDistance; j < playerChunkZ + renderDistance; j++) {
                DiamondGen.gen.getStartingPos(i << 4, j << 4);
            }
        }
    }
    
    public static boolean isOpaque(BlockPos pos) {
        if (client.level != null) {  // Changed from DiamondGen.gen.world
            BlockState state = client.level.getBlockState(pos);
            return state.isSolid();  // Changed - isOpaque() doesn't exist in 1.21.11
            // Alternative: state.isSolidRender(client.level, pos)
        }
        return false;
    }
    
    // Helper method for opacity check
    public static boolean isSolidRender(BlockPos pos) {
        if (client.level != null) {
            BlockState state = client.level.getBlockState(pos);
            return state.isSolidRender(client.level, pos);
        }
        return false;
    }
}