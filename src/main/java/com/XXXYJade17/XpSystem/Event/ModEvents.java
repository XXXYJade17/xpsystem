package com.XXXYJade17.XpSystem.Event;

import com.XXXYJade17.XpSystem.Capability.ModCapabilities;
import com.XXXYJade17.XpSystem.Capability.PlayerXp;
import com.XXXYJade17.XpSystem.XpData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;
import java.util.logging.Logger;

public class ModEvents {
    private static final int TICKS_PER_MINUTE = 5*20;
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            Optional.ofNullable(event.player.getCapability(ModCapabilities.PLAYER_XP_HANDLER))
                    .ifPresent(xp -> {
                        if(++tickCounter >= TICKS_PER_MINUTE) {
                            tickCounter = 0;
                            xp.addXp(1);
                            PacketDistributor.PLAYER.with((ServerPlayer) event.player)
                                    .send(new XpData(xp.getXp(), xp.getLevel()));
                            event.player.sendSystemMessage(Component.literal("XP +1"));
                        }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER))
                    .ifPresent(xp -> {
                        PacketDistributor.PLAYER.with(player)
                                .send(new XpData(xp.getXp(), xp.getLevel()));
                    });
        }
    }
}
