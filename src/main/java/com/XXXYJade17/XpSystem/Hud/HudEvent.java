package com.XXXYJade17.XpSystem.Hud;

import com.XXXYJade17.XpSystem.XpSystem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HudEvent {
    @SubscribeEvent
    public static void registerHud(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(new ResourceLocation(XpSystem.MODID, "xp_hud"), XpHud.HUD_XP);
    }
}
