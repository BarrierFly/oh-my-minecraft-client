package com.plusls.ommc.mixin.feature.blockModelNoOffset.fabric;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.plusls.ommc.impl.feature.blockModelNoOffset.BlockModelNoOffsetHelper;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("UnstableApiUsage")
@Mixin(targets = "net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext", remap = false)
public abstract class MixinTerrainRenderContext {

    @WrapWithCondition(
            method = {
                    "tessellateBlock", // For fabric-renderer-indigo 0.5.0 and above
                    "tesselateBlock", // For fabric-renderer-indigo 0.5.0 below
                    "bufferModel" // 1.21.5+
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/class_4587;method_22904(DDD)V",
                    remap = false
            )
    )
    private static boolean blockModelNoOffsetCondition(
            PoseStack instance, double d, double e, double f,
            @Local(argsOnly = true) BlockState blockState
    ) {
        return !BlockModelNoOffsetHelper.shouldNoOffset(blockState);
    }
}