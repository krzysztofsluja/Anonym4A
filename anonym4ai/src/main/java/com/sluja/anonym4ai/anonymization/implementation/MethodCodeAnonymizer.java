package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.interfaces.ICounterReset;
import com.sluja.anonym4ai.anonymization.utils.AbstractAnonymizingVisitor;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MethodCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<MethodDeclaration> implements ICounterReset {

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

    public MethodCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
        registerVisitorHandlers(visitor);
    }

    @Override
    public String anonymize(final MethodDeclaration code) {
        visitor.visit(code, null);
        resetCounters();
        return code.toString();
    }

    private void anonymizeMethod(final MethodDeclaration code, final Void arg) {
        if (!code.isConstructorDeclaration()) {
            final String originalName = code.getNameAsString();
            final String anonymizedName = getSetting("method.name") + methodCounter++;
            code.setName(anonymizedName);
            code.setParameters(anonymizeMethodParameters(code, ParameterOption.CLASS_METHOD));
            code.getBody().ifPresent(body -> {
                visitor.visit(body, arg);
            });
        } else {
            anonymizeMethodParameters(code, ParameterOption.CONSTRUCTOR);
        }
    }

    private NodeList<Parameter> anonymizeMethodParameters(final MethodDeclaration code,
            final ParameterOption methodType) {
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
    protected void registerVisitorHandlers(final AbstractAnonymizingVisitor visitor) {
        visitor.registerHandler(MethodDeclaration.class, this::anonymizeMethod);
    }

    @Override
    public void resetCounters() {
        this.methodCounter = 1;
        this.parameterCounter = 1;
    }
}
