package com.sluja.anonym4ai.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;

public class UserSettingsConfiguration {

    private static UserSettingsConfiguration instance;
    @Getter
    private Map<String, String> settings;

    private UserSettingsConfiguration() {
        settings = new HashMap<>();
    }

    public static UserSettingsConfiguration getInstance() {
        if(Objects.isNull(instance)) {
            instance = new UserSettingsConfiguration();
        }
        return instance;
    }

    public String getSetting(final String key) {
        return getInstance().getSettings().getOrDefault(key, "var");
    }

}
