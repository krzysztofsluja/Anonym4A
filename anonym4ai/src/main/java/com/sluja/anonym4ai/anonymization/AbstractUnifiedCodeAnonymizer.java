package com.sluja.anonym4ai.anonymization;

import com.github.javaparser.ast.Node;
import com.sluja.anonym4ai.anonymization.utils.AbstractAnonymizingVisitor;

public abstract class AbstractUnifiedCodeAnonymizer<T extends Node> extends AbstractCodeAnonymizer<T, T> {

    public AbstractUnifiedCodeAnonymizer(final AbstractAnonymizingVisitor visitor) {
        super(visitor);
    }

}
