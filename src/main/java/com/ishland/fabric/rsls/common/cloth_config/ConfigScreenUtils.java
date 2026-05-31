package com.ishland.fabric.rsls.common.cloth_config;

import com.ishland.fabric.rsls.RSLSMod;
import com.ishland.fabric.rsls.common.RSLSConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public class ConfigScreenUtils {
    
    public static Screen makeConfigScreen(Screen parent) {
        final ConfigBuilder builder = ConfigBuilder.create();
        builder.setParentScreen(parent)
            .setTitle(Component.literal("RSLS Config"))
            .setSavingRunnable(() -> {
                Minecraft.getInstance().getSoundManager().reload();
                RSLSConfig.saveConfig();
            });
        final ConfigCategory category = builder.getOrCreateCategory(Component.literal("Settings"));
        category.addEntry(
            builder.entryBuilder().startIntSlider(Component.literal("Total sound limit"), RSLSConfig.maxSourcesCount, 32, RSLSConfig.probedMaxSourcesCount)
                .setDefaultValue(RSLSConfig.probedMaxSourcesCount)
                .setSaveConsumer(integer -> RSLSConfig.maxSourcesCount = integer)
                .build()
        );
        category.addEntry(
            builder.entryBuilder().startIntSlider(Component.literal("Streaming sound limit"), RSLSConfig.maxStreamingSources, 8, RSLSConfig.probedMaxSourcesCount)
                .setDefaultValue(8)
                .setSaveConsumer(integer -> RSLSConfig.maxStreamingSources = integer)
                .build()
        );
        return builder.build();
    }
    
    public static Button getConfigButton(Screen screen) {
        Button widget = null;
        try {
            widget = Button.builder(Component.literal("RSLS Config"), button -> Minecraft.getInstance().setScreen(makeConfigScreen(screen))).bounds(screen.width - 90, 8, 80, 20).build();
        } catch (NoSuchMethodError e) {
            try {
                final Constructor<Button> constructor = Button.class.getDeclaredConstructor(int.class, int.class, int.class, int.class, Component.class, Button.OnPress.class, Button.CreateNarration.class);
                widget = constructor.newInstance(screen.width - 90, 8, 80, 20, Component.literal("RSLS Config"), (Button.OnPress) button -> Minecraft.getInstance().setScreen(makeConfigScreen(screen)), (Button.CreateNarration) Supplier::get);
            } catch (Throwable t) {
                RSLSMod.LOGGER.error("Failed to inject RSLS config button", t);
            }
        }
        return widget;
    }
    
}
