package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.body.EnumDeclaration;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.interfaces.ICounterReset;
import com.sluja.anonym4ai.anonymization.utils.AbstractAnonymizingVisitor;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

public class EnumCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<EnumDeclaration> implements ICounterReset {

    private int enumCounter = 1;
    private int enumConstantCounter = 1;

    public EnumCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
        registerVisitorHandlers(visitor);
    }

    @Override
    public String anonymize(EnumDeclaration code) {
        visitor.visit(code, null);
        resetCounters();
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
    protected void registerVisitorHandlers(final AbstractAnonymizingVisitor visitor) {
        visitor.registerHandler(EnumDeclaration.class, this::anonymizeEnumDeclaration);
    }

    @Override
    public void resetCounters() {
        this.enumCounter = 1;
        this.enumConstantCounter = 1;
    }

}
