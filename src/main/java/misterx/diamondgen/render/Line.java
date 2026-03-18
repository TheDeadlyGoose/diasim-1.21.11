package misterx.diamondgen.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;  // Changed
import net.minecraft.client.renderer.RenderType;        // Changed
import net.minecraft.client.renderer.ShaderInstance;    // Changed
import net.minecraft.core.BlockPos;                     // Changed
import net.minecraft.world.phys.Vec3;                   // Changed
import org.joml.Matrix4f;                               // New for 1.21.11
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class Line extends Renderer {

    public Vec3 start;
    public Vec3 end;  // Changed from Vec3d
    public Color color;

    public Line() {
        this(Vec3.ZERO, Vec3.ZERO, Color.WHITE);  // Changed
    }

    public Line(Vec3 start, Vec3 end) {  // Changed parameter types
        this(start, end, Color.WHITE);
    }

    public Line(Vec3 start, Vec3 end, Color color) {  // Changed parameter types
        this.start = start;
        this.end = end;
        this.color = color;
    }

    @Override
    public void render() {
        if(this.start == null || this.end == null || this.color == null) return;

        Vec3 camPos = this.mc.gameRenderer.getMainCamera().getPosition();  // Changed
        
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();  // Changed from enableLineSmooth
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(2.0f);
        
        // In 1.21.11, rendering is done through MultiBufferSource
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.LINES);
        
        Matrix4f matrix = new Matrix4f();  // You'll need to get the proper matrix from context

        // Put the start and end vertices in the buffer
        this.putVertex(buffer, matrix, camPos, this.start);
        this.putVertex(buffer, matrix, camPos, this.end);

        // Draw it all
        bufferSource.endBatch();
        
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    protected void putVertex(VertexConsumer buffer, Matrix4f matrix, Vec3 camPos, Vec3 pos) {
        buffer.addVertex(
                matrix,
                (float)(pos.x - camPos.x),  // Changed from getX()
                (float)(pos.y - camPos.y),  // Changed from getY()
                (float)(pos.z - camPos.z)   // Changed from getZ()
        ).setColor(
                this.color.getFRed(),
                this.color.getFGreen(),
                this.color.getFBlue(),
                1.0F
        ).setNormal(0, 1, 0);  // Need to add normal
    }

    @Override
    public BlockPos getPos() {
        double x = (this.end.x - this.start.x) / 2 + this.start.x;  // Changed
        double y = (this.end.y - this.start.y) / 2 + this.start.y;  // Changed
        double z = (this.end.z - this.start.z) / 2 + this.start.z;  // Changed
        return new BlockPos((int)x, (int)y, (int)z);
    }
}