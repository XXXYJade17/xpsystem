package com.XXXYJade17.XpSystem.Hud;

import com.XXXYJade17.XpSystem.XpSystem;
import com.XXXYJade17.XpSystem.Config.XpConfig;
import com.XXXYJade17.XpSystem.NetWork.Client.ClientPayloadHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;

@OnlyIn(Dist.CLIENT)
public class XpHud {
    private static final ResourceLocation XP_BAR = new ResourceLocation(XpSystem.MODID, "textures/gui/xp_bar.png");

    public static final IGuiOverlay HUD_XP = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int currentXp = ClientPayloadHandler.getClientXp();
        int requiredXp = XpConfig.getRequiredXp(ClientPayloadHandler.getClientLevel());

        int barWidth = Math.round((float) currentXp / requiredXp * 100);

        guiGraphics.blit(XP_BAR, screenWidth/2 - 50, screenHeight - 30, 0, 0, 100, 10);
        guiGraphics.blit(XP_BAR, screenWidth/2 - 50, screenHeight - 30, 0, 10, barWidth, 10);

        String displayText = String.format("Level %d: %d/%d XP",
                ClientPayloadHandler.getClientLevel(),
                currentXp,
                requiredXp);
//        FontUtil.drawShadow(guiGraphics, displayText, screenWidth/2, screenHeight - 20, Color.WHITE.getRGB());
    };
}