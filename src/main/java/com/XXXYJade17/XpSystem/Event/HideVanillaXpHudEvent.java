package com.XXXYJade17.XpSystem.Event;

import com.XXXYJade17.XpSystem.XpSystem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

@Mod.EventBusSubscriber(modid = XpSystem.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HideVanillaXpHudEvent {
    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Pre event) {
        // 检查是否是原版经验条的渲染事件
        if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type()) {
            // 取消事件，阻止原版经验条的渲染
            event.setCanceled(true);
        }
    }
}
