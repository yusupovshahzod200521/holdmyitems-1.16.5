package uz.shahzod.holdmyitems;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HoldMyItems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class HandAnimationHandler {

    private static float sneakSmooth = 0f;
    private static float walkSmooth = 0f;

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (event.getHand() != Hand.MAIN_HAND) return;

        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;

        MatrixStack ms = event.getMatrixStack();
        float pt = event.getPartialTicks();
        float time = (player.tickCount + pt) * 0.08f;

        ms.translate(0.0D, MathHelper.sin(time) * 0.006D, 0.0D);
        ms.mulPose(Vector3f.XP.rotationDegrees(MathHelper.cos(time) * 0.8F));

        Vector3d motion = player.getDeltaMovement();
        float speed = (float) Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        float walkTarget = player.isOnGround() && speed > 0.05F ? 1F : 0F;
        walkSmooth += (walkTarget - walkSmooth) * 0.1F;
        float walkTime = (player.tickCount + pt) * 0.6F;
        ms.translate(MathHelper.cos(walkTime * 0.5F) * 0.012D * walkSmooth,
                     Math.abs(MathHelper.sin(walkTime)) * 0.015D * walkSmooth, 0.0D);
        ms.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.sin(walkTime) * 1.5F * walkSmooth));

        float sneakTarget = player.isCrouching() ? 1F : 0F;
        sneakSmooth += (sneakTarget - sneakSmooth) * 0.12F;
        ms.translate(0.0D, -0.08D * sneakSmooth, 0.0D);
        ms.mulPose(Vector3f.XP.rotationDegrees(5F * sneakSmooth));

        float swing = event.getSwingProgress();
        if (swing > 0F) {
            ms.mulPose(Vector3f.ZP.rotationDegrees(-12F * MathHelper.sin(swing * (float) Math.PI)));
        }
    }
}
