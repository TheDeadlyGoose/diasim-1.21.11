package misterx.diamondgen.mixin;

import misterx.diamondgen.SimOreGen;
import net.minecraft.resources.ResourceLocation;  // Changed
import net.minecraft.world.level.dimension.DimensionType;  // Changed
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DimensionType.class)
public class DimensionTypeMixin implements SimOreGen.DimensionTypeCaller {

    @Shadow @Final private ResourceLocation infiniburn;  // Changed from Identifier
    
    @Override
    public ResourceLocation getInfiniburn() {  // Changed return type
        return this.infiniburn;
    }
}