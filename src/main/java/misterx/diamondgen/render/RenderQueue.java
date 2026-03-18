package misterx.diamondgen.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Matrix4f;  // Changed from MatrixStack

import java.util.*;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class RenderQueue {
    // Class body remains the same, but change Consumer<MatrixStack> to Consumer<Matrix4f>
    private final Map<String, List<Consumer<Matrix4f>>> typeRunnableMap = new HashMap<>();
    private Matrix4f matrix = null;

    public void add(String type, Consumer<Matrix4f> runnable) {
        // ... same logic
    }

    public void onRender(String type) {
        if (this.matrix == null) return;
        List<Consumer<Matrix4f>> list = this.typeRunnableMap.get(type);
        if (list == null) return;
        list.forEach(r -> r.accept(this.matrix));
    }
}