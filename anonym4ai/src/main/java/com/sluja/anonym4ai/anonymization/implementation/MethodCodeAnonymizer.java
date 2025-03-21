package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;

public class MethodCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<MethodDeclaration> {

    private int methodCounter = 1;
    private int parameterCounter = 1;

    @Override
    protected String anonymize(final MethodDeclaration code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        visitor.visit(code, null);
        return code.getNameAsString();
    }

    private void anonymizeMethod(final MethodDeclaration code, final Void arg) {
        if (!code.isConstructorDeclaration()) {
            final String originalName = code.getNameAsString();
            final String anonymizedName = getSetting("method.name") + methodCounter++;
            code.setName(anonymizedName);
            anonymizeMethodParameters(code, anonymizedName);
        } else {
            anonymizeMethodParameters(code, "constructor");
        }
    }

    private void anonymizeMethodParameters(final MethodDeclaration code, final String methodName) {
        code.getParameters().forEach(parameter -> anonymizeParameter(parameter, methodName));
    }
    
    private void anonymizeParameter(final Parameter parameter, final String methodName) {
        final String originalName = parameter.getNameAsString();
        final String parameterPrefix = userSettingsConfiguration.getSetting("parameter.name");
        final String anonymizedName = methodName + parameterPrefix + parameterCounter++;
        parameter.setName(anonymizedName);
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(MethodDeclaration.class, this::anonymizeMethod);
    }

    @Override
    public MethodDeclaration parse(final String code) throws ParseProblemException {
        return StaticJavaParser.parseMethodDeclaration(code);
    }
}
