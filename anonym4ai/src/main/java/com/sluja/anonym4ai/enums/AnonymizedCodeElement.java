package com.sluja.anonym4ai.enums;

import lombok.Getter;

public enum AnonymizedCodeElement {
    
    CLASS("class.name"),
    METHOD("method.name"),
    PARAMETER("parameter.name"),
    FIELD_VARIABLE("variable.class.name"),
    METHOD_VARIABLE("variable.method.name"),
    DEFAULT_VARIABLE("variable.default.name"),
    STRING("string.name"),
    ENUM("enum.name"),
    LOOP_VARIABLE("loop.variable.name"),
    EXCEPTION_VARIABLE("exception.variable.name");
    
    @Getter
    private final String settingKey;
    
    AnonymizedCodeElement(String settingKey) {
        this.settingKey = settingKey;
    }
    
}
