package com.XXXYJade17.XpSystem.Capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class PlayerXpProvider implements ICapabilityProvider<Player, Void, PlayerXp>, INBTSerializable<CompoundTag> {
    private PlayerXp xp = null;

    private PlayerXp createPlayerXp() {
        if (xp == null) {
            xp = new PlayerXp();
        }
        return xp;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerXp().saveData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerXp().loadData(nbt);
    }

    @Override
    public @Nullable PlayerXp getCapability(Player player, Void context) {
        return createPlayerXp();
    }
}
