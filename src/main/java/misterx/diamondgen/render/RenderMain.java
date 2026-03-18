package misterx.diamondgen.render;

import com.mojang.blaze3d.systems.RenderSystem;
import misterx.diamondgen.DiamondGen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.ShaderInstance;  // Changed
import org.joml.Matrix4f;  // New

@Environment(EnvType.CLIENT)
public class RenderMain {

    private static final RenderMain INSTANCE = new RenderMain();

    public static RenderMain get() {
        return INSTANCE;
    }

    public void renderFinders(Matrix4f matrix, float tickDelta) {  // Changed parameter
        // Save render state
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();

        // In 1.21.11, shader handling is different
        // You'll need to get the shader from the render system
        RenderSystem.setShader(() -> GameRenderer.getPositionColorShader());

        // Delegate actual rendering - you'll need to pass the matrix
        DiamondGen.gen.simOreGen.render(matrix, tickDelta);  // You'll need to update simOreGen

        // Restore render state
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}