package com.sluja.anonym4ai.anonymization.utils.statement.visitor;

import java.util.Set;

import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.sluja.anonym4ai.anonymization.interfaces.StatementCategoryVisitor;

public class BasicStatementVisitor extends AbstractStatementAnonymizingVisitor implements StatementCategoryVisitor {

    public BasicStatementVisitor() {
        super(Set.of(ReturnStmt.class, ExpressionStmt.class, EmptyStmt.class));
    }

    @Override
    public void visit(final ReturnStmt n, final Void arg) {
        executeAnonymization(ReturnStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final ExpressionStmt n, final Void arg) {
        executeAnonymization(ExpressionStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final EmptyStmt n, final Void arg) {
        executeAnonymization(EmptyStmt.class, n, arg);
        super.visit(n, arg);
    }

}
