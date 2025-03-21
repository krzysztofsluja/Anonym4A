package com.sluja.anonym4ai.anonymization;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sluja.anonym4ai.context.UserSettingsConfiguration;

public abstract class AbstractCodeAnonymizer<T, K> {

    final protected UserSettingsConfiguration userSettingsConfiguration;

    public AbstractCodeAnonymizer() {
        userSettingsConfiguration = UserSettingsConfiguration.getInstance();
    }

    protected abstract String anonymize(final T code);
    protected abstract void registerVisitorHandlers(AnonymizingVisitor visitor);
    public abstract K parse(final String code) throws ParseProblemException;

    protected String getSetting(final String key) {
        return userSettingsConfiguration.getSetting(key);
    }

    public class AnonymizingVisitor extends VoidVisitorAdapter<Void> {
        
        private final Map<Class<?>, BiConsumer<Object, Void>> handlers = new HashMap<>();
        
        public <N> void registerHandler(Class<N> nodeClass, BiConsumer<N, Void> handler) {
            handlers.put(nodeClass, (BiConsumer<Object, Void>) handler);
        }

        private void executeAnonymization(final Class<?> nodeClass, final Object node, final Void arg) {
            handlers.computeIfPresent(nodeClass, (k, v) -> {
                v.accept(node, arg);
                return v;
            });
        }

        @Override
        public void visit(final ClassOrInterfaceDeclaration n, Void arg) {
            executeAnonymization(ClassOrInterfaceDeclaration.class, n, arg);
            super.visit(n, arg);
        }
        
        @Override
        public void visit(final MethodDeclaration n, Void arg) {
            executeAnonymization(MethodDeclaration.class, n, arg);
            super.visit(n, arg);
        }
        
        @Override
        public void visit(final VariableDeclarator n, Void arg) {
            executeAnonymization(VariableDeclarator.class, n, arg);
            super.visit(n, arg);
        }
        
    }
}
