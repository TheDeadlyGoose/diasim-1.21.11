package misterx.diamondgen.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import misterx.diamondgen.DiamondGen;
import misterx.diamondgen.init.ClientCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;  // Changed
import net.minecraft.client.multiplayer.ClientPacketListener;  // Possibly renamed
import net.minecraft.commands.CommandSource;  // Changed
import net.minecraft.network.Connection;  // Changed
import net.minecraft.network.protocol.common.ClientboundCommandsPacket;  // Changed
import net.minecraft.network.protocol.game.ClientboundBundlePacket;  // Changed
import net.minecraft.network.protocol.game.ClientboundLoginPacket;  // Changed
import net.minecraft.commands.CommandSourceStack;  // Changed
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)  // Verify this class name
public class ClientPlayNetworkHandlerMixin {
    @Shadow private CommandDispatcher<CommandSource> commands;  // Changed field name

    @Shadow private Minecraft minecraft;  // Changed

    @SuppressWarnings("unchecked")
    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(Minecraft minecraft, Screen screen, Connection connection, GameProfile profile, CallbackInfo ci) {
        ClientCommands.registerCommands((CommandDispatcher<CommandSourceStack>)(Object)this.commands);
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "handleCommands", at = @At("TAIL"))  // Method name might be different
    public void onOnCommandTree(ClientboundCommandsPacket packet, CallbackInfo ci) {
        ClientCommands.registerCommands((CommandDispatcher<CommandSourceStack>)(Object)this.commands);
    }
    
    @Inject(method = "handleLogin", at = @At("TAIL"))  // Method name might be different
    private void onGameJoin(ClientboundLoginPacket packet, CallbackInfo ci){
        DiamondGen.clear(DiamondGen.gen.currentSeed);
    }
}