package misterx.diamondgen.mixin;

import misterx.diamondgen.render.RenderQueue;
import net.minecraft.client.renderer.GameRenderer;  // Changed import
import net.minecraft.client.Camera;  // Changed import
import net.minecraft.client.renderer.LevelRenderer;  // LightmapTextureManager is now part of LevelRenderer
import com.mojang.blaze3d.vertex.PoseStack;  // Changed from MatrixStack
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void diamondgen$renderLevelStart(
            float tickDelta,
            long startTime,
            PoseStack matrices,  // Changed
            Camera camera,
            GameRenderer gameRenderer,
            LevelRenderer levelRenderer,  // Changed
            Matrix4f projectionMatrix,
            CallbackInfo ci
    ) {
        RenderQueue.get().setTrackRender(matrices);
    }

    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void diamondgen$renderLevelEnd(
            float tickDelta,
            long startTime,
            PoseStack matrices,  // Changed
            Camera camera,
            GameRenderer gameRenderer,
            LevelRenderer levelRenderer,  // Changed
            Matrix4f projectionMatrix,
            CallbackInfo ci
    ) {
        RenderQueue.get().setTrackRender(null);
    }
}