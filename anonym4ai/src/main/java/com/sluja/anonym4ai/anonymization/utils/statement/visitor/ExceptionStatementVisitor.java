package com.sluja.anonym4ai.anonymization.utils.statement.visitor;

import java.util.Set;

import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.sluja.anonym4ai.anonymization.interfaces.StatementCategoryVisitor;

public class ExceptionStatementVisitor extends AbstractStatementAnonymizingVisitor implements StatementCategoryVisitor {

    public ExceptionStatementVisitor() {
        super(Set.of(
            ThrowStmt.class,
            TryStmt.class
        ));
    }
    @Override
    public void visit(final ThrowStmt n, final Void arg) {
        executeAnonymization(ThrowStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final TryStmt n, final Void arg) {
        executeAnonymization(TryStmt.class, n, arg);
        super.visit(n, arg);
    }
}
