package com.walle.pehkuiproportions;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
//import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.LivingEntity;

import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class ModCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("scale")
            .then(
                Commands.literal("proportions")
                .then(
                    Commands.literal("dwarfscale")
                    .then(
                        Commands.literal("set")
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f, 10f))
                            .executes(ModCommands::runDwarfScaleSelf)
                        )
                        .then(Commands.argument("target", EntityArgument.entity())
                            .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f, 10f))
                                .executes(ModCommands::runDwarfScale)
                            )
                        )
                    )
                )
                .then(
                    Commands.literal("widescale")
                    .then(
                        Commands.literal("set")
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f, 10f))
                            .executes(ModCommands::runWideScaleSelf)
                        )
                        .then(Commands.argument("target", EntityArgument.entity())
                            .then(Commands.argument("value", FloatArgumentType.floatArg(0.1f, 10f))
                                .executes(ModCommands::runWideScale)
                            )
                        )
                    )
                )
            )
        );
    }

    private static int runDwarfScale(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        LivingEntity target = (LivingEntity)EntityArgument.getEntity(ctx, "target");
        float ratio = FloatArgumentType.getFloat(ctx, "value");
        return runDwarfScale(target, ratio);
    }

    private static int runDwarfScaleSelf(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        LivingEntity target = ctx.getSource().getPlayerOrException();
        float ratio = FloatArgumentType.getFloat(ctx, "value");
        return runDwarfScale(target, ratio);
    }

    private static int runDwarfScale(LivingEntity target, float ratio) {
        ScaleData data_1 = PehkuiScaleTypes.HEAD.getScaleData(target);
        ScaleData data_2 = PehkuiScaleTypes.BODY_HEIGHT.getScaleData(target);
        ScaleData data_3 = ScaleTypes.BASE.getScaleData(target);
        data_1.setTargetScale(1/ratio);
        data_2.setTargetScale((32F - 8F * (1/ratio)) / 24F);
        data_3.setTargetScale(ratio);
        return 1;
    }

    private static int runWideScale(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        LivingEntity target = (LivingEntity)EntityArgument.getEntity(ctx, "target");
        float ratio = FloatArgumentType.getFloat(ctx, "value");
        return runWideScale(target, ratio);
    }

    private static int runWideScaleSelf(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        LivingEntity target = ctx.getSource().getPlayerOrException();
        float ratio = FloatArgumentType.getFloat(ctx, "value");
        return runWideScale(target, ratio);
    }

    private static int runWideScale(LivingEntity target, float ratio) {
        ScaleData data_1 = PehkuiScaleTypes.ARM_WIDTH.getScaleData(target);
        ScaleData data_2 = PehkuiScaleTypes.LEG_WIDTH.getScaleData(target);
        ScaleData data_3 = PehkuiScaleTypes.TORSO_X.getScaleData(target);
        ScaleData data_4 = PehkuiScaleTypes.TORSO_Z.getScaleData(target);
        ScaleData data_5 = ScaleTypes.HITBOX_WIDTH.getScaleData(target);
        data_1.setTargetScale(ratio);
        data_2.setTargetScale(ratio);
        data_3.setTargetScale(ratio);
        data_4.setTargetScale(ratio);
        data_5.setTargetScale(ratio);
        return 1;
    }
}
