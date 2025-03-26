package com.XXXYJade17.XpSystem.Config;

import com.XXXYJade17.XpSystem.XpSystem;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class XpConfig {
    private static final Map<Integer, Integer> levelXpMap = new HashMap<>();
    private static final Logger LOGGER = XpSystem.getLOGGER();;
    private static final Gson gson = new Gson();
    private static JsonObject xpconfig;

    public XpConfig(){
        load();
    }

    public static void load() {
        try{
            Path xpConfigDir = Path.of("config/xpsystem");
            Files.createDirectories(xpConfigDir); //创建目录

            Path xpConfigPath = xpConfigDir.resolve("xpconfig.json");
            if (Files.notExists(xpConfigPath)) {
                try (InputStream inputStream = XpConfig.class.getResourceAsStream("/config/xpsystem/xpconfig.json")) {
                    if (inputStream != null) {
                        Files.copy(inputStream, xpConfigPath);
                        LOGGER.info("XpConfig has loaded!");
                    } else {
                        LOGGER.warn("XpConfig is empty!");
                        return;
                    }
                }
            }

            try (Reader reader = Files.newBufferedReader(xpConfigPath, StandardCharsets.UTF_8)) {
                JsonElement element = JsonParser.parseReader(reader);
                JsonObject object = element.getAsJsonObject();
                object.entrySet().forEach(entry -> {
                    int level = Integer.parseInt(entry.getKey());
                    int xp = entry.getValue().getAsInt();
                    levelXpMap.put(level, xp);
                });
            }
        } catch (IOException e) {
            LOGGER.error("XpConfig is failed to load : ",e);
        }
    }

    public static int getRequiredXp(int level) {
        return levelXpMap.getOrDefault(level, 0);
    }
}
