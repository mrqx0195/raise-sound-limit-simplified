package com.ishland.fabric.rsls.mixin;

import com.ishland.fabric.rsls.common.RSLSConfig;
import com.mojang.blaze3d.audio.Library;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Library.class)
public class MixinLibrary {

    @ModifyArg(
            method = "init",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I", ordinal = 1),
            index = 2
    )
    private int modifyStaticLimit(int originalMax) {
        return RSLSConfig.maxSourcesCount;
    }

    @ModifyArg(
            method = "init",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I", ordinal = 0),
            index = 2
    )
    private int modifyStreamingLimit(int originalMax) {
        return RSLSConfig.maxStreamingSources;
    }

}
