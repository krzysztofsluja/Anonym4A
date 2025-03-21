package com.sluja.anonym4ai.anonymization.implementation;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;

public class StatementCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<Statement> {

    @Override
    protected String anonymize(final Statement code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        anonymizeStatement(code, null);
        return StringUtils.EMPTY; 
    }

    private void anonymizeStatement(final Statement code, final Void arg) {
        code.getAllContainedComments().forEach(Node::remove);
        code.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final BlockStmt n, final Void arg) {
                new BlockCodeAnonymizer().anonymize(n);
                super.visit(n, arg);
            }
        }, arg);
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(BlockStmt.class, this::anonymizeStatement);
    }

    @Override
    public Statement parse(final String code) {
        return StaticJavaParser.parseStatement(code);
    }
}
