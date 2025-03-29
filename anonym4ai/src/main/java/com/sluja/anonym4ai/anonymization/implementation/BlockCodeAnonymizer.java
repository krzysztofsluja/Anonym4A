package com.sluja.anonym4ai.anonymization.implementation;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

public class BlockCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<BlockStmt> {

    public BlockCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
        registerVisitorHandlers(visitor);
    }

    @Override
    public String anonymize(final BlockStmt code) {
        visitor.visit(code, null);
        return code.toString();

    }

    private void anonymizeBlock(final BlockStmt code, final Void arg) {
        code.getAllContainedComments().forEach(Node::remove);
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(BlockStmt.class, this::anonymizeBlock);
    }

}
