package com.walle.pehkuiproportions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import virtuoel.pehkui.api.ScaleModifier;
//import virtuoel.pehkui.api.ScaleModifiers;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.util.ScaleUtils;

public class PehkuiScaleTypes {
    public static final ScaleType BODY_HEIGHT = registerMyScale("body_height", null, PehkuiScaleModifiers.BODY_HEIGHT_MOD);
    public static final ScaleType BODY_DEPTH = registerMyScale("body_depth", null, PehkuiScaleModifiers.BODY_DEPTH_MOD);
    public static final ScaleType ARM_WIDTH = registerMyScale("arm_width", null, PehkuiScaleModifiers.ARM_WIDTH_MOD);
    public static final ScaleType LEG_WIDTH = registerMyScale("leg_width", null, PehkuiScaleModifiers.LEG_WIDTH_MOD);

    public static final ScaleType HEAD = registerMyScale("head", null, PehkuiScaleModifiers.EFFECTIVE_HEIGHT);

    public static final ScaleType TORSO_X = registerMyScale("torso_x", null);
    public static final ScaleType TORSO_Y = registerMyScale("torso_y", PehkuiScaleModifiers.BODY_HEIGHT_MOD, PehkuiScaleModifiers.EFFECTIVE_HEIGHT);
    public static final ScaleType TORSO_Z = registerMyScale("torso_z", PehkuiScaleModifiers.BODY_DEPTH_MOD);
    
    public static final ScaleType ARM_X = registerMyScale("arm_x", PehkuiScaleModifiers.ARM_WIDTH_MOD);
    public static final ScaleType ARM_Y = registerMyScale("arm_y", PehkuiScaleModifiers.BODY_HEIGHT_MOD);
    public static final ScaleType ARM_Z = registerMyScale("arm_z", PehkuiScaleModifiers.ARM_DEPTH_MOD);

    public static final ScaleType LEG_X = registerMyScale("leg_x", PehkuiScaleModifiers.LEG_WIDTH_MOD);
    public static final ScaleType LEG_Y = registerMyScale("leg_y", PehkuiScaleModifiers.BODY_HEIGHT_MOD, PehkuiScaleModifiers.EFFECTIVE_HEIGHT);
    public static final ScaleType LEG_Z = registerMyScale("leg_z", PehkuiScaleModifiers.LEG_DEPTH_MOD);

    public static void init() {}
    
    private static ScaleType registerMyScale(String name, ScaleModifier valueModifier, ScaleModifier... dependentModifiers)
    {
        ScaleType.Builder bob_the_builder = ScaleType.Builder.create();

        if (valueModifier != null)
		{
			bob_the_builder.addBaseValueModifier(valueModifier);
		}
		
		for (ScaleModifier scaleModifier : dependentModifiers)
		{
			bob_the_builder.addDependentModifier(scaleModifier);
		}

        return ScaleRegistries.register(
			ScaleRegistries.SCALE_TYPES,
			ResourceLocation.fromNamespaceAndPath(pehkuiproportions.MODID, name),
			bob_the_builder.build()
		);
    }

    public static float getScale(Entity entity, ScaleType myscale)
    {
        return ScaleUtils.getTypedScale(entity, myscale, 1.0F);
    }
}