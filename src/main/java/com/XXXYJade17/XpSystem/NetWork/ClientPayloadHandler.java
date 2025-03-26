package com.XXXYJade17.XpSystem.NetWork;

import com.XXXYJade17.XpSystem.XpData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@OnlyIn(Dist.CLIENT)
public class ClientPayloadHandler {
    private static int clientXp;
    private static int clientLevel;

    public static void handleXpData(XpData data, PlayPayloadContext context) {
        clientXp = data.xp();
        clientLevel = data.level();
    }

    public static int getClientXp() {
        return clientXp;
    }

    public static int getClientLevel() {
        return clientLevel;
    }
}
