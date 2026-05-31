package com.ishland.fabric.rsls.mixin;

import com.ishland.fabric.rsls.common.WorldRandomHolder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Level.class, priority = 10000)
public class MixinWorld {

    @Shadow
    @Final
    public RandomSource random;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void captureWorldRandom(CallbackInfo ci) {
        WorldRandomHolder.putWorldRandom(this.random);
    }

}
