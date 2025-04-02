package com.sluja.anonym4ai.anonymization;

import com.github.javaparser.ast.Node;
import com.sluja.anonym4ai.anonymization.utils.AbstractAnonymizingVisitor;
import com.sluja.anonym4ai.context.UserSettingsConfiguration;

import lombok.Getter;

public abstract class AbstractCodeAnonymizer<T, K extends Node> {

    final protected UserSettingsConfiguration userSettingsConfiguration;
    @Getter
    final protected AbstractAnonymizingVisitor visitor;

    public AbstractCodeAnonymizer(final AbstractAnonymizingVisitor visitor) {
        userSettingsConfiguration = UserSettingsConfiguration.getInstance();
        this.visitor = visitor;
    }

    public abstract String anonymize(final T code);

    protected abstract void registerVisitorHandlers(AbstractAnonymizingVisitor visitor);

    protected String getSetting(final String key) {
        return userSettingsConfiguration.getSetting(key);
    }

}
