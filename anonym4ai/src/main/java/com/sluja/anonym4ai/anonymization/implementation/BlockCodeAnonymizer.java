package com.sluja.anonym4ai.anonymization.implementation;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;

public class BlockCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<BlockStmt> {

    @Override
    protected String anonymize(BlockStmt code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        visitor.visit(code, null);
        return StringUtils.EMPTY; 
    }

    private void anonymizeBlock(final BlockStmt code, final Void arg) {
        code.getAllContainedComments().forEach(Node::remove);
        code.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final ClassOrInterfaceDeclaration n, final Void arg) {
                new ClassCodeAnonymizer().anonymize(n);
                super.visit(n, arg);
            }
            
            @Override
            public void visit(final MethodDeclaration n, final Void arg) {
                new MethodCodeAnonymizer().anonymize(n);
                super.visit(n, arg);
            }
        }, arg);
    }


    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(BlockStmt.class, this::anonymizeBlock);
    }

}
