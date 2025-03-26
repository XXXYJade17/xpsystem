package com.XXXYJade17.XpSystem.Capability;

import com.XXXYJade17.XpSystem.Config.XpConfig;
import net.minecraft.nbt.CompoundTag;

public class PlayerXp {
    private int xp=0;
    private int level = 1;
    private int requiredXp = XpConfig.getRequiredXp(level);

    public int getXp(){
        return this.xp;
    }

    public int getLevel() {
        return this.level;
    }

    public void addXp(int xp){
        this.xp+=xp;
        while (this.xp >= this.requiredXp) {
            this.xp -= this.requiredXp;
            level++;
            updateRequiredXp();
        }
    }

    public void updateRequiredXp() {
        this.requiredXp = XpConfig.getRequiredXp(level);
    }

    public void saveData(CompoundTag nbt) {
        nbt.putInt("level", level);
        nbt.putInt("xp", xp);
    }

    public void loadData(CompoundTag nbt) {
        level = nbt.getInt("level");
        xp = nbt.getInt("xp");
    }

}
