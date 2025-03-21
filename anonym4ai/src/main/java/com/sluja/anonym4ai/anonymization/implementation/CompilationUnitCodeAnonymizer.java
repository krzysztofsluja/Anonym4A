package com.sluja.anonym4ai.anonymization.implementation;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.sluja.anonym4ai.anonymization.AbstractCodeAnonymizer;
import com.sluja.anonym4ai.anonymization.AbstractUnifiedCodeAnonymizer;

public class CompilationUnitCodeAnonymizer extends AbstractUnifiedCodeAnonymizer<CompilationUnit> {

    private AbstractCodeAnonymizer<ClassOrInterfaceDeclaration, ClassOrInterfaceType> classAnonymizer;
    private AbstractUnifiedCodeAnonymizer<MethodDeclaration> methodAnonymizer;
    private AbstractUnifiedCodeAnonymizer<BlockStmt> blockCodeAnonymizer;
    private AbstractUnifiedCodeAnonymizer<Statement> statementCodeAnonymizer;
    //private AbstractUnifiedCodeAnonymizer<Statement> statementCodeAnonymizer;

    public CompilationUnitCodeAnonymizer() {
        classAnonymizer = new ClassCodeAnonymizer();
        methodAnonymizer = new MethodCodeAnonymizer();
        blockCodeAnonymizer = new BlockCodeAnonymizer();
        statementCodeAnonymizer = new StatementCodeAnonymizer();
    }

    @Override
    protected String anonymize(CompilationUnit code) {
        final AnonymizingVisitor visitor = new AnonymizingVisitor();
        registerVisitorHandlers(visitor);
        visitor.visit(code, null);
        return StringUtils.EMPTY;
    }

    private void anonymizeCompilationUnit(final CompilationUnit code, final Void arg) {
        code.removePackageDeclaration();
        code.setImports(new NodeList<>());
        code.getAllComments().forEach(Comment::remove);
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

            @Override
            public void visit(final BlockStmt n, final Void arg) {
                new BlockCodeAnonymizer().anonymize(n);
                super.visit(n, arg);
            }
            
        }, arg);
    }

    @Override
    protected void registerVisitorHandlers(final AbstractCodeAnonymizer<CompilationUnit, CompilationUnit>.AnonymizingVisitor visitor) {
        visitor.registerHandler(CompilationUnit.class, this::anonymizeCompilationUnit);
    }

}
