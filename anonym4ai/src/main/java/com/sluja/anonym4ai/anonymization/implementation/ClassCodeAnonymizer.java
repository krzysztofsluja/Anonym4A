package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.interfaces.ICounterReset;
import com.sluja.anonym4ai.anonymization.utils.AbstractAnonymizingVisitor;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

public class ClassCodeAnonymizer extends AbstractCodeAnonymizer<ClassOrInterfaceDeclaration, ClassOrInterfaceType>
        implements ICounterReset {

    private int classCounter = 1;

    public ClassCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
        registerVisitorHandlers(visitor);
    }

    @Override
    public String anonymize(final ClassOrInterfaceDeclaration code) {
        visitor.visit(code, null);
        resetCounters();
        return code.toString();
    }

    private void anonymizeClass(final ClassOrInterfaceDeclaration code, final Void arg) {
        removeAnnotations(code);
        final String originalName = code.getNameAsString();
        final String anonymizedName = userSettingsConfiguration.getSetting("class.name") + classCounter++;
        code.setName(anonymizedName);
    }

    private void removeAnnotations(final ClassOrInterfaceDeclaration code) {
        code.getAnnotations().forEach(AnnotationExpr::remove);
    }

    @Override
    protected void registerVisitorHandlers(final AbstractAnonymizingVisitor blockVisitor) {
        blockVisitor.registerHandler(ClassOrInterfaceDeclaration.class, this::anonymizeClass);
    }

    @Override
    public void resetCounters() {
        this.classCounter = 1;
    }

}
