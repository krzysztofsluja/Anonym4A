package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MethodCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<MethodDeclaration> {

    private int methodCounter = 1;
    private int parameterCounter = 1;

    @AllArgsConstructor
    @Getter
    private enum ParameterOption {
        CONSTRUCTOR("constructor.parameter.name"),
        CLASS_METHOD("method.parameter.name"),
        STATIC("static.parameter.name");
        private final String anonymizationParameterName;
    
    }

    @Override
    public String anonymize(final MethodDeclaration code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        visitor.visit(code, null);
        return code.toString();
    }

    private void anonymizeMethod(final MethodDeclaration code, final Void arg) {
        if (!code.isConstructorDeclaration()) {
            final String originalName = code.getNameAsString();
            final String anonymizedName = getSetting("method.name") + methodCounter++;
            code.setName(anonymizedName);
            code.setParameters(anonymizeMethodParameters(code, ParameterOption.CLASS_METHOD));
            code.getBody().ifPresent(body -> {
                BlockCodeAnonymizer blockAnonymizer = new BlockCodeAnonymizer();
                blockAnonymizer.anonymize(body);
            });
        } else {
            anonymizeMethodParameters(code, ParameterOption.CONSTRUCTOR);
        }
    }

    private NodeList<Parameter> anonymizeMethodParameters(final MethodDeclaration code, final ParameterOption methodType) {
        return new NodeList<>(code.getParameters()
                    .stream()
                    .map(parameter -> anonymizeParameter(parameter, methodType))
                    .toList());
    }
    
    private Parameter anonymizeParameter(final Parameter parameter, final ParameterOption methodType) {
        final String originalName = parameter.getNameAsString();
        final String parameterPrefix = userSettingsConfiguration.getSetting(methodType.getAnonymizationParameterName());
        final String anonymizedName = parameterPrefix + parameterCounter++;
        parameter.setName(anonymizedName);
        return parameter;
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(MethodDeclaration.class, this::anonymizeMethod);
    }
}
