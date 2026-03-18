package misterx.diamondgen.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.arguments.IntegerArgumentType;  // Added explicit import
import misterx.diamondgen.DiamondGen;
import net.minecraft.commands.CommandSourceStack;  // Changed
import net.minecraft.ChatFormatting;  // Added

import static net.minecraft.commands.Commands.argument;  // Changed
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;

public class RangeCommand extends ClientCommand {

    @Override
    public String getName() {
        return "range";
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSourceStack> builder) {  // Changed
        builder.then(argument("range", integer(1, 256))  // Added min/max values
                .executes(ctx -> setRange(getInteger(ctx, "range"))));  // Fixed case
    }

    private static int setRange(int range) {
        DiamondGen.range = range;
        ClientCommand.sendFeedback("Updated range to: " + range, ChatFormatting.AQUA, false);
        return 1;
    }
}