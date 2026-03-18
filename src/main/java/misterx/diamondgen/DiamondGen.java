package misterx.diamondgen;

import misterx.diamondgen.render.RenderMain;
import misterx.diamondgen.render.RenderQueue;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;  // Changed

public class DiamondGen implements ClientModInitializer {

    public static int range = 100;
    public static boolean active = true;
    public static String ver = "1.21.11";  // Updated default version

    private static boolean opaque = false;

    public static StartGen gen = new StartGen(0);

    public static boolean isOpaque() {
        return opaque;
    }

    public static void setOpaque(boolean opaque) {
        DiamondGen.opaque = opaque;
    }

    @Override
    public void onInitializeClient() {
        clear(0);
        // In 1.21.11, render hooks are different - we'll need to register differently
        // This will be handled in a mixin or event listener
        // RenderQueue.get().add("hand", RenderMain.get()::renderFinders);
    }

    public static void clear(long seed) {
        gen = new StartGen(seed);

        if (Minecraft.getInstance().player == null) {  // Changed
            return;
        }

        Util.reload();
    }
}