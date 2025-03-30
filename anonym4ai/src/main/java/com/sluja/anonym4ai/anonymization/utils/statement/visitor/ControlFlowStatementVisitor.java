package com.sluja.anonym4ai.anonymization.utils.statement.visitor;

import java.util.Set;

import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.sluja.anonym4ai.anonymization.interfaces.StatementCategoryVisitor;

public class ControlFlowStatementVisitor extends AbstractStatementAnonymizingVisitor implements StatementCategoryVisitor{

    public ControlFlowStatementVisitor() {
        super(Set.of(
            IfStmt.class,
            WhileStmt.class,
            ForEachStmt.class,
            ForStmt.class,
            SwitchStmt.class,
            BreakStmt.class,
            ContinueStmt.class,
            DoStmt.class
        ));
    }

    @Override
    public void visit(final IfStmt n, final Void arg) {
        executeAnonymization(IfStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final WhileStmt n, final Void arg) {
        executeAnonymization(WhileStmt.class, n, arg);
        super.visit(n, arg);
    }
    @Override
    public void visit(final ForEachStmt n, final Void arg) {
        executeAnonymization(ForEachStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final ForStmt n, final Void arg) {
        executeAnonymization(ForStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final SwitchStmt n, final Void arg) {
        executeAnonymization(SwitchStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final BreakStmt n, final Void arg) {
        executeAnonymization(BreakStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final ContinueStmt n, final Void arg) {
        executeAnonymization(ContinueStmt.class, n, arg);
        super.visit(n, arg);
    }

    @Override
    public void visit(final DoStmt n, final Void arg) {
        executeAnonymization(DoStmt.class, n, arg);
        super.visit(n, arg);
    }

}
