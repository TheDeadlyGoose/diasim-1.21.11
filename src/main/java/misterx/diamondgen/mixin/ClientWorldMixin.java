package misterx.diamondgen.mixin;

import misterx.diamondgen.DiamondGen;
import net.minecraft.client.multiplayer.ClientLevel;  // Changed
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)  // Changed
public class ClientWorldMixin {
    @Inject(method = "disconnect", at = @At("HEAD"))
    private void disconnect(CallbackInfo ci) {
        DiamondGen.clear(DiamondGen.gen.currentSeed);
    }
}