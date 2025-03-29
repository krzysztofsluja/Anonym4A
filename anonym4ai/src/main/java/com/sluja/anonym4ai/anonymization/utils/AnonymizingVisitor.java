package com.sluja.anonym4ai.anonymization.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

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

    @Override
    public void visit(final CompilationUnit n, Void arg) {
        executeAnonymization(CompilationUnit.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final EnumDeclaration n, Void arg) {
        executeAnonymization(EnumDeclaration.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final Parameter n, Void arg) {
        executeAnonymization(Parameter.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final FieldDeclaration n, Void arg) {
        executeAnonymization(FieldDeclaration.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final BlockStmt n, Void arg) {
        executeAnonymization(BlockStmt.class, n, arg);
        super.visit(n, arg);
    }

}
