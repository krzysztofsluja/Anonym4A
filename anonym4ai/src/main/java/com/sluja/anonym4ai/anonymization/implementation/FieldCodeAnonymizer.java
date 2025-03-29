package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.interfaces.ICounterReset;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

public class FieldCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<FieldDeclaration> implements ICounterReset {

    private int fieldCounter = 1;

    public FieldCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
        registerVisitorHandlers(visitor);
    }

    @Override
    public String anonymize(final FieldDeclaration code) {
        visitor.visit(code, null);
        resetCounters();
        return code.toString();
    }

    private void anonymizeFieldDeclaration(final FieldDeclaration field, final Void arg) {
        field.getVariables().forEach(this::anonymizeVariable);
    }

    private void anonymizeVariable(final VariableDeclarator variable) {
        final String originalName = variable.getNameAsString();
        final String fieldPrefix = getSetting("field.name");
        final String anonymizedName = fieldPrefix + fieldCounter++;
        variable.setName(anonymizedName);
    }

    private void anonymizeVariableDeclarator(final VariableDeclarator variable, final Void arg) {
        anonymizeVariable(variable);
    }

    @Override
    protected void registerVisitorHandlers(AnonymizingVisitor visitor) {
        visitor.registerHandler(FieldDeclaration.class, this::anonymizeFieldDeclaration);
        visitor.registerHandler(VariableDeclarator.class, this::anonymizeVariableDeclarator);
    }

    @Override
    public void resetCounters() {
        this.fieldCounter = 1;
    }
}
