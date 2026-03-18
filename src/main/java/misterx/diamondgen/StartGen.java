package misterx.diamondgen;

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
    MCVersion version = MCVersion.v1_21;

    // Ancient debris uses uniform decorators in modern versions
    int decoratorStep = 7;
    int decoratorIndex1 = 15;
    int decoratorIndex2 = 16;

    Random random = new Random();

    // First vein
    long seed1 = ChunkSeeds.getDecoratorSeed(
            currentSeed, blockX, blockZ, decoratorIndex1, decoratorStep, version
    );
    random.setSeed(seed1);

    int x = random.nextInt(16) + blockX;
    int z = random.nextInt(16) + blockZ;
    int y = 8 + random.nextInt(112); // y = 8–119

    simDebrisGen.generate(random, new BlockPos(x, y, z), 3);

    // Second vein
    long seed2 = ChunkSeeds.getDecoratorSeed(
            currentSeed, blockX, blockZ, decoratorIndex2, decoratorStep, version
    );
    random.setSeed(seed2);

    x = random.nextInt(16) + blockX;
    z = random.nextInt(16) + blockZ;
    y = 8 + random.nextInt(112);

    simDebrisGen.generate(random, new BlockPos(x, y, z), 2);
}
