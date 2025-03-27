package com.sluja.anonym4ai.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import lombok.Getter;

public class UserSettingsConfiguration {

    private static UserSettingsConfiguration instance;
    @Getter
    private Map<String, String> settings;
    @Getter
    private Map<String, String> anonymizationSettings;

    private UserSettingsConfiguration() {
        settings = new HashMap<>();
        anonymizationSettings = initializeMap();
    }

    private Map<String, String> initializeMap() {
        final Properties properties = new Properties();
        final Map<String, String> result = new HashMap<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("values.properties")) {
            if (Objects.nonNull(is)) {
                properties.load(is);
                properties.forEach((key, value) -> result.put(key.toString(), value.toString()));
            } else {
                System.err.println("Resource not found: values.properties");
            }
        } catch (final IOException e) {
            System.err.println("Failed to load properties: " + e.getMessage());
        }
        return result;    
    }

    public static UserSettingsConfiguration getInstance() {
        if(Objects.isNull(instance)) {
            instance = new UserSettingsConfiguration();
        }
        return instance;
    }

    public String getSetting(final String key) {
        return getInstance().getAnonymizationSettings().getOrDefault(key, "var");
    }

}
