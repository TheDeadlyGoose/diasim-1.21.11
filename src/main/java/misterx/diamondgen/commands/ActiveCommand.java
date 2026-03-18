package misterx.diamondgen.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import misterx.diamondgen.DiamondGen;
import net.minecraft.commands.CommandSourceStack;  // Changed

import static net.minecraft.commands.Commands.literal;  // Changed

public class ActiveCommand extends ClientCommand {
    @Override
    public String getName() {
        return "active";
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSourceStack> builder) {  // Changed
        builder.then(literal("ON").executes(context -> setActive(true)))
                .then(literal("OFF").executes(context -> setActive(false)));
    }

    private static int setActive(boolean active) {
        DiamondGen.active = active;
        ClientCommand.sendFeedback("DiamondSim is now: " + (active ? "ON" : "OFF"), 
            active ? ChatFormatting.GREEN : ChatFormatting.RED, false);  // Added feedback
        return 1;  // Return 1 for success
    }
}