package com.XXXYJade17.XpSystem.Event;

import com.XXXYJade17.XpSystem.Capability.ModCapabilities;
import com.XXXYJade17.XpSystem.Capability.PlayerXp;
import com.XXXYJade17.XpSystem.XpData;
import com.XXXYJade17.XpSystem.XpSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

import java.util.Optional;

public class ModEvents {
    private static final int TICKS_PER_MINUTE = 5*20;
    private static int tickCounter = 0;
    private final Logger LOGGER = XpSystem.getLOGGER();

    private static ModEvents INSTANCE;

    public static ModEvents getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = new ModEvents();
        }
        return INSTANCE;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            Optional<PlayerXp> optionalPlayerXp = Optional.ofNullable(event.player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
            optionalPlayerXp.ifPresent(xp -> {
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
    public void onPlayerJoin(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                Optional<PlayerXp> optionalPlayerXp = Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
                optionalPlayerXp.ifPresent(xp -> {
                    PacketDistributor.PLAYER.with(player)
                            .send(new XpData(xp.getXp(), xp.getLevel()));
                    LOGGER.info("Player {} has XP: {} and Level: {}", player.getName().getString(), xp.getXp(), xp.getLevel());
                });
            }
        }
    }
}
