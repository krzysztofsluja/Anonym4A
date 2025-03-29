package com.sluja.anonym4ai.anonymization;

import com.github.javaparser.ast.Node;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

public abstract class AbstractUnifiedCodeAnonymizer<T extends Node> extends AbstractCodeAnonymizer<T, T> {

    public AbstractUnifiedCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
    }

}
