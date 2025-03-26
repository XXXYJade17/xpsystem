package com.XXXYJade17.XpSystem.Capability;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class ModCapabilities {
    public static final EntityCapability<PlayerXp, Void> PLAYER_XP_HANDLER =
            EntityCapability.createVoid(new ResourceLocation(XpSystem.MODID, "player_xp_handler"),
                    PlayerXp.class);
}
