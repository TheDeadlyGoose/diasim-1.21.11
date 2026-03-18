package misterx.diamondgen.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import misterx.diamondgen.init.ClientCommands;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

import static net.minecraft.commands.Commands.literal;

@Environment(EnvType.CLIENT)
public abstract class ClientCommand {

    public abstract String getName();

    public abstract void build(LiteralArgumentBuilder<CommandSourceStack> builder);

    public static void sendFeedback(String message, ChatFormatting color, boolean overlay) {
        try {
            Minecraft.getInstance().player.sendSystemMessage(
                Component.literal(message).withStyle(color)
            );
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = literal(this.getName());
        this.build(builder);
        dispatcher.register(literal(ClientCommands.PREFIX).then(builder));
    }
}