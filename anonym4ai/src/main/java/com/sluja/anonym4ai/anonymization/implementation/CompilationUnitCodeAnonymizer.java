package com.sluja.anonym4ai.anonymization.implementation;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.utils.AnonymizingVisitor;

public class CompilationUnitCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<CompilationUnit> {

    private AbstractCodeAnonymizer<ClassOrInterfaceDeclaration, ClassOrInterfaceType> classAnonymizer;
    private AbstractUnifiedCodeAnonymizer<MethodDeclaration> methodAnonymizer;
    private AbstractUnifiedCodeAnonymizer<BlockStmt> blockCodeAnonymizer;
    // private AbstractUnifiedCodeAnonymizer<SimpleName> statementCodeAnonymizer;
    private AbstractUnifiedCodeAnonymizer<EnumDeclaration> enumCodeAnonymizer;
    // private AbstractUnifiedCodeAnonymizer<Statement> statementCodeAnonymizer;

    public CompilationUnitCodeAnonymizer(final AnonymizingVisitor visitor) {
        super(visitor);
        classAnonymizer = new ClassCodeAnonymizer(visitor);
        methodAnonymizer = new MethodCodeAnonymizer(visitor);
        blockCodeAnonymizer = new BlockCodeAnonymizer(visitor);
        // statementCodeAnonymizer = new StatementCodeAnonymizer(visitor);
        enumCodeAnonymizer = new EnumCodeAnonymizer(visitor);
    }

    @Override
    public String anonymize(CompilationUnit code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        visitor.visit(code, null);
        return code.toString();
    }

    private void anonymizeCompilationUnit(final CompilationUnit code, final Void arg) {
        code.removePackageDeclaration();
        code.setImports(new NodeList<>());
        code.getAllComments().forEach(Comment::remove);
        code.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(final ClassOrInterfaceDeclaration n, final Void arg) {
                classAnonymizer.anonymize(n);
                super.visit(n, arg);
            }

            @Override
            public void visit(final MethodDeclaration n, final Void arg) {
                methodAnonymizer.anonymize(n);
                super.visit(n, arg);
            }

            @Override
            public void visit(final BlockStmt n, final Void arg) {
                blockCodeAnonymizer.anonymize(n);
                super.visit(n, arg);
            }

            @Override
            public void visit(final EnumDeclaration n, final Void arg) {
                enumCodeAnonymizer.anonymize(n);
                super.visit(n, arg);
            }

        }, arg);
    }

    @Override
    protected void registerVisitorHandlers(final AnonymizingVisitor visitor) {
        visitor.registerHandler(CompilationUnit.class, this::anonymizeCompilationUnit);
    }

}
