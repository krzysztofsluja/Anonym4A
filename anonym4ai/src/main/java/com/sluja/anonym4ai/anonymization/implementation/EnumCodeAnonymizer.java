package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.body.EnumDeclaration;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;

public class EnumCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<EnumDeclaration> {

    private static int enumCounter = 1;
    private static int enumConstantCounter = 1;

    @Override
    public String anonymize(EnumDeclaration code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        visitor.visit(code, null);
        return code.toString();
    }

    private void anonymizeEnumDeclaration(final EnumDeclaration code, final Void arg) {
        final String originalName = code.getNameAsString();
        final String anonymizedName = userSettingsConfiguration.getSetting("enum.name") + enumCounter++;
        code.setName(anonymizedName);
        anonymizeEnumConstants(code);
        anonymizeEnumMethods(code);
    }

    private void anonymizeEnumConstants(final EnumDeclaration code) {
        code.getEntries().forEach(entry -> {
            final String originalName = entry.getNameAsString();
            final String anonymizedName = userSettingsConfiguration.getSetting("enum.constant.name")
                    + enumConstantCounter++;
            entry.setName(anonymizedName);
        });
    }

    private void anonymizeEnumMethods(final EnumDeclaration code) {
        code.getMethods().forEach(method -> {
            final String originalName = method.getNameAsString();
            final AnonymizingVisitor visitor = new AnonymizingVisitor();
            visitor.visit(method, null);
        });
    }

    @Override
    protected void registerVisitorHandlers(
            AbstractCodeAnonymizer<EnumDeclaration, EnumDeclaration>.AnonymizingVisitor visitor) {
        visitor.registerHandler(EnumDeclaration.class, this::anonymizeEnumDeclaration);
    }

}
