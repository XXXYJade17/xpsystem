package com.XXXYJade17.XpSystem;

import com.XXXYJade17.XpSystem.Capability.XpSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record XpData(int xp, int level) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(XpSystem.MODID, "xp_data");

    public XpData(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(xp);
        buf.writeInt(level);
    }

    public ResourceLocation id() {
        return ID;
    }

}
