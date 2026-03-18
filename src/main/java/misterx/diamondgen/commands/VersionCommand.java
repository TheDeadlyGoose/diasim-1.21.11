package misterx.diamondgen.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import misterx.diamondgen.DiamondGen;
import net.minecraft.commands.CommandSourceStack;  // Changed
import net.minecraft.ChatFormatting;  // Added

import static net.minecraft.commands.Commands.literal;  // Changed

public class VersionCommand extends ClientCommand {
    @Override
    public String getName() {
        return "version";
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSourceStack> builder) {  // Changed
        builder.then(literal("1.16").executes(context -> setVersion("1.16")))
                .then(literal("1.15").executes(context -> setVersion("1.15")));
    }

    public int setVersion(String ver) {
        String version = ver.equals("1.16") ? "1.16" : "1.15";
        DiamondGen.ver = version;
        DiamondGen.clear(DiamondGen.gen.currentSeed);
        ClientCommand.sendFeedback("Set version to: " + version, ChatFormatting.LIGHT_PURPLE, false);
        return 1;
    }
}