package com.XXXYJade17.XpSystem.NetWork.Client;

import com.XXXYJade17.XpSystem.XpData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientPayloadHandler {
    private static ClientPayloadHandler INSTANCE;

    private static int clientXp;
    private static int clientLevel;

    public static ClientPayloadHandler getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ClientPayloadHandler();
        }
        return INSTANCE;
    }

    public void handleXpData(XpData data, PlayPayloadContext context) {
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
