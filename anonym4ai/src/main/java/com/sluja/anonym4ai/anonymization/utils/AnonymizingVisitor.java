package com.sluja.anonym4ai.anonymization.utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.sluja.anonym4ai.anonymization.utils.statement.dispatcher.StatementDispatcher;

public class AnonymizingVisitor extends AbstractAnonymizingVisitor {

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
        n.getStatements().forEach(statement -> StatementDispatcher.getInstance().dispatchStatement(statement));
        super.visit(n, arg);
    }

}
