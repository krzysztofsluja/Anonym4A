package com.sluja.anonym4ai.anonymization.utils.statement.dispatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.github.javaparser.ast.stmt.Statement;
import com.sluja.anonym4ai.anonymization.utils.statement.visitor.AbstractStatementAnonymizingVisitor;
import com.sluja.anonym4ai.anonymization.utils.statement.visitor.BasicStatementVisitor;
import com.sluja.anonym4ai.anonymization.utils.statement.visitor.ControlFlowStatementVisitor;
import com.sluja.anonym4ai.anonymization.utils.statement.visitor.ExceptionStatementVisitor;

public class StatementDispatcher {
    private final Map<Class<? extends Statement>, AbstractStatementAnonymizingVisitor> visitorMap;
    private final AbstractStatementAnonymizingVisitor basicVisitor;
    private final AbstractStatementAnonymizingVisitor controlFlowVisitor;
    private final AbstractStatementAnonymizingVisitor exceptionVisitor;

    private static StatementDispatcher instance;

    private StatementDispatcher() {
        this.visitorMap = new HashMap<>();
        this.basicVisitor = new BasicStatementVisitor();
        this.controlFlowVisitor = new ControlFlowStatementVisitor();
        this.exceptionVisitor = new ExceptionStatementVisitor();
        registerVisitors();
    }

    public synchronized static StatementDispatcher getInstance() {
        if (Objects.isNull(instance)) {
            instance = new StatementDispatcher();
        }
        return instance;
    }

    private void registerVisitors() {
        basicVisitor.getHandledStatements().forEach(type -> visitorMap.put(type, basicVisitor));
        controlFlowVisitor.getHandledStatements().forEach(type -> visitorMap.put(type, controlFlowVisitor));
        exceptionVisitor.getHandledStatements().forEach(type -> visitorMap.put(type, exceptionVisitor));
    }

    public void dispatchStatement(final Statement statement) {
        final AbstractStatementAnonymizingVisitor visitor = visitorMap.get(statement.getClass());
        if (Objects.nonNull(visitor)) {
            statement.accept(visitor, null);
        }
    }
}
