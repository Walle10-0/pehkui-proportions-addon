package com.walle.pehkuiproportions;

//import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;

public class ScaleModel {
    //private static LocalPlayer player = Minecraft.getInstance().player;

    public static void modifyEntity(LivingEntity entity, HumanoidModel<?> model)
    {
        float bodySizeY = 12F;
        float bodySizeX = 4F;
        float armSizeX = 1F;
        //float legSizeY = 12F;
        float legSizeX = 2F;

        float headScale = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.HEAD);

        float bodyX = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.TORSO_X);
        float bodyY = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.TORSO_Y);
        float bodyZ = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.TORSO_Z);

        float armX = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.ARM_X);
        float armY = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.ARM_Y);
        float armZ = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.ARM_Z);

        float legX = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.LEG_X);
        float legY = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.LEG_Y);
        float legZ = PehkuiScaleTypes.getScale(entity, PehkuiScaleTypes.LEG_Z);

        float bodyOffset = bodySizeY * (1-bodyY);
        float armOffset = bodySizeX * (1 - bodyX) + armSizeX * (1 - armX);
        float legOffsetY = model.leftLeg.y * (1-legY);
        float legOffsetX = legSizeX * legX;

        applyScale(model.head, headScale);

        applyScale(model.body, bodyX, bodyY, bodyZ);

        applyScale(model.rightArm, armX, armY, armZ);
        applyScale(model.leftArm, armX, armY, armZ);

        applyScale(model.rightLeg, legX, legY, legZ);
        applyScale(model.leftLeg, legX, legY, legZ);

        model.head.y += bodyOffset + legOffsetY;
        model.body.y += bodyOffset + legOffsetY;
        model.leftArm.y += bodyOffset + legOffsetY;
        model.leftArm.x -= armOffset;
        model.rightArm.y += bodyOffset + legOffsetY;
        model.rightArm.x += armOffset;
        model.leftLeg.y += legOffsetY;
        model.rightLeg.y += legOffsetY;
        // stop exponential growth
        model.leftLeg.x = legOffsetX;
        model.rightLeg.x = -legOffsetX;
    }

    private static void applyScale(ModelPart part, float scale)
    {
        applyScale(part, scale, scale, scale);
    }

    private static void applyScale(ModelPart part, float x, float y, float z)
    {
        part.xScale = x;
        part.yScale = y;
        part.zScale = z;
    }
}
