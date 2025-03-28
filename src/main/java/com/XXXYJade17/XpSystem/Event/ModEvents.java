package com.XXXYJade17.XpSystem.Event;

import com.XXXYJade17.XpSystem.Capability.ModCapabilities;
import com.XXXYJade17.XpSystem.Capability.PlayerXp;
import com.XXXYJade17.XpSystem.Data.XpWorldData;
import com.XXXYJade17.XpSystem.XpData;
import com.XXXYJade17.XpSystem.XpSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = XpSystem.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final int TICKS_PER_MINUTE = 5*20;
    private static int tickCounter = 0;
    private static final Logger LOGGER = XpSystem.getLOGGER();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.SERVER) {
            Optional<PlayerXp> optionalPlayerXp = Optional.ofNullable(event.player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
            optionalPlayerXp.ifPresent(xp -> {
                if(++tickCounter >= TICKS_PER_MINUTE) {
                    tickCounter = 0;
                    xp.addXp(1);
                    if(event.player instanceof ServerPlayer player) {
                        PacketDistributor.PLAYER.with(player)
                                .send(new XpData(xp.getXp(), xp.getLevel()));
                        player.sendSystemMessage(Component.literal("XP +1"));
                        player.sendSystemMessage(Component.literal(""+xp.getXp()));
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player&& event.getLevel() instanceof ServerLevel serverLevel) {
                XpWorldData data = XpWorldData.get(serverLevel );
                data.loadPlayerXp(player);
                Optional<PlayerXp> optionalPlayerXp = Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
                optionalPlayerXp.ifPresent(xp -> {
                    CompoundTag playerData = player.getPersistentData();
                    if (playerData.contains("XpSystemData")) {
                        CompoundTag xpData = playerData.getCompound("XpSystemData");
                        xp.loadData(xpData);
                    PacketDistributor.PLAYER.with(player)
                            .send(new XpData(xp.getXp(), xp.getLevel()));
                    LOGGER.info("Player {} has XP: {} and Level: {}", player.getName().getString(), xp.getXp(), xp.getLevel());
                }});
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide()){
            if(event.getEntity() instanceof ServerPlayer player&& event.getLevel() instanceof ServerLevel serverLevel) {
                XpWorldData data = XpWorldData.get(serverLevel);
                data.savePlayerXp(player);
                LOGGER.info("Saved {} XP data.",player);
            }
        }
    }

    @SubscribeEvent
    public static void onServerClose(ServerStoppedEvent event) {
        MinecraftServer server = event.getServer();
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        if (overworld != null) {
            XpWorldData data = XpWorldData.get(overworld);
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                data.savePlayerXp(player);
            }
            LOGGER.info("Saved player XP data.");
        }
    }

//    @SubscribeEvent
//    public static void onServerStart(ServerStartedEvent event){
//        MinecraftServer server = event.getServer();
//        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
//        if(overworld != null) {
//            XpWorldData data = XpWorldData.get(overworld);
//            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
//                data.loadPlayerXp(player);
//            }
//            LOGGER.info("Loaded player XP data.");
//        }
//    }
}
