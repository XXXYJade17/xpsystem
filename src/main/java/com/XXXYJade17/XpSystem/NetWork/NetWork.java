package com.XXXYJade17.XpSystem.NetWork;

import com.XXXYJade17.XpSystem.XpSystem;
import com.XXXYJade17.XpSystem.NetWork.Client.ClientPayloadHandler;
import com.XXXYJade17.XpSystem.NetWork.Server.ServerPayloadHandler;
import com.XXXYJade17.XpSystem.XpData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

@Mod.EventBusSubscriber(modid = XpSystem.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetWork {
    @SubscribeEvent
    public static void registerPackets(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(XpSystem.MODID);
        registrar.play(XpData.ID, XpData::new, handler ->
                handler.client(ClientPayloadHandler.getINSTANCE()::handleXpData)
                        .server(ServerPayloadHandler.getINSTANCE()::handleXpData));
    }
}
