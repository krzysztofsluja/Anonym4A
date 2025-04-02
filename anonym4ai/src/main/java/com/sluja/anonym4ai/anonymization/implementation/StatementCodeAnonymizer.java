package com.sluja.anonym4ai.anonymization.implementation;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.interfaces.ICounterReset;
import com.sluja.anonym4ai.anonymization.utils.AbstractAnonymizingVisitor;

public class StatementCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<Statement> implements ICounterReset {

    public StatementCodeAnonymizer(final AbstractAnonymizingVisitor visitor) {
        super(visitor);
        registerVisitorHandlers(visitor);
    }

    private int statementCounter = 1;

    @Override
    public String anonymize(final Statement code) {
        resetCounters();
        // visitor.visit(code, null);
        return StringUtils.EMPTY;
    }

    private void anonymizeStatement(final Statement code, final Void arg) {
        removeComments(code);
        if(code instanceof EmptyStmt) {
            return;
        }
        if(code instanceof ReturnStmt returnStmt) {
            anonymizeReturnStatement(returnStmt);
        } else if(code instanceof ExpressionStmt expressionStmt) {
            anonymizeExpressionStatement(expressionStmt);
        }
        resetCounters();
    }

    private void removeComments(final Statement code) {
        code.getAllContainedComments().forEach(Node::remove);
    }

    private void anonymizeReturnStatement(final ReturnStmt code) {
        code.getExpression().ifPresent(expr -> {
            if(expr.isNameExpr()) {
                visitor.visit(expr.asNameExpr().getName(), null);
            } else if(expr.isMethodCallExpr()) {
                visitor.visit(expr.asMethodCallExpr(), null);
            }
        });
    }

    private void anonymizeExpressionStatement(final ExpressionStmt code) {

    }

    private void anonymizeSimpleName(final SimpleName code, final Void arg) {
        code.setIdentifier(getSetting("statement.simple.name.parameter") + statementCounter++);
    }

    @Override
    protected void registerVisitorHandlers(final AbstractAnonymizingVisitor visitor) {
        visitor.registerHandler(ReturnStmt.class, this::anonymizeStatement);
        visitor.registerHandler(EmptyStmt.class, this::anonymizeStatement);
        visitor.registerHandler(ExpressionStmt.class, this::anonymizeStatement);
        visitor.registerHandler(SimpleName.class, this::anonymizeSimpleName);
    }

    @Override
    public void resetCounters() {
        this.statementCounter = 1;
    }

}
