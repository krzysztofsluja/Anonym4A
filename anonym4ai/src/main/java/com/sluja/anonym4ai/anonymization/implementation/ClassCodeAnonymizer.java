package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;

public class ClassCodeAnonymizer extends AbstractCodeAnonymizer<ClassOrInterfaceDeclaration, ClassOrInterfaceType> {

    private int classCounter = 1;

    @Override
    protected String anonymize(final ClassOrInterfaceDeclaration code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        visitor.visit(code, null);
        return code.getNameAsString();
    }

    private void anonymizeClass(final ClassOrInterfaceDeclaration code, final Void arg) {
        final String originalName = code.getNameAsString();
        final String anonymizedName = userSettingsConfiguration.getSetting("class.name") + classCounter++;
        code.setName(anonymizedName);
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor blockVisitor) {
        blockVisitor.registerHandler(ClassOrInterfaceDeclaration.class, this::anonymizeClass);
    }

}
