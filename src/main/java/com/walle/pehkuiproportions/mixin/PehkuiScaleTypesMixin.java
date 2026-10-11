package com.walle.pehkuiproportions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import net.minecraft.resources.ResourceLocation;

import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.api.ScaleModifier;
//import virtuoel.pehkui.api.ScaleModifiers;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;

import com.walle.pehkuiproportions.PehkuiScaleModifiers;

@Mixin(ScaleTypes.class)
public class PehkuiScaleTypesMixin {

    @Shadow
    private static ScaleType register(ResourceLocation id, ScaleType.Builder builder)
    {
        throw new AssertionError();
    }

    @WrapOperation(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lvirtuoel/pehkui/api/ScaleTypes;registerDimensionScale(Ljava/lang/String;Lvirtuoel/pehkui/api/ScaleModifier;[Lvirtuoel/pehkui/api/ScaleModifier;)Lvirtuoel/pehkui/api/ScaleType;"
        )
    )
    private static ScaleType wrapRegisterDimensionScale(
        String path,
        ScaleModifier modifier,
        ScaleModifier[] dependentModifiers,
        Operation<ScaleType> original
    ) {
        if ("hitbox_height".equals(path) || "eye_height".equals(path)) {
            return registerComplexDimensionScale(
                path,
                modifier,
                PehkuiScaleModifiers.EFFECTIVE_HEIGHT
            );
        }

        return original.call(path, modifier, dependentModifiers);
    }

    @Unique
    private static ScaleType registerComplexDimensionScale(String path, ScaleModifier... valueModifier)
	{
        final ScaleType.Builder builder = ScaleType.Builder.create()
            .affectsDimensions();
            
        for (ScaleModifier scaleModifier : valueModifier)
        {
            builder.addBaseValueModifier(scaleModifier);
        }
            
        return register(Pehkui.id(path), builder);
    }
}
