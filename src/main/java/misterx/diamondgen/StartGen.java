package misterx.diamondgen;

import kaptainwutax.biomeutils.Biome;
import kaptainwutax.biomeutils.source.NetherBiomeSource;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.mc.seed.ChunkSeeds;
import net.minecraft.client.Minecraft;  // Changed
import net.minecraft.client.multiplayer.ClientLevel;  // Changed
import net.minecraft.core.BlockPos;  // Changed
import net.minecraft.world.entity.player.Player;  // Changed

import java.util.Random;

public class StartGen {
    public SimOreGen simOreGen = new SimOreGen();
    public ClientLevel world = Minecraft.getInstance().level;  // Changed
    public Player player = Minecraft.getInstance().player;  // Changed
    public ChunkGenerated chunkList = new ChunkGenerated();
    public SimDebrisGen simDebrisGen = new SimDebrisGen();
    public long currentSeed = 0L;

    StartGen(long seed) {
        this.currentSeed = seed;
    }

    public void getStartingPos(int blockX, int blockZ) {  // Fixed parameter naming
        if(!chunkList.Check(blockX, blockZ)) {
            return;
        }
        
        // Update version check
        int step;
        if(DiamondGen.ver.equals("1.16")) {
            step = 6;
        } else if(DiamondGen.ver.equals("1.21")) {
            step = 4; // 1.21+ uses different decorator steps
        } else {
            step = 4; // Default for modern versions
        }
        
        // Use appropriate MCVersion for decorator seed
        MCVersion version = DiamondGen.ver.equals("1.16") ? MCVersion.v1_16_1 : MCVersion.v1_21;
        long decSeed = ChunkSeeds.getDecoratorSeed(this.currentSeed, blockX, blockZ, 9, step, version);
        
        Random random = new Random();
        random.setSeed(decSeed);
        
        int x = random.nextInt(16) + blockX;
        int z = random.nextInt(16) + blockZ;
        
        // In 1.21.11, world height is -64 to 320
        int minY = -64;
        int maxY = 320;
        int y = minY + random.nextInt(maxY - minY);  // Random y in world height
        
        BlockPos blockPos = new BlockPos(x, y, z);
        simOreGen.generate(random, blockPos, DiamondGen.gen.world, 8);
        
        // Only generate ancient debris in Nether
        if (world != null && world.dimension() == Level.NETHER) {
            ancientDebris(blockX, blockZ);
        }
    }

    public void ancientDebris(int blockX, int blockZ) {
        // For 1.21.11 Nether generation
        MCVersion version = DiamondGen.ver.equals("1.16") ? MCVersion.v1_16_1 : MCVersion.v1_21;
        NetherBiomeSource netherBiomeSource = new NetherBiomeSource(version, currentSeed);
        
        // Get biome at position
        Biome biome = netherBiomeSource.getBiome(blockX + 8, 0, blockZ + 8);
        
        // Ancient debris decorator index varies by biome in modern versions
        int index = 15; // Default
        
        if (biome != null) {
            String biomeName = biome.getName();
            if (biomeName.equals("warped_forest")) {
                index = 13;
            } else if (biomeName.equals("crimson_forest")) {
                index = 12;
            } else if (biomeName.equals("basalt_deltas")) {
                index = 14; // Adjust based on 1.21.11 biome names
            } else if (biomeName.equals("soul_sand_valley")) {
                index = 11; // Adjust based on 1.21.11 biome names
            }
        }
        
        // First vein
        long decSeed = ChunkSeeds.getDecoratorSeed(this.currentSeed, blockX, blockZ, index, 7, version);
        Random random = new Random();
        random.setSeed(decSeed);

        int x = random.nextInt(16) + blockX;
        int z = random.nextInt(16) + blockZ;
        
        // Ancient debris generates between y=8-119 in modern versions
        int y = 8 + random.nextInt(112);
        BlockPos blockPos = new BlockPos(x, y, z);
        simDebrisGen.generate(random, blockPos, 3);

        // Second vein (different index)
        decSeed = ChunkSeeds.getDecoratorSeed(this.currentSeed, blockX, blockZ, index + 1, 7, version);
        random.setSeed(decSeed);

        x = random.nextInt(16) + blockX;
        z = random.nextInt(16) + blockZ;
        y = 8 + random.nextInt(112);  // Updated range for 1.21.11
        BlockPos blockPos1 = new BlockPos(x, y, z);
        simDebrisGen.generate(random, blockPos1, 2);
    }
}