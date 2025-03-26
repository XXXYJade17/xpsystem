package com.XXXYJade17.XpSystem.Capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PlayerXpProvider implements ICapabilityProvider<Player, Void, PlayerXp>, INBTSerializable<CompoundTag> {
    private PlayerXp xp = null;

    private PlayerXp createPlayerXp() {
        if (this.xp == null) {
            this.xp = new PlayerXp();
        }
        return this.xp;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerXp().saveData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundTag nbt) {
        createPlayerXp().loadData(nbt);
    }

    @Override
    public @Nullable PlayerXp getCapability(@NotNull Player player, @NotNull Void context) {
        return createPlayerXp();
    }
}
