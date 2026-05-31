package com.ishland.fabric.rsls.mixin.cloth_config;

import com.ishland.fabric.rsls.common.cloth_config.ConfigScreenUtils;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundOptionsScreen.class)
public abstract class MixinSoundOptionsScreen extends OptionsSubScreen {
    public MixinSoundOptionsScreen(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }
    
    @Dynamic
    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        final Button widget = ConfigScreenUtils.getConfigButton(this);
        if (widget != null) {
            this.addRenderableWidget(widget);
        }
    }
}
