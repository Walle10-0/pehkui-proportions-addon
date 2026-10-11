package com.walle.pehkuiproportions;

import net.minecraft.resources.ResourceLocation;

import virtuoel.pehkui.api.ScaleModifier;
import virtuoel.pehkui.api.ScaleRegistries;
//import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.api.TypedScaleModifier;

public class PehkuiScaleModifiers {

    public static final ScaleModifier BODY_HEIGHT_MOD = registerMyModifier("body_height_multiplier", new TypedScaleModifier(() -> PehkuiScaleTypes.BODY_HEIGHT));
	public static final ScaleModifier BODY_DEPTH_MOD = registerMyModifier("body_depth_multiplier", new TypedScaleModifier(() -> PehkuiScaleTypes.BODY_DEPTH));
    public static final ScaleModifier ARM_WIDTH_MOD = registerMyModifier("arm_width_multiplier", new TypedScaleModifier(() -> PehkuiScaleTypes.ARM_WIDTH));
    public static final ScaleModifier LEG_WIDTH_MOD = registerMyModifier("leg_width_multiplier", new TypedScaleModifier(() -> PehkuiScaleTypes.LEG_WIDTH));

	// (8F / 32F) * headScale + (12F / 32F) * bodyY + (12F / 32F) * legY
	public static final ScaleModifier EFFECTIVE_HEIGHT = registerMyModifier("effective_height_multiplier",
		new WeightedScaleModifier(
			new WeightedScaleModifier.Entry(() -> PehkuiScaleTypes.HEAD, 8f),
			new WeightedScaleModifier.Entry(() -> PehkuiScaleTypes.TORSO_Y, 12f),
			new WeightedScaleModifier.Entry(() -> PehkuiScaleTypes.LEG_Y, 12f)
		)
	);
	public static final ScaleModifier ARM_DEPTH_MOD = registerMyModifier("arm_depth_multiplier",
		new CompoundScaleModifier(
			new CompoundScaleModifier.Entry(() -> PehkuiScaleTypes.ARM_WIDTH),
			new CompoundScaleModifier.Entry(() -> PehkuiScaleTypes.BODY_DEPTH)
		)
	);
	public static final ScaleModifier LEG_DEPTH_MOD = registerMyModifier("leg_depth_multiplier",
		new CompoundScaleModifier(
			new CompoundScaleModifier.Entry(() -> PehkuiScaleTypes.LEG_WIDTH),
			new CompoundScaleModifier.Entry(() -> PehkuiScaleTypes.BODY_DEPTH)
		)
	);

    public static void init() {}

    private static ScaleModifier registerMyModifier(String name, ScaleModifier scaleModifier)
	{
		return ScaleRegistries.register(
			ScaleRegistries.SCALE_MODIFIERS,
			ResourceLocation.fromNamespaceAndPath(pehkuiproportions.MODID, name),
			scaleModifier
		);
	}
}