package misterx.diamondgen.mixin;

import misterx.diamondgen.render.RenderQueue;
import net.minecraft.util.profiling.InactiveProfiler;  // Try this instead
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InactiveProfiler.class)  // Changed
public abstract class DummyProfilerMixin {

    @Inject(method = "pop", at = @At("HEAD"))  // Method might be different
    private void onPop(String type, CallbackInfo ci) {
        RenderQueue.get().onRender(type);
    }
}