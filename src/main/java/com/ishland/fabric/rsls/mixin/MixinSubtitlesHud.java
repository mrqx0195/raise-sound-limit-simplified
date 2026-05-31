package com.ishland.fabric.rsls.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.WeighedSoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubtitleOverlay.class)
public abstract class MixinSubtitlesHud {
    @Shadow
    @Final
    private Minecraft minecraft;
    
    @Inject(method = "onPlaySound", at = @At("HEAD"), cancellable = true)
    private void onSoundPlayedHandler(SoundInstance sound, WeighedSoundEvents accessor, CallbackInfo ci) {
        if (!this.minecraft.isSameThread()) {
            ci.cancel();
            this.minecraft.execute(() -> this.onPlaySound(sound, accessor));
        }
    }
    
    @Shadow
    public abstract void onPlaySound(SoundInstance sound, WeighedSoundEvents accessor);
}
