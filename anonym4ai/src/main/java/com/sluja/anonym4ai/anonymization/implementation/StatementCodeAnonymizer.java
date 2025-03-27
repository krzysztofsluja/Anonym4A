package com.sluja.anonym4ai.anonymization.implementation;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;

public class StatementCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<Statement> {

    private static int statementCounter = 1;

    @Override
    public String anonymize(final Statement code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        //visitor.visit(code, null);
        return StringUtils.EMPTY; 
    }

    private void anonymizeStatement(final Statement code, final Void arg) {
        code.getAllContainedComments().forEach(Node::remove);
    }
        
    private void anonymizeVariableDeclaration(final VariableDeclarationExpr code, final Void arg) {
        // Anonymize variable declarations
        code.getVariables().forEach(var -> {
            String originalName = var.getNameAsString();
            String anonymizedName = "MethodVar" + statementCounter++;
            //variableNameMap.put(originalName, anonymizedName);
            var.setName(anonymizedName);
        });
    }
    
    private void anonymizeNameExpr(final NameExpr code, final Void arg) {
        String originalName = code.getNameAsString();
        code.setName(getSetting("method.variable.name"));
    }
    
    private void anonymizeReturnStmt(final ReturnStmt code, final Void arg) {
        // Nothing specific to do - the visitor will handle the expression
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(Statement.class, this::anonymizeStatement);
        visitor.registerHandler(VariableDeclarationExpr.class, this::anonymizeVariableDeclaration);
        visitor.registerHandler(NameExpr.class, this::anonymizeNameExpr);
        visitor.registerHandler(ReturnStmt.class, this::anonymizeReturnStmt);
    }
    
}
