package com.sluja.anonym4ai.anonymization.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public abstract class AbstractAnonymizingVisitor extends VoidVisitorAdapter<Void> {

    private final Map<Class<?>, BiConsumer<Object, Void>> handlers;

    public AbstractAnonymizingVisitor() {
        this.handlers = new HashMap<>();
    }

    public <N> void registerHandler(Class<N> nodeClass, BiConsumer<N, Void> handler) {
        handlers.put(nodeClass, (BiConsumer<Object, Void>) handler);
    }

    protected  void executeAnonymization(final Class<?> nodeClass, final Object node, final Void arg) {
        handlers.computeIfPresent(nodeClass, (k, v) -> {
            v.accept(node, arg);
            return v;
        });
    }

}
