package misterx.diamondgen.mixin;

import com.mojang.brigadier.StringReader;
import misterx.diamondgen.init.ClientCommands;
import net.minecraft.client.player.LocalPlayer;  // Changed
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.regex.Pattern;

@Mixin(LocalPlayer.class)  // Changed
public class ClientPlayerEntityMixin {
    @Inject(method = "chat", at = @At("HEAD"), cancellable = true)  // Method might be renamed to "chat"
    private void onSendChatMessage(String message, CallbackInfo ci) {
        if(message.startsWith("/")) {
            StringReader reader = new StringReader(message);
            reader.skip();
            int cursor = reader.getCursor();
            reader.setCursor(cursor);
            if(ClientCommands.isClientSideCommand(message.substring(1).split(Pattern.quote(" ")))) {
                ClientCommands.executeCommand(reader);
                ci.cancel();
            }
        }
    }
}