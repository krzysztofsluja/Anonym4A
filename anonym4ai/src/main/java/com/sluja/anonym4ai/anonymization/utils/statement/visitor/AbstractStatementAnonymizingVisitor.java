package com.sluja.anonym4ai.anonymization.utils.statement.visitor;

import java.util.Set;

import com.github.javaparser.ast.stmt.Statement;
import com.sluja.anonym4ai.anonymization.interfaces.StatementCategoryVisitor;
import com.sluja.anonym4ai.anonymization.utils.AbstractAnonymizingVisitor;

import lombok.Getter;

public abstract class AbstractStatementAnonymizingVisitor extends AbstractAnonymizingVisitor implements StatementCategoryVisitor {

    @Getter
    protected Set<Class<? extends Statement>> handledStatements;

    protected  AbstractStatementAnonymizingVisitor(final Set<Class<? extends Statement>> handledStatements) {
        super();
        this.handledStatements = handledStatements;
    }

    public boolean isHandledStatement(final Class<? extends Statement> statementClass) {
        return handledStatements.contains(statementClass);
    }

}
