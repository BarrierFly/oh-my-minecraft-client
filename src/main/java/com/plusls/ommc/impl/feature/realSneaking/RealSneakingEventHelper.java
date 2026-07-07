package com.plusls.ommc.impl.feature.realSneaking;

import com.plusls.ommc.game.Configs;
import com.plusls.ommc.util.CompatGetUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import top.hendrixshen.magiclib.api.compat.minecraft.world.entity.LivingEntityCompat;

public class RealSneakingEventHelper {
    private static final float MIN_STEP_HEIGHT = 0.001F;
    private static float prevStepHeight;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(RealSneakingEventHelper::preClientTick);
    }

    private static void preClientTick(Minecraft minecraftClient) {
        if (minecraftClient.player != null) {
            LivingEntityCompat entityCompat = CompatGetUtil.getLivingEntityCompat(minecraftClient.player);
            float currentStepHeight = entityCompat.getMaxUpStep();

            if (Math.abs(currentStepHeight - MIN_STEP_HEIGHT) >= 0.00001) {
                prevStepHeight = currentStepHeight;
            }

            if (Configs.realSneaking.getBooleanValue() && minecraftClient.player.isShiftKeyDown()) {
                if (Math.abs(currentStepHeight - MIN_STEP_HEIGHT) >= 0.00001) {
                    entityCompat.setMaxUpStep(MIN_STEP_HEIGHT);
                }
            } else {
                if (Math.abs(currentStepHeight - prevStepHeight) >= 0.00001) {
                    entityCompat.setMaxUpStep(prevStepHeight);
                }
            }
        }
    }
}
