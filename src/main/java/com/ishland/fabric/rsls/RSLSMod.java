package com.ishland.fabric.rsls;

import com.ishland.fabric.rsls.common.RSLSConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod("rsls")
public class RSLSMod {
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public RSLSMod() {
        RSLSConfig.init();
        RSLSInjectorLWJGL.init();
    }
}
