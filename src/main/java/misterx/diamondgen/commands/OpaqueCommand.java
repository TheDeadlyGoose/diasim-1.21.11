package misterx.diamondgen.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import misterx.diamondgen.DiamondGen;
import net.minecraft.commands.CommandSourceStack;  // Changed
import net.minecraft.ChatFormatting;  // Added

import static net.minecraft.commands.Commands.literal;  // Changed

public class OpaqueCommand extends ClientCommand {
    @Override
    public String getName() {
        return "checkOpaque";
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSourceStack> builder) {  // Changed
        builder.then(literal("ON").executes(context -> setOpaque(true)))
                .then(literal("OFF").executes(context -> setOpaque(false)));
    }

    private int setOpaque(boolean checkOpaque) {
        DiamondGen.setOpaque(checkOpaque);
        ClientCommand.sendFeedback("Check opaque blocks: " + checkOpaque, 
            checkOpaque ? ChatFormatting.GREEN : ChatFormatting.RED, false);
        return 1;
    }
}