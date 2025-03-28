package com.XXXYJade17.XpSystem.Data;

import com.XXXYJade17.XpSystem.Capability.ModCapabilities;
import com.XXXYJade17.XpSystem.Capability.PlayerXp;
import com.XXXYJade17.XpSystem.XpSystem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class XpWorldData extends SavedData {
    private static final String DATA_NAME = "xp_data";
    private final Map<UUID, PlayerXp> playerXpData = new HashMap<>();
    private static final Logger LOGGER= XpSystem.getLOGGER();

    public static XpWorldData create() {
        return new XpWorldData();
    }

    public static XpWorldData load(CompoundTag nbt) {
        LOGGER.info("Loading player XP data...");
        XpWorldData data = new XpWorldData();
        ListTag playerList = nbt.getList("Players", Tag.TAG_COMPOUND);
        for (int i = 0; i < playerList.size(); i++) {
            CompoundTag playerNbt = playerList.getCompound(i);  //
            UUID uuid = playerNbt.getUUID("UUID");
            PlayerXp xp = new PlayerXp();
            xp.loadData(playerNbt.getCompound("XpData"));
            data.playerXpData.put(uuid, xp);
        }
        LOGGER.info("Player XP data loaded successfully.");
        return data;
    }

    public static XpWorldData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(XpWorldData::create, XpWorldData::load),
                DATA_NAME
        );
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        LOGGER.info("Saving player XP data...");
        ListTag playerList = new ListTag();
        for (Map.Entry<UUID, PlayerXp> entry : playerXpData.entrySet()) {
            CompoundTag playerNbt = new CompoundTag();
            playerNbt.putUUID("UUID", entry.getKey());
            CompoundTag xpNbt = new CompoundTag();
            entry.getValue().saveData(xpNbt);
            playerNbt.put("XpData", xpNbt);
            playerList.add(playerNbt);
        }
        nbt.put("Players", playerList);
        LOGGER.info("Player XP data saved successfully.");
        return nbt;
    }

    public PlayerXp getPlayerXp(UUID uuid) {
        return playerXpData.computeIfAbsent(uuid, k -> new PlayerXp());
    }

    public void savePlayerXp(Player player) {
        Optional<PlayerXp> optionalXp = Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
        optionalXp.ifPresent(xp -> {
            playerXpData.put(player.getUUID(), xp);
            setDirty(); // 必须调用，否则数据不会保存
        });
    }

    // 加载玩家数据（从存储中读取）
    public void loadPlayerXp(Player player) {
        PlayerXp xp = getPlayerXp(player.getUUID());
        Optional<PlayerXp> optionalXp = Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
        optionalXp.ifPresent(cap -> {
            CompoundTag nbt = new CompoundTag();
            xp.saveData(nbt);
            cap.loadData(nbt);
        });
    }
}
