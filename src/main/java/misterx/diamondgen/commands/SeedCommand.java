package misterx.diamondgen.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.arguments.LongArgumentType;  // Added explicit import
import misterx.diamondgen.DiamondGen;
import net.minecraft.commands.CommandSourceStack;  // Changed
import net.minecraft.ChatFormatting;  // Added

import static net.minecraft.commands.Commands.argument;  // Changed
import static com.mojang.brigadier.arguments.LongArgumentType.longArg;
import static com.mojang.brigadier.arguments.LongArgumentType.getLong;

public class SeedCommand extends ClientCommand {
    @Override
    public String getName() {
        return "seed";
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSourceStack> builder) {  // Changed
        builder.then(argument("seed", longArg())
                .executes(ctx -> setSeed(getLong(ctx, "seed"))));  // Fixed case
    }

    private static int setSeed(long seed) {
        DiamondGen.clear(seed);
        ClientCommand.sendFeedback("Set seed to: " + seed, ChatFormatting.GOLD, false);
        return 1;
    }
}