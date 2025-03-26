package com.XXXYJade17.XpSystem;

import com.XXXYJade17.XpSystem.Config.XpConfig;
import com.XXXYJade17.XpSystem.Event.ModEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.fml.common.Mod;

import java.awt.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(XpSystem.MODID)
public class XpSystem {
    public static final String MODID = "xpsystem";
    private static Logger LOGGER=LogUtils.getLogger();
    private static ModEvents modEvents;
    private static XpConfig xpconfig;

    public XpSystem(IEventBus modEventBus, ModContainer modContainer){
//        modEvents=ModEvents.getINSTANCE();
        xpconfig=XpConfig.getINSTANCE();
        NeoForge.EVENT_BUS.register(ModEvents.class);
    }

    public static Logger getLOGGER(){
        return LOGGER;
    }
}
