package com.XXXYJade17.XpSystem.NetWork;

import com.XXXYJade17.XpSystem.Capability.ModCapabilities;
import com.XXXYJade17.XpSystem.XpData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Optional;

public class ServerPayloadHandler {
    public static void handleXpData(XpData data, PlayPayloadContext context) {
        context.player().ifPresent(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER))
                        .ifPresent(xp -> {
                            // 这里可以处理自定义数据包逻辑
                            PacketDistributor.PLAYER.with(serverPlayer)
                                    .send(new XpData(xp.getXp(), xp.getLevel()));
                        });
            }
        });
    }
}
