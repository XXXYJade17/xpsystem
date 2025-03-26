package com.XXXYJade17.XpSystem.Config;

import com.XXXYJade17.XpSystem.XpSystem;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;

import java.io.FileReader;
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
    private static final XpConfig INSTANCE= new XpConfig();

    public static XpConfig getINSTANCE() {
        return INSTANCE;
    }

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
                    } else {
                        LOGGER.warn("XpConfig is empty!");
                        return;
                    }
                }
            }
            try(FileReader reader = new FileReader(xpConfigPath.toFile())) {
                Map<Integer, Integer> loadedXp = gson.fromJson(reader, new TypeToken<Map<Integer, Integer>>() {}.getType());
                if (loadedXp != null) {
                    levelXpMap.putAll(loadedXp);
                }
                LOGGER.info("XpConfig has loaded!");
            }
        } catch (IOException e) {
            LOGGER.error("XpConfig is failed to load : ",e);
        }
    }

    public static int getRequiredXp(int level) {
        return levelXpMap.getOrDefault(level, 0);
    }
}
