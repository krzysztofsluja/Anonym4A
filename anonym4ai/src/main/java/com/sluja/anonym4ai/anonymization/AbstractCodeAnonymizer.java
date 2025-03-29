package com.sluja.anonym4ai.anonymization;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;
import com.sluja.anonym4ai.context.UserSettingsConfiguration;

import lombok.Getter;

public abstract class AbstractCodeAnonymizer<T, K extends Node> {

    final protected UserSettingsConfiguration userSettingsConfiguration;
    @Getter
    final protected AnonymizingVisitor visitor;

    public AbstractCodeAnonymizer(final AnonymizingVisitor visitor) {
        userSettingsConfiguration = UserSettingsConfiguration.getInstance();
        this.visitor = visitor;
    }

    public abstract String anonymize(final T code);

    protected abstract void registerVisitorHandlers(AnonymizingVisitor visitor);

    protected String getSetting(final String key) {
        return userSettingsConfiguration.getSetting(key);
    }

}
