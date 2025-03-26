package com.XXXYJade17.XpSystem;

import com.XXXYJade17.XpSystem.Capability.ModCapabilities;
import com.XXXYJade17.XpSystem.Capability.PlayerXpProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@Mod.EventBusSubscriber(modid = XpSystem.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus{
    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(ModCapabilities.PLAYER_XP_HANDLER,
                EntityType.PLAYER,
                new PlayerXpProvider());
    }
}

