package com.XXXYJade17.XpSystem.Capability;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(XpSystem.MODID)
public class XpSystem {
    public static final String MODID = "xpsystem";
    private static Logger LOGGER=LogUtils.getLogger();

    public static Logger getLOGGER(){
        return LOGGER;
    }
}
